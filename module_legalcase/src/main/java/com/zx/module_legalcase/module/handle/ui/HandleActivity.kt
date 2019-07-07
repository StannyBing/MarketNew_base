package com.zx.module_legalcase.module.handle.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_legalcase.R
import com.zx.module_legalcase.XAppLegalcase
import com.zx.module_legalcase.module.handle.mvp.contract.HandleContract
import com.zx.module_legalcase.module.handle.mvp.model.HandleModel
import com.zx.module_legalcase.module.handle.mvp.presenter.HandlePresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import kotlinx.android.synthetic.main.activity_query.*


/**
 * Create By admin On 2017/7/11
 * 功能：案件执法-流程办理
 */
@Route(path = RoutePath.ROUTE_LEGALASE_HANDLE)
class HandleActivity : BaseActivity<HandlePresenter, HandleModel>(), HandleContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, HandleActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_handle
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolBar_view.withXApp(XAppLegalcase.get("案件办理"))
        search_view.withXApp(XAppLegalcase.get("案件办理"))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
