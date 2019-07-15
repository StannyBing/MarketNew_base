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
import com.zx.module_legalcase.module.query.mvp.contract.DisposeForceContract
import com.zx.module_legalcase.module.query.mvp.model.DisposeForceModel
import com.zx.module_legalcase.module.query.mvp.presenter.DisposeForcePresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.UserBean
import com.zx.module_library.func.tool.UserActionTool
import com.zx.zxutils.other.ZXInScrollRecylerManager
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.activity_dispose_force.*


/**
 * Create By admin On 2017/7/11
 * 功能：综合执法处置-强制措施
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_LEGALCASE_DISPOSEFORCE)
class DisposeForceActivity : BaseActivity<DisposeForcePresenter, DisposeForceModel>(), DisposeForceContract.View {
    private lateinit var detailBean: DetailBean

    private val disposeList = arrayListOf<DisposeBean>()
    private val disposeAdapter = DisposeAdapter(disposeList)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, detailBean: DetailBean) {
            val intent = Intent(activity, DisposeForceActivity::class.java)
            intent.putExtra("detailBean", detailBean)
            activity.startActivityForResult(intent, 0x01)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_dispose_force
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        detailBean = intent.getSerializableExtra("detailBean") as DetailBean

        toolBar_view.setMidText("任务处理-${detailBean.info.compelStatusName}")
        toolBar_view.withXApp(XAppLegalcase.get("综合执法"))
        btn_submit_force.background.setTint(ContextCompat.getColor(this, XAppLegalcase.get("综合执法")!!.moduleColor))

        rv_dispose_force.apply {
            layoutManager = ZXInScrollRecylerManager(this@DisposeForceActivity) as RecyclerView.LayoutManager?
            adapter = disposeAdapter.apply {
                setModuleColor(ContextCompat.getColor(this@DisposeForceActivity, XAppLegalcase.get("综合执法")!!.moduleColor))
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
            if (rv_dispose_force.isComputingLayout) {
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
        btn_submit_force.setOnClickListener {
            if (disposeAdapter.checkItem()) {
                ZXDialogUtil.showYesNoDialog(this@DisposeForceActivity, "提示", "是否提交任务处理信息？") { _, _ ->
                    when (detailBean.info.compelStatus) {
                        "QZ01" -> {//承办人审核
                            mPresenter.doForceCRBaudit(ApiParamUtil.caseForceSimpleParam(detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "QZ02" -> {//机构负责人审核
                            mPresenter.doForceFZRaudit(ApiParamUtil.caseForceWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "QZ03" -> {//局领导审核
                            mPresenter.doForceJLDaudit(ApiParamUtil.caseForceWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        "QZ04" -> {//承办人处置
                            mPresenter.doForceCBRexecute(ApiParamUtil.caseForceWithAgreeParam(disposeAdapter.getItem("处理结果")!!.resultValue,
                                    detailBean.info.id,
                                    detailBean.info.taskId,
                                    disposeAdapter.getItem("受理人")!!.resultValue,
                                    disposeAdapter.getItem("受理人")!!.resultKey,
                                    disposeAdapter.getItem("备注")!!.resultValue))
                        }
                        else -> {
                            mPresenter.doForceStart(ApiParamUtil.caseForceStartParam(detailBean.info.id,
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
        var isAgree = true
        var addDept = true
        when (detailBean.info.compelStatus) {
            "QZ01" -> {//承办人审核
                isAgree = false
            }
            "QZ02" -> {//机构负责人审核
                isAgree = false
            }
            "QZ03" -> {//局领导审核
            }
            "QZ04" -> {//承办人处置
            }
            else -> {//启动
                isAgree = false
                addDept = false
            }
        }
        mPresenter.getDeptList(ApiParamUtil.deptListParam("360481"))
        disposeList.apply {
            if (isAgree) add(DisposeBean(DisposeBean.DisposeType.Spinner, "处理结果", arrayListOf<DisposeBean.ValueBean>().apply {
                add(DisposeBean.ValueBean("同意", "0", true))
                add(DisposeBean.ValueBean("不同意", "1"))
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
