package com.zx.module_entity.module.special.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_entity.R
import com.zx.module_entity.XAppEntity
import com.zx.module_entity.module.entity.bean.DicTypeBean
import com.zx.module_entity.module.special.bean.DeptBean
import com.zx.module_entity.module.special.bean.DisposeBean
import com.zx.module_entity.module.special.func.adapter.DisposeAdapter
import com.zx.module_entity.module.special.mvp.contract.SpecialAddContract
import com.zx.module_entity.module.special.mvp.model.SpecialAddModel
import com.zx.module_entity.module.special.mvp.presenter.SpecialAddPresenter
import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.toJson
import com.zx.zxutils.other.ZXInScrollRecylerManager
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXLocationUtil
import kotlinx.android.synthetic.main.activity_special_add.*
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：特殊主体-上报
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_ENTITY_SPECIAL_ADD)
class SpecialAddActivity : BaseActivity<SpecialAddPresenter, SpecialAddModel>(), SpecialAddContract.View {

    private val disposeList = arrayListOf<DisposeBean>()
    private val disposeAdapter = DisposeAdapter(disposeList)

    private var entityLocation: PointF? = null

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, SpecialAddActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_special_add
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolBar_view.withXApp(XAppEntity.SPECIAL)
        file_view.withXApp(XAppEntity.SPECIAL)
                .setModifiable()
        btn_special_submit.background.setTint(ContextCompat.getColor(this, XAppEntity.SPECIAL.moduleColor))

        rv_special_info.apply {
            layoutManager = ZXInScrollRecylerManager(this@SpecialAddActivity) as RecyclerView.LayoutManager?
            adapter = disposeAdapter.apply {
                setModuleColor(ContextCompat.getColor(this@SpecialAddActivity, XAppEntity.SPECIAL.moduleColor))
            }
        }

