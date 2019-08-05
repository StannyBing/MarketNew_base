package com.zx.module_other.module.approval.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther

import com.zx.module_other.module.approval.mvp.contract.ApprovalContract
import com.zx.module_other.module.approval.mvp.model.ApprovalModel
import com.zx.module_other.module.approval.mvp.presenter.ApprovalPresenter
import kotlinx.android.synthetic.main.activity_approval.*


/**
 * Create By admin On 2017/7/11
 * 功能：审批
 */
@Route(path = RoutePath.ROUTE_OTHER_APPROVAL)
class ApprovalActivity : BaseActivity<ApprovalPresenter, ApprovalModel>(), ApprovalContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, ApprovalActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_approval
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar_view.withXApp(XAppOther.APPROVAL)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
