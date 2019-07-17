package com.zx.module_library.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.zx.module_library.R
import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.FileBean
import com.zx.module_library.bean.XAppBean
import com.zx.module_library.func.adapter.AddFileAdapter
import com.zx.module_library.func.listener.FileFuncListener
import com.zx.module_library.func.tool.FileUriUtil
import com.zx.zxutils.other.ZXInScrollRecylerManager
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXTimeUtil
import com.zx.zxutils.util.ZXToastUtil

/**
 * Created by Xiangb on 2019/6/28.
 * 功能：文件相关处理界面
 */
@SuppressLint("NewApi")
class AddFileView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var rvFileList: RecyclerView
    private lateinit var ivAddFile: ImageView
    private lateinit var tvAddInfo: TextView
    val fileList = arrayListOf<FileBean>()
    private val fileAdapter = AddFileAdapter(fileList)
    private var module_color: Int//xapp主题色
    private var modifiable: Boolean//可修改的
    private var listType: Int = 0//列表类型 0 普通列表  1九宫格
    private var fileFuncListener: FileFuncListener? = null
    private var addType = AddType.NORMAL
    private lateinit var footerView: View


    enum class AddType {
        IMAGE,
        VIDEO,
        FILE,
        IMAGE_VIDEO,
        NORMAL
    }

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.FileHandleView, defStyleAttr, 0)
        module_color = typedArray.getColor(R.styleable.FileHandleView_module_color, ContextCompat.getColor(context, R.color.colorPrimary))
        modifiable = typedArray.getBoolean(R.styleable.FileHandleView_modifiable, false)

        initFooterView()
        fileAdapter.init(module_color, modifiable)

        setView(context)
        typedArray.recycle()
    }

    private fun setView(context: Context) {
        View.inflate(context, R.layout.layout_file_view, this)
        rvFileList = findViewById(R.id.rv_file_list)
        tvAddInfo = findViewById(R.id.tv_add_file_info)

        val addDrawable = ivAddFile.drawable.mutate()
        addDrawable.setTint(module_color)

        rvFileList.apply {
            layoutManager = if (listType == 0) {
                ZXInScrollRecylerManager(context)
            } else {
                GridLayoutManager(context, 3)
            }
            adapter = fileAdapter.apply {
                //child的点击事件
                setOnItemChildClickListener { _, view, position ->
                    when (view.id) {
                        R.id.iv_file_delete -> {
                            if (fileFuncListener != null) {
                                fileFuncListener!!.onFileDelete(fileList[position], position)
                            }
                            fileList.removeAt(position)
                            fileAdapter.notifyItemRemoved(position)

                        }
                    }
                }
                //view的点击事件
                setOnItemClickListener { _, _, position ->
                    if (fileFuncListener != null) {
                        fileFuncListener!!.onFileClick(fileList[position], position)
                    }
                    XApp.startXApp(RoutePath.ROUTE_OTHER_PREVIEW) {
                        it["name"] = fileList[position].fileName ?: ""
                        it["path"] = fileList[position].filePath ?: ""
                    }
                }
                //添加footer
                if (modifiable) {
                    addFooterView(footerView)
                }
            }
        }
        if (modifiable) {
            tvAddInfo.visibility = View.GONE
        } else {
            tvAddInfo.setTextColor(module_color)
            tvAddInfo.visibility = View.VISIBLE
        }
        fileAdapter.notifyDataSetChanged()

    }

    private fun initFooterView() {
        footerView = LayoutInflater.from(context).inflate(R.layout.layout_add_file_foot, null, false)
        ivAddFile = footerView.findViewById(R.id.iv_file_add)
        footerView.setOnClickListener {
            (context as BaseActivity<*, *>).getPermission(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)) {
                if (addType == AddType.IMAGE) {
                    XApp.startXApp(RoutePath.ROUTE_OTHER_CAMERA, context as Activity, 0x02) {
                        it["cameraType"] = 1
                    }
                } else if (addType == AddType.VIDEO) {
                    XApp.startXApp(RoutePath.ROUTE_OTHER_CAMERA, context as Activity, 0x02) {
                        it["cameraType"] = 2
                    }
                } else if (addType == AddType.FILE) {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "*/*"//设置类型，我这里是任意类型，任意后缀的可以这样写。
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    (context as Activity).startActivityForResult(intent, 0x03)
                } else {
                    val addView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_addfile, null, false)
                    val llAddImage = addView.findViewById<LinearLayout>(R.id.ll_fileadd_image)
                    val llAddVideo = addView.findViewById<LinearLayout>(R.id.ll_fileadd_video)
                    val llAddOther = addView.findViewById<LinearLayout>(R.id.ll_fileadd_other)
                    val ivAddImage = addView.findViewById<ImageView>(R.id.iv_fileadd_image)
                    val ivAddVideo = addView.findViewById<ImageView>(R.id.iv_fileadd_video)
                    val ivAddOther = addView.findViewById<ImageView>(R.id.iv_fileadd_other)

                    ivAddImage.drawable.mutate().setTint(module_color)
                    ivAddVideo.drawable.mutate().setTint(module_color)
                    ivAddOther.drawable.mutate().setTint(module_color)

                    if (addType == AddType.IMAGE_VIDEO) {
                        ivAddOther.visibility = View.GONE
                    }

                    llAddImage.setOnClickListener {
                        XApp.startXApp(RoutePath.ROUTE_OTHER_CAMERA, context as Activity, 0x02) {
                            it["cameraType"] = 1
                        }
                        ZXDialogUtil.dismissDialog()
                    }
                    llAddVideo.setOnClickListener {
                        XApp.startXApp(RoutePath.ROUTE_OTHER_CAMERA, context as Activity, 0x02) {
                            it["cameraType"] = 2
                        }
                        ZXDialogUtil.dismissDialog()
                    }
                    llAddOther.setOnClickListener {
                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.type = "*/*"//设置类型，我这里是任意类型，任意后缀的可以这样写。
                        intent.addCategory(Intent.CATEGORY_OPENABLE)
                        (context as Activity).startActivityForResult(intent, 0x03)
                        ZXDialogUtil.dismissDialog()
                    }

                    ZXDialogUtil.showCustomViewDialog(context, "请选择添加类型", addView, null, { _, _ -> }, false)
                }
            }
        }
    }

    //可修改
    fun setModifiable(): AddFileView {
        modifiable = true
        fileAdapter.init(module_color, modifiable)
        if (!modifiable) {
            fileAdapter.removeAllFooterView()
        } else {
            if (fileAdapter.footerViewsCount == 0) {
                fileAdapter.addFooterView(footerView)
            }
        }
        val addDrawable = ivAddFile.drawable.mutate()
        addDrawable.setTint(module_color)

        if (modifiable) {
            tvAddInfo.visibility = View.GONE
        } else {
            tvAddInfo.setTextColor(module_color)
            tvAddInfo.visibility = View.VISIBLE
        }
        return this
    }

    //设置文件获取方式
    fun setAddType(addType: AddType): AddFileView {
        this.addType = addType
        return this
    }

    //设置文件监听
    fun setFileListener(fileFuncListener: FileFuncListener): AddFileView {
        this.fileFuncListener = fileFuncListener
        return this
    }

    //设置默认数据
    fun setDatas(files: List<FileBean>): AddFileView {
        fileList.addAll(files)
        fileAdapter.notifyDataSetChanged()
        if (!modifiable&&fileList.isEmpty()){
            tvAddInfo.visibility = View.VISIBLE
        }else{
            tvAddInfo.visibility = View.GONE
        }
        return this
    }

    //初始化主题
    fun withXApp(xappBean: XAppBean?): AddFileView {
        if (xappBean != null) {
            module_color = ContextCompat.getColor(context, xappBean.moduleColor)
            fileAdapter.init(module_color, modifiable)

            tvAddInfo.setTextColor(module_color)
        }
        return this
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0x03) {//文件选取
            if (data != null) {
                val uri = data.getData()
                val filePath = FileUriUtil.getFileAbsolutePath(context, uri)
                if (filePath == null) {
                    ZXToastUtil.showToast("获取文件路径失败，请重试")
                } else {
//                    val type = filePath.substring(filePath.lastIndexOf(".") + 1)
//                    if (type !in arrayOf("jpg", "png", "gif", "bmp", "psd", "tiff", "tga", "eps",
//                                    "mp4", "rmvb", "avi", "mp3", "wma", "wav", "pdf", "xls", "txt",
//                                    "doc", "docx", "ppt", "xlsx", "pptx", "cad", "csv", "dwg", "odt")) {
//                        ZXToastUtil.showToast("当前文件不符合规范，请重新选择")
//                        return
//                    }
                    val fileName = filePath.substring(filePath.lastIndexOf("/") + 1)
                    val fileBean = FileBean(fileName = fileName, filePath = filePath, date = ZXTimeUtil.getCurrentTime())
                    fileList.add(fileBean)

                    fileAdapter.notifyDataSetChanged()
                    if (fileFuncListener != null) {
                        fileFuncListener!!.onFileAdd(fileBean)
                    }
                }
            }
        } else if (resultCode == 0x02) {//拍摄-录像
            if (data != null) {
                val fileBean = FileBean(fileName = data.getStringExtra("name"), filePath = data.getStringExtra("path"), date = ZXTimeUtil.getCurrentTime())
                fileList.add(fileBean)
                fileAdapter.notifyDataSetChanged()
                if (fileFuncListener != null) {
                    fileFuncListener!!.onFileAdd(fileBean)
                }
            }
        }
    }

}