        initItem()
    }

    private fun initItem() {
        disposeList.clear()
        disposeList.add(DisposeBean(DisposeBean.DisposeType.Edit, "主体名称", isRequired = true))
        disposeList.add(DisposeBean(DisposeBean.DisposeType.Spinner, "特殊主体类型", arrayListOf(), isRequired = true))
        disposeList.add(DisposeBean(DisposeBean.DisposeType.Edit, "法定代表人(负责人)"))
        disposeList.add(DisposeBean(DisposeBean.DisposeType.Edit, "地址"))
        disposeList.add(DisposeBean(DisposeBean.DisposeType.Edit, "联系方式"))
        disposeList.add(DisposeBean(DisposeBean.DisposeType.Spinner, "分管所", arrayListOf()))
        disposeList.add(DisposeBean(DisposeBean.DisposeType.Spinner, "分管片区", arrayListOf()))
        disposeList.add(DisposeBean(DisposeBean.DisposeType.Spinner, "企业标识", arrayListOf()))
        disposeList.add(DisposeBean(DisposeBean.DisposeType.Time, "录入时间"))
        disposeAdapter.notifyDataSetChanged()

        mPresenter.getNormalInfo()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //定位选择
        ll_special_location.setOnClickListener {
            ZXDialogUtil.showListDialog(this, "提示", "关闭", arrayOf("前往地图选点", "当前坐标点")) { _, index ->
                when (index) {
                    0 -> {//前往地图选点
                        XApp.startXApp(RoutePath.ROUTE_MAP_MAP, this, 100) {
                            it["type"] = 2
                            if (entityLocation != null) {
                                it["longitude"] = entityLocation!!.x
                                it["latitude"] = entityLocation!!.y
                            }
                        }
                    }
                    1 -> {//当前坐标点
                        getPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                            val location = ZXLocationUtil.getLocation(this)
                            if (location != null) {
                                entityLocation = PointF(location.longitude.toFloat(), location.latitude.toFloat())
                                tv_special_location.text = "经度：" + location.longitude +
                                        "\n" +
                                        "纬度：" + location.latitude
                            } else {
                                showToast("当前无法获取到坐标点，请前往地图选择")
                                XApp.startXApp(RoutePath.ROUTE_MAP_MAP, this, 100) {
                                    it["type"] = 2
                                }
                            }
                        }
                    }
                }
            }
        }
        //选择事件
        disposeAdapter.setSpinnerCall {
            if (rv_special_info.isComputingLayout) {
                return@setSpinnerCall
            }
            if (disposeList[it].disposeName == "分管所") {
                if (disposeList[it].resultValue.isNotEmpty()) {
                    mPresenter.getAreaDeptList(hashMapOf("parentId" to disposeList.getItem("分管所")?.resultValue!!))
                } else {
                    val position = disposeList.getItemPosition("分管片区")
                    disposeList[position].disposeValue.clear()
                    disposeAdapter.notifyItemChanged(position)
                }
            }
        }
        //提交
        btn_special_submit.setOnClickListener {
            if (entityLocation == null) {
                showToast("请先选择主体位置")
            } else if (disposeAdapter.checkItem()) {
                ZXDialogUtil.showYesNoDialog(this, "提示", "是否提交特殊主体信息？") { _, _ ->
                    if (file_view.fileList.isNotEmpty()) {
                        val fileList = arrayListOf<File>()
                        file_view.fileList.forEach {
                            fileList.add(File(it.filePath))
                        }
                        mPresenter.uploadFile(fileList)
                    } else {
                        onFileUploadResult("", "")
                    }
                }
            }
        }
        //记录
        toolBar_view.setRightClickListener {
            SpecialListActivity.startAction(this, false)
        }
    }

    private fun List<DisposeBean>.getItem(name: String): DisposeBean? {
        if (isNotEmpty()) {
            forEach {
                if (it.disposeName == name) {
                    return it
                }
            }
        }
        return null
    }

    private fun List<DisposeBean>.getItemPosition(name: String): Int {
        if (isNotEmpty()) {
            forEachIndexed { index, it ->
                if (it.disposeName == name) {
                    return index
                }
            }
        }
        return -1
    }

    //主体类别
    override fun onDicListResult(dicTypeBeans: List<DicTypeBean>) {
        val position = disposeList.getItemPosition("特殊主体类型")
        disposeList[position].resultValue = ""
        if (position != -1) {
            disposeList[position].disposeValue.apply {
                clear()
                if (dicTypeBeans.isNotEmpty()) {
                    dicTypeBeans.forEach {
                        add(DisposeBean.ValueBean(it.dicName, it.id))
                    }
                }
            }
            disposeAdapter.notifyItemChanged(position)
        }
    }

    //分管所
    override fun onDeptListResult(deptBeans: List<DeptBean>) {
        val position = disposeList.getItemPosition("分管所")
        disposeList[position].resultValue = ""
        if (position != -1) {
            disposeList[position].disposeValue.apply {
                clear()
                if (deptBeans.isNotEmpty()) {
                    deptBeans.forEach {
                        add(DisposeBean.ValueBean(it.name, it.id))
                    }
                }
            }
            disposeAdapter.notifyItemChanged(position)
        }
    }

    //分管片区
    override fun onAreaDeptListResult(deptBeans: List<DeptBean>) {
        val position = disposeList.getItemPosition("分管片区")
        disposeList[position].resultValue = ""
        if (position != -1) {
            disposeList[position].disposeValue.apply {
                clear()
                if (deptBeans.isNotEmpty()) {
                    deptBeans.forEach {
                        add(DisposeBean.ValueBean(it.name, it.id))
                    }
                }
            }
            disposeAdapter.notifyItemChanged(position)
        }
    }

    //企业标识
    override fun onIdentifyListResult(dicTypeBeans: List<DicTypeBean>) {
        val position = disposeList.getItemPosition("企业标识")
        disposeList[position].resultValue = ""
        if (position != -1) {
            disposeList[position].disposeValue.apply {
                clear()
                if (dicTypeBeans.isNotEmpty()) {
                    dicTypeBeans.forEach {
                        add(DisposeBean.ValueBean(it.dicName, it.id))
                    }
                }
            }
            disposeAdapter.notifyItemChanged(position)
        }
    }

    //提交成功
    override fun onSubmitResult() {
        showToast("提交成功")
        initItem()
        tv_special_location.text = ""
        entityLocation = null
        file_view.fileList.clear()
        file_view.fileAdapter.notifyDataSetChanged()
    }

    //文件上传完成
    override fun onFileUploadResult(id: String, paths: String) {
        mPresenter.doSubmit(hashMapOf(
                "fEntityName" to disposeList.getItem("主体名称")?.resultValue,
                "fType" to disposeList.getItem("特殊主体类型")?.resultValue,
                "fLongitude" to entityLocation!!.x,
                "fLatitude" to entityLocation!!.y,
                "fLegalPerson" to disposeList.getItem("法定代表人(负责人)")?.resultValue,
                "fAddress" to disposeList.getItem("地址")?.resultValue,
                "fContactInfo" to disposeList.getItem("联系方式")?.resultValue,
                "fStation" to disposeList.getItem("分管所")?.resultKey,
                "fStationCode" to disposeList.getItem("分管所")?.resultValue,
                "fGrid" to disposeList.getItem("分管片区")?.resultKey,
                "fGridCode" to disposeList.getItem("分管片区")?.resultValue,
                "fTags" to disposeList.getItem("企业标识")?.resultValue,
                "fFoundDate" to disposeList.getItem("录入时间")?.resultValue,
                "fImgUrl" to id
        ).toJson())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        file_view?.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 100) {
            entityLocation = PointF(data?.extras?.getDouble("longitude")!!.toFloat(), data?.extras?.getDouble("latitude")!!.toFloat())
            tv_special_location.text = "经度：" + data?.extras?.getDouble("longitude").toString() +
                    "\n" +
                    "纬度：" + data?.extras?.getDouble("latitude").toString()
        }
    }
}
