package com.zx.module_complain.module.info.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_complain.R
import com.zx.module_complain.XAppComplain
import com.zx.module_complain.api.ApiParamUtil
import com.zx.module_complain.module.info.bean.DeptBean
import com.zx.module_complain.module.info.bean.DetailBean
import com.zx.module_complain.module.info.bean.DisposeBean
import com.zx.module_complain.module.info.func.adapter.DisposeAdapter
import com.zx.module_complain.module.info.func.tool.ComplainStatusTool
import com.zx.module_complain.module.info.mvp.contract.DisposeContract
import com.zx.module_complain.module.info.mvp.model.DisposeModel
import com.zx.module_complain.module.info.mvp.presenter.DisposePresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.UserBean
import com.zx.module_library.func.tool.UserActionTool
import com.zx.module_library.func.tool.UserManager
import com.zx.zxutils.other.ZXInScrollRecylerManager
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.activity_complain_dispose.*
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：投诉举报-处置
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_COMPLAIN_DISPOSE)
class DisposeActivity : BaseActivity<DisposePresenter, DisposeModel>(), DisposeContract.View {

    private lateinit var detailBean: DetailBean

    private val disposeList = arrayListOf<DisposeBean>()
    private val disposeAdapter = DisposeAdapter(disposeList)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, detailBean: DetailBean) {
            val intent = Intent(activity, DisposeActivity::class.java)
            intent.putExtra("detailBean", detailBean)
            activity.startActivityForResult(intent, 0x01)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_complain_dispose
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        detailBean = intent.getSerializableExtra("detailBean") as DetailBean

        toolBar_view.setMidText("任务处理-${ComplainStatusTool.getStatusString(detailBean.baseInfo.fStatus)}")
        toolBar_view.withXApp(XAppComplain.LIST)
        btn_complain_submit.background.setTint(ContextCompat.getColor(this, XAppComplain.LIST.moduleColor))

        rv_complain_dispose.apply {
            layoutManager = ZXInScrollRecylerManager(this@DisposeActivity) as RecyclerView.LayoutManager?
            adapter = disposeAdapter.apply {
                setModuleColor(ContextCompat.getColor(this@DisposeActivity, XAppComplain.LIST.moduleColor))
            }
        }

        initItem()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //spinner选择事件
        disposeAdapter.setSpinnerCall {
            if (rv_complain_dispose.isComputingLayout) {
                return@setSpinnerCall
            }
            when (disposeList[it].disposeName) {
                "操作" -> initItem(disposeList[it].resultValue == "pass")
                "分流科室/分局" -> {
                    if (disposeList[it].resultValue == "0") {//业务科室
                        mPresenter.getDeptList(ApiParamUtil.deptListParam("17"))
                    } else if (disposeList[it].resultValue == "1") {//分局
                        mPresenter.getDeptList(ApiParamUtil.deptListParam("10"))
                    }
                }
            }
        }
        //提交事件
        btn_complain_submit.setOnClickListener {
            if (disposeAdapter.checkItem()) {
                ZXDialogUtil.showYesNoDialog(this@DisposeActivity, "提示", "是否提交任务处理信息？") { dialog, which ->
                    if (disposeList.getItem("操作") != null && disposeList.getItem("操作")!!.resultValue == "return") {//退回
                        mPresenter.submitDispose(ApiParamUtil.disposeParam(detailBean.baseInfo.fGuid!!,
                                detailBean.baseInfo.fStatus!!,
                                disposeList.getItem("操作")!!.resultValue,
                                disposeList.getItem("处理说明")!!.resultValue))
                    } else {
                        when (detailBean.baseInfo.fStatus) {
                            10 -> {//受理

                            }
                            20 -> {//分流
                                mPresenter.submitDispose(ApiParamUtil.shuntParam(detailBean.baseInfo.fGuid!!,
                                        detailBean.baseInfo.fStatus!!,
                                        disposeList.getItem("操作")!!.resultValue,
                                        "分流到-" + disposeList.getItem("分流科室/分局")!!.resultKey + "(" + disposeList.getItem("分流对象")!!.resultKey + ")。" + disposeList.getItem("处理说明")!!.resultValue,
                                        disposeList.getItem("分流对象")!!.resultValue))
                            }
                            30 -> {//指派
                                mPresenter.submitDispose(ApiParamUtil.assignParam(detailBean.baseInfo.fGuid!!,
                                        detailBean.baseInfo.fStatus!!,
                                        disposeList.getItem("操作")!!.resultValue,
                                        "指派到-" + UserManager.getUser().department + "(" + disposeList.getItem("指派对象")!!.resultKey + ")。" + disposeList.getItem("处理说明")!!.resultValue,
                                        disposeList.getItem("指派对象")!!.resultValue))
                            }
                            40 -> {//联系
                                mPresenter.submitDispose(ApiParamUtil.contractParam(detailBean.baseInfo.fGuid!!,
                                        detailBean.baseInfo.fStatus!!,
                                        disposeList.getItem("操作")!!.resultValue,
                                        if (disposeList.getItem("是否联系")!!.resultValue == "是") {
                                            "已联系。${disposeList.getItem("处理说明")!!.resultValue}"
                                        } else {
                                            "未联系。${disposeList.getItem("处理说明")!!.resultValue}"
                                        },
                                        disposeList.getItem("是否联系")!!.resultValue,
                                        if (disposeList.getItem("是否联系")!!.resultValue == "是") {
                                            disposeList.getItem("联系途径")!!.resultValue
                                        } else {
                                            ""
                                        }))
                            }
                            50 -> {//处置
                                if (addfile_view.fileList.isNotEmpty()) {
                                    val files = arrayListOf<File>()
                                    addfile_view.fileList.forEach {
                                        files.add(File(it.filePath))
                                    }
                                    mPresenter.uploadFile(detailBean.baseInfo.fFilename ?: "", files)
                                } else {
                                    onFileUploadResult("", "")
                                }
                            }
                            60 -> {//初审
                                mPresenter.submitDispose(ApiParamUtil.disposeParam(detailBean.baseInfo.fGuid!!,
                                        detailBean.baseInfo.fStatus!!,
                                        disposeList.getItem("操作")!!.resultValue,
                                        disposeList.getItem("处理说明")!!.resultValue))
                            }
                            70 -> {//终审
                                mPresenter.submitDispose(ApiParamUtil.disposeParam(detailBean.baseInfo.fGuid!!,
                                        detailBean.baseInfo.fStatus!!,
                                        disposeList.getItem("操作")!!.resultValue,
                                        disposeList.getItem("处理说明")!!.resultValue))
                            }
                            80 -> {//办结
                                mPresenter.submitDispose(ApiParamUtil.disposeParam(detailBean.baseInfo.fGuid!!,
                                        detailBean.baseInfo.fStatus!!,
                                        disposeList.getItem("操作")!!.resultValue,
                                        disposeList.getItem("处理说明")!!.resultValue))
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 初始化item
     */
    private fun initItem(pass: Boolean = true) {
        disposeList.clear()
        if (!pass) {//退回操作
            addfile_view.visibility = View.GONE
            disposeList.apply {
                add(DisposeBean(DisposeBean.DisposeType.Spinner, "操作", arrayListOf<DisposeBean.ValueBean>().apply {
                    add(DisposeBean.ValueBean("通过", "pass"))
                    add(DisposeBean.ValueBean("退回", "return", true))
                }, true, false))
                add(DisposeBean(DisposeBean.DisposeType.Edit, "处理说明"))
            }
            disposeAdapter.notifyDataSetChanged()
            return
        }
        //通过的操作
        when (detailBean.baseInfo.fStatus) {
            10 -> {//受理
                disposeList.apply {
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "操作", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("受理", "pass", true))
                        add(DisposeBean.ValueBean("不受理", "not"))
                        add(DisposeBean.ValueBean("退回", "return"))
                    }, true, false))
                    add(DisposeBean(DisposeBean.DisposeType.Edit, "处理说明"))
                }
            }
            20 -> {//分流
                disposeList.apply {
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "操作", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("通过", "pass", true))
                        add(DisposeBean.ValueBean("退回", "return"))
                    }, true, false))
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "分流科室/分局", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("业务科室", "0"))
                        add(DisposeBean.ValueBean("分局", "1"))
                    }, true))
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "分流对象", arrayListOf<DisposeBean.ValueBean>().apply {
                    }, true))
                    add(DisposeBean(DisposeBean.DisposeType.Edit, "处理说明"))
                }
            }
            30 -> {//指派
                disposeList.apply {
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "操作", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("通过", "pass", true))
                        add(DisposeBean.ValueBean("退回", "return"))
                    }, true, false))
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "指派对象", arrayListOf<DisposeBean.ValueBean>().apply {
                    }, true))
                    add(DisposeBean(DisposeBean.DisposeType.Edit, "处理说明"))
                }
                mPresenter.getUserList(ApiParamUtil.userListParam(UserManager.getUser().departmentCode, "1002,2040"))
            }
            40 -> {//联系
                disposeList.apply {
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "操作", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("通过", "pass", true))
                        add(DisposeBean.ValueBean("退回", "return"))
                    }, true, false))
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "是否联系", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("是", "是"))
                        add(DisposeBean.ValueBean("否", "否"))
                    }, true))
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "联系途径", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("电话", "电话"))
                        add(DisposeBean.ValueBean("传真", "传真"))
                        add(DisposeBean.ValueBean("邮件", "邮件"))
                    }, false))
                    add(DisposeBean(DisposeBean.DisposeType.Edit, "处理说明"))
                }
            }
            50 -> {//处置
                addfile_view.visibility = View.VISIBLE
                addfile_view.withXApp(XAppComplain.LIST)
                        .setModifiable()
                disposeList.apply {
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "操作", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("通过", "pass", true))
                        add(DisposeBean.ValueBean("退回", "return"))
                    }, true, false))
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "调解结果", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("达成调解协议", "达成调解协议"))
                        add(DisposeBean.ValueBean("协商和解", "协商和解"))
                        add(DisposeBean.ValueBean("撤回投诉", "撤回投诉"))
                        add(DisposeBean.ValueBean("终止调解", "终止调解"))
                    }, true))
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "受理类型", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("调解", "调解"))
                        add(DisposeBean.ValueBean("查处", "查处"))
                        add(DisposeBean.ValueBean("不属实", "不属实"))
                        add(DisposeBean.ValueBean("作为案件线索查处", "作为案件线索查处"))
                        add(DisposeBean.ValueBean("其他", "其他"))
                    }, false))
                    add(DisposeBean(DisposeBean.DisposeType.Edit, "调解结果(反馈内容)", isRequired = true))
                }
            }
            60 -> {//初审
                disposeList.apply {
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "操作", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("通过", "pass", true))
                        add(DisposeBean.ValueBean("退回", "return"))
                    }, true, false))
                    add(DisposeBean(DisposeBean.DisposeType.Text, "处置结果", resultValue =
                    "受理类型：" + (detailBean.baseInfo.fAcceptType ?: "") + "\n" +
                            "调解结果：" + (detailBean.baseInfo.fMediationResult ?: "") + "\n" +
                            "调解结果（反馈内容）" + (detailBean.baseInfo.fFeedbackContent ?: "")))
                    add(DisposeBean(DisposeBean.DisposeType.Edit, "处理说明"))
                }
            }
            70 -> {//终审
                disposeList.apply {
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "操作", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("通过", "pass", true))
                        add(DisposeBean.ValueBean("退回", "return"))
                    }, true, false))
                    add(DisposeBean(DisposeBean.DisposeType.Text, "处置结果", resultValue =
                    "受理类型：" + (detailBean.baseInfo.fAcceptType ?: "") + "\n" +
                            "调解结果：" + (detailBean.baseInfo.fMediationResult ?: "") + "\n" +
                            "调解结果（反馈内容）" + (detailBean.baseInfo.fFeedbackContent ?: "")))
                    add(DisposeBean(DisposeBean.DisposeType.Edit, "处理说明"))
                }
            }
            80 -> {//办结
                disposeList.apply {
                    add(DisposeBean(DisposeBean.DisposeType.Spinner, "操作", arrayListOf<DisposeBean.ValueBean>().apply {
                        add(DisposeBean.ValueBean("通过", "pass", true))
                        add(DisposeBean.ValueBean("退回", "return"))
                    }, true, false))
                    add(DisposeBean(DisposeBean.DisposeType.Text, "处置结果", resultValue =
                    "受理类型：" + (detailBean.baseInfo.fAcceptType ?: "") + "\n" +
                            "调解结果：" + (detailBean.baseInfo.fMediationResult ?: "") + "\n" +
                            "调解结果（反馈内容）" + (detailBean.baseInfo.fFeedbackContent ?: "")))
                    add(DisposeBean(DisposeBean.DisposeType.Edit, "处理说明"))
                }
            }
        }
        disposeAdapter.notifyDataSetChanged()
    }

    override fun onDisposeResult() {
        UserActionTool.addUserAction(this, UserActionTool.ActionType.Complain, detailBean.baseInfo.fGuid!!)
        showToast("处置信息提交成功")
        setResult(0x01)
        finish()
    }

    /**
     * 指派-人员列表
     */
    override fun onUserListResult(userList: List<UserBean>) {
        val position = disposeList.getItemPosition("指派对象")
        disposeList[position].resultValue = ""
        if (position != -1) {
            disposeList[position].disposeValue.apply {
                clear()
                if (userList.isNotEmpty()) {
                    userList.forEach {
                        add(DisposeBean.ValueBean(it.realName, it.id))
                    }
                }
            }
            disposeAdapter.notifyItemChanged(position)
        }
    }

    /**
     * 分流-人员列表
     */
    override fun onDeptListResult(deptList: List<DeptBean>) {
        val position = disposeList.getItemPosition("分流对象")
        disposeList[position].resultValue = ""
        if (position != -1) {
            disposeList[position].disposeValue.apply {
                clear()
                if (deptList.isNotEmpty()) {
                    deptList.forEach {
                        add(DisposeBean.ValueBean(it.name, it.id))
                    }
                }
            }
            disposeAdapter.notifyItemChanged(position)
        }
    }

    //处置-附件上传成功
    override fun onFileUploadResult(id: String, paths: String) {
        mPresenter.submitDispose(ApiParamUtil.handleParam(detailBean.baseInfo.fGuid!!,
                detailBean.baseInfo.fStatus!!,
                disposeList.getItem("操作")!!.resultValue,
                disposeList.getItem("调解结果")!!.resultValue,
                disposeList.getItem("受理类型")!!.resultValue,
                disposeList.getItem("调解结果(反馈内容)")!!.resultValue,
                id))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        addfile_view.onActivityResult(requestCode, resultCode, data)
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

}
