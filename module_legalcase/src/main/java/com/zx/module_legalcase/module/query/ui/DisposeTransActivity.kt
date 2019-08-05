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
import com.zx.module_legalcase.module.query.bean.DetailBean
import com.zx.module_legalcase.module.query.bean.DisposeBean
import com.zx.module_legalcase.module.query.func.adapter.DisposeAdapter
import com.zx.module_legalcase.module.query.mvp.contract.DisposeTransContract
import com.zx.module_legalcase.module.query.mvp.model.DisposeTransModel
import com.zx.module_legalcase.module.query.mvp.presenter.DisposeTransPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.UserActionTool
import com.zx.zxutils.other.ZXInScrollRecylerManager
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.activity_dispose_trans.*


/**
 * Create By admin On 2017/7/11
 * 功能：综合执法-案源移交
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_LEGALCASE_TASK_DISPOSETRANS)
class DisposeTransActivity : BaseActivity<DisposeTransPresenter, DisposeTransModel>(), DisposeTransContract.View {
    private lateinit var detailBean: DetailBean

    private val disposeList = arrayListOf<DisposeBean>()
    private val disposeAdapter = DisposeAdapter(disposeList)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, detailBean: DetailBean) {
            val intent = Intent(activity, DisposeTransActivity::class.java)
            intent.putExtra("detailBean", detailBean)
            activity.startActivityForResult(intent,0x01)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_dispose_trans
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        detailBean = intent.getSerializableExtra("detailBean") as DetailBean

        toolBar_view.withXApp(XAppLegalcase.HANDLE)
        btn_submit_trans.background.setTint(ContextCompat.getColor(this, XAppLegalcase.HANDLE.moduleColor))

        rv_dispose_trans.apply {
            layoutManager = ZXInScrollRecylerManager(this@DisposeTransActivity) as RecyclerView.LayoutManager?
            adapter = disposeAdapter.apply {
                setModuleColor(ContextCompat.getColor(this@DisposeTransActivity, XAppLegalcase.HANDLE.moduleColor))
            }
        }

        initItem()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //提交事件
        btn_submit_trans.setOnClickListener {
            if (disposeAdapter.checkItem()) {
                ZXDialogUtil.showYesNoDialog(this@DisposeTransActivity, "提示", "是否启动案源移交？") { _, _ ->
                    mPresenter.caseTrans(ApiParamUtil.caseTransParam(detailBean.info.id,
                            detailBean.info.processId,
                            disposeAdapter.getItem("移送部门")!!.resultValue,
                            disposeAdapter.getItem("移送文档")!!.resultValue,
                            disposeAdapter.getItem("移送方式")!!.resultValue,
                            System.currentTimeMillis().toString()))
                }
            }
        }
    }

    /**
     * 初始化item
     */
    private fun initItem() {
        disposeList.clear()
        disposeList.apply {
            add(DisposeBean(DisposeBean.DisposeType.Edit, "移送部门", isRequired = true))
            add(DisposeBean(DisposeBean.DisposeType.Edit, "移送方式", isRequired = true))
            add(DisposeBean(DisposeBean.DisposeType.Edit, "移送文档", isRequired = true))
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
