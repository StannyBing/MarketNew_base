package com.zx.module_supervise.module.manage.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.manage.mvp.contract.ManageDrugsContract
import com.zx.module_supervise.module.manage.mvp.model.ManageDrugsModel
import com.zx.module_supervise.module.manage.mvp.presenter.ManageDrugsPresenter
import kotlinx.android.synthetic.main.activity_manage_drugs.*


/**
 * Create By admin On 2017/7/11
 * 功能：药品安全监管
 */
@Route(path = RoutePath.ROUTE_SUPERVISE_MANAGE_DRUGS)
class ManageDrugsActivity : BaseActivity<ManageDrugsPresenter, ManageDrugsModel>(), ManageDrugsContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, ManageDrugsActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_manage_drugs
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar_view.withXApp(XAppSupervise.DRUGS)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
