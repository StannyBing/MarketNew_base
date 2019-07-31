package com.zx.module_entity.module.entity.ui

import android.content.Intent
import android.os.Bundle
import com.zx.module_entity.R
import com.zx.module_entity.XAppEntity
import com.zx.module_entity.module.entity.bean.EntityDetailBean
import com.zx.module_entity.module.entity.mvp.contract.DetailImageContract
import com.zx.module_entity.module.entity.mvp.model.DetailImageModel
import com.zx.module_entity.module.entity.mvp.presenter.DetailImagePresenter
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.FileBean
import com.zx.module_library.func.listener.FileFuncListener
import com.zx.module_library.func.tool.toJson
import com.zx.module_library.view.AddFileView
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.fragment_entity_detail_image.*
import java.io.File

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailImageFragment : BaseFragment<DetailImagePresenter, DetailImageModel>(), DetailImageContract.View {

    private var entityDetail: EntityDetailBean? = null

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DetailImageFragment {
            val fragment = DetailImageFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_entity_detail_image
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        file_view.withXApp(XAppEntity.ENTITY)
                .setModifiable()
                .showSaveView()
                .setAddType(AddFileView.AddType.NORMAL)
                .setFileListener(object : FileFuncListener {
                    override fun onFileDelete(fileBean: FileBean, position: Int): Boolean {
                        if (!fileBean.id.isNullOrEmpty()) {
                            ZXDialogUtil.showYesNoDialog(activity, "提示", "是否删除该文件？") { _, _ ->
                                mPresenter.deleteFile(position, hashMapOf("savePath" to fileBean.filePath?.replace(BaseConfigModule.BASE_IP, "")).toJson())
                            }
                        }
                        return fileBean.id.isNullOrEmpty()
                    }

                    override fun onFileSave() {
                        val files = arrayListOf<File>()
                        if (file_view.fileList.isNotEmpty()) {
                            file_view.fileList.forEach {
                                if (it.id.isNullOrEmpty()) {
                                    files.add(File(it.filePath))
                                }
                            }
                        }
                        if (files.isEmpty()) {
                            showToast("当前文件信息无修改")
                        } else {
                            ZXDialogUtil.showYesNoDialog(activity!!, "提示", "是否提交文件信息？") { _, _ ->
                                mPresenter.uploadFile(entityDetail!!.fImgUrl ?: "", files)
                            }
                        }
                    }
                })
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    fun resetInfo(entityDetail: EntityDetailBean) {
        this.entityDetail = entityDetail
        if (entityDetail.fFile != null && entityDetail.fFile!!.isNotEmpty()) {
            val files = arrayListOf<FileBean>()
            entityDetail.fFile!!.forEach {
                files.add(FileBean(it.id, it.realName, BaseConfigModule.BASE_IP + it.savePath, dateLong = it.saveDate))
            }
            file_view.setDatas(files)
        }
    }

    //文件删除回调
    override fun onFileDeleteResult(position: Int) {
        showToast("删除成功")
        file_view.fileList.removeAt(position)
        file_view.fileAdapter.notifyItemRemoved(position)
    }

    //文件上传回调
    override fun onFileUploadResult(id: String, paths: String) {
        val pathList = paths.split(",")
        file_view.fileList.forEach {
            if (it.id.isNullOrEmpty() && !it.filePath.isNullOrEmpty() && pathList.isNotEmpty()) {
                val fileName = it.filePath!!.substring(it.filePath!!.lastIndexOf("/"), it.filePath!!.lastIndexOf("."))
                for (path in pathList) {
                    if (path.contains(fileName)) {
                        it.id = "hasUpload"
                        it.filePath = BaseConfigModule.BASE_IP + path
                    }
                }
            }
        }
        mPresenter.doModify(hashMapOf("fEntityGuid" to entityDetail?.fEntityGuid, "fImgUrl" to id).toJson())
    }

    //信息修改回调
    override fun onInfoModifyResult() {
        showToast("文件信息保存成功")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        file_view.onActivityResult(requestCode, resultCode, data)
    }
}
