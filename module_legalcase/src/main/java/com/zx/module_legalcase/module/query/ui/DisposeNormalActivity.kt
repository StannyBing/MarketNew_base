package com.zx.module_legalcase.module.query.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_legalcase.R
import com.zx.module_legalcase.XAppLegalcase
import com.zx.module_legalcase.api.ApiParamUtil
import com.zx.module_legalcase.module.query.bean.DeptBean
import com.zx.module_legalcase.module.query.bean.DetailBean
import com.zx.module_legalcase.module.query.bean.DisposeBean
import com.zx.module_legalcase.module.query.func.adapter.DisposeAdapter
import com.zx.module_legalcase.module.query.mvp.contract.DisposeNormalContract
import com.zx.module_legalcase.module.query.mvp.model.DisposeNormalModel
import com.zx.module_legalcase.module.query.mvp.presenter.DisposeNormalPresenter
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.UserBean
import com.zx.module_library.func.tool.UserActionTool
import com.zx.zxutils.other.ZXInScrollRecylerManager
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.activity_dispose_normal.*


/**
 * Create By admin On 2017/7/11
 * 功能：案件-流程处置-普通流程
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_LEGALCASE_DISPOSENORMAL)
class DisposeNormalActivity : BaseActivity<DisposeNormalPresenter, DisposeNormalModel>(), DisposeNormalContract.View {

    private lateinit var detailBean: DetailBean

    private val disposeList = arrayListOf<DisposeBean>()
    private val disposeAdapter = DisposeAdapter(disposeList)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, detailBean: DetailBean) {
            val intent = Intent(activity, DisposeNormalActivity::class.java)
            intent.putExtra("detailBean", detailBean)
            activity.startActivityForResult(intent, 0x01)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_dispose_normal
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        detailBean = intent.getSerializableExtra("detailBean") as DetailBean

        toolBar_view.setMidText("任务处理-${detailBean.info.statusName}")
        toolBar_view.withXApp(XAppLegalcase.get("综合执法"))
        btn_submit_normal.background.setTint(ContextCompat.getColor(this, XAppLegalcase.get("综合执法")!!.moduleColor))

        rv_dispose_normal.apply {
            layoutManager = ZXInScrollRecylerManager(this@DisposeNormalActivity) as RecyclerView.LayoutManager?
            adapter = disposeAdapter.apply {
                setModuleColor(ContextCompat.getColor(this@DisposeNormalActivity, XAppLegalcase.get("综合执法")!!.moduleColor))
            }
        }

        initItem()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //列表psinner选择时间
        disposeAdapter.setSpinnerCall {
            if (rv_dispose_normal.isComputingLayout) {
                return@setSpinnerCall
            }
            if (disposeList[it].disposeName == "受理部门") {
                if (disposeList[it].resultValue.isNotEmpty()) {
                    mPresenter.getUserList(ApiParamUtil.userListParam(disposeList[it].resultValue))
                } else {
                    val position = disposeAdapter.getItemPosition("受理人")
                    disposeList[position].disposeValue.clear()
                    disposeAdapter.notifyItemChanged(position)
                }
            }
        }
        //提交事件
        btn_submit_normal.setOnClickListener {
            if (disposeAdapter.checkItem()) {
                ZXDialogUtil.showYesNoDialog(this@DisposeNormalActivity, "提示", "是否提交任务处理信息？") { _, _ ->
                    when (detailBean.info.status) {
                        "00" -> {//案件登记
                            mPresenter.doCaseStart(ApiParamUtil.caseStartParam(detailBean.info.id,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "01" -> {//线索核查
                            mPresenter.doCaseIsCase(ApiParamUtil.caseDisposeWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "02" -> {//结案
                            mPresenter.doCaseEndCase(ApiParamUtil.caseEndParam(detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "N01" -> {//反馈
                            mPresenter.doCaseOther(ApiParamUtil.caseDisposeWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y01" -> {//立案申请
                            mPresenter.doCaseApply(ApiParamUtil.caseDisposeWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId.toString(),
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y02" -> {//立案审批
                            mPresenter.doCaseAuditing(ApiParamUtil.caseDisposeWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y03" -> {//调查取证
                            mPresenter.doCaseExamine(ApiParamUtil.caseDisposeWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y04" -> {//终止调查
                            mPresenter.doCaseStopCase(ApiParamUtil.caseDisposeSimpleParam(detailBean.info.id,
                                    detailBean.info.taskId.toString(),
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y05" -> {//终结报告
                            mPresenter.doCaseReport(ApiParamUtil.caseDisposeSimpleParam(detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y06" -> {//初审
                            mPresenter.doCaseFirstTrial(ApiParamUtil.caseDisposeWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y07" -> {//委员会集体审理
                            mPresenter.doCaseSecondTrial(ApiParamUtil.caseDisposeWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y08" -> {//行政处罚告知
                            mPresenter.doCaseNotice(ApiParamUtil.caseDisposeWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y10" -> {//听证
                            mPresenter.doCaseHearing(ApiParamUtil.caseDisposeSimpleParam(detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y11" -> {//处罚决定
                            mPresenter.doCaseDecide(ApiParamUtil.caseDisposeSimpleParam(detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y12" -> {//处罚决定审核
                            mPresenter.doCaseDecideAuditing(ApiParamUtil.caseDisposeWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y13" -> {//送达当事人
                            mPresenter.doCaseSend(ApiParamUtil.caseDisposeSimpleParam(detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "Y14" -> {//行政处罚的执行
                            mPresenter.doCaseExecute(ApiParamUtil.caseDisposeSimpleParam(detailBean.info.id,
                                    detailBean.info.taskId.toString(),
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                    }
                }
            }
        }
    }

    /**
     * 初始化item
     */
    private fun initItem() {
        disposeList.clear()
        var yesText = "同意"
        var noText = "不同意"
        var addAgree = true
        var addDept = true
        when (detailBean.info.status) {
            "00" -> {//案件登记
                addAgree = false
                addDept = false
            }
            "01" -> {//线索核查
            }
            "02" -> {//结案
                addAgree = false
                addDept = false
            }
            "N01" -> {//反馈
                yesText = "结案"
                noText = "退回"
            }
            "Y01" -> {//立案申请
            }
            "Y02" -> {//立案审批
            }
            "Y03" -> {//调查取证
                yesText = "终结报告"
                noText = "终止调查"
            }
            "Y04" -> {//终止调查
                addAgree = false
            }
            "Y05" -> {//终结报告
                addAgree = false
            }
            "Y06" -> {//初审
            }
            "Y07" -> {//委员会集体审理
            }
            "Y08" -> {//行政处罚告知
                yesText = "听证"
                noText = "不听证"
            }
            "Y10" -> {//听证
                addAgree = false
            }
            "Y11" -> {//处罚决定
                addAgree = false
            }
            "Y12" -> {//处罚决定审核
            }
            "Y13" -> {//送达当事人
                addAgree = false
            }
            "Y14" -> {//行政处罚的执行
                addAgree = false
            }
        }
        if (addDept) {
            mPresenter.getDeptList(ApiParamUtil.deptListParam(BaseConfigModule.appInfo.areaParentId))
        }
        disposeList.apply {
            if (addAgree) add(DisposeBean(DisposeBean.DisposeType.Spinner, "处理结果", arrayListOf<DisposeBean.ValueBean>().apply {
                add(DisposeBean.ValueBean(yesText, "0", true))
                add(DisposeBean.ValueBean(noText, "1"))
            }, true, false))
            if (addDept) add(DisposeBean(DisposeBean.DisposeType.Spinner, "受理部门", arrayListOf()
                    , true))
            if (addDept) add(DisposeBean(DisposeBean.DisposeType.Spinner, "受理人", arrayListOf()
                    , true))
            add(DisposeBean(DisposeBean.DisposeType.Edit, "备注"))
        }
    }

    /**
     * 人员列表
     */
    override fun onUserListResult(userList: List<UserBean>) {
        val position = disposeAdapter.getItemPosition("受理人")
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
     * 部门列表
     */
    override fun onDeptListResult(deptList: List<DeptBean>) {
        val position = disposeAdapter.getItemPosition("受理部门")
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

    /**
     * 处置完成
     */
    override fun onDisposeResult() {
        UserActionTool.addUserAction(this, UserActionTool.ActionType.CaseLegal, detailBean.info.id)
        showToast("处置信息提交成功")
        setResult(0x01)
        finish()
    }

}
