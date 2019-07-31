package com.zx.module_other.module.infomation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.module.infomation.mvp.contract.InfomationContract
import com.zx.module_other.module.infomation.mvp.model.InfomationModel
import com.zx.module_other.module.infomation.mvp.presenter.InfomationPresenter


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
@Route(path = RoutePath.ROUTE_OTHER_INFOMATION)
class InfomationActivity : BaseActivity<InfomationPresenter, InfomationModel>(), InfomationContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, InfomationActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_infomation
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
