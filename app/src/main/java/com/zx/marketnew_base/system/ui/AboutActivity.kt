package com.zx.marketnew_base.system.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.R
import com.zx.marketnew_base.system.mvp.contract.AboutContract
import com.zx.marketnew_base.system.mvp.model.AboutModel
import com.zx.marketnew_base.system.mvp.presenter.AboutPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.zxutils.util.ZXSystemUtil
import kotlinx.android.synthetic.main.activity_about.*


/**
 * Create By admin On 2017/7/11
 * 功能：关于我们
 */
@Route(path = RoutePath.ROUTE_APP_SETTING_ABOUT)
class AboutActivity : BaseActivity<AboutPresenter, AboutModel>(), AboutContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, AboutActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        tv_about_versionCode.text = ZXSystemUtil.getVersionCode().toString()
        tv_about_versionName.text = ZXSystemUtil.getVersionName()
        tv_about_appname.text = ZXSystemUtil.getAppName()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
