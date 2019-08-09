package com.zx.marketnew_base.system.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.jpush.android.api.JPushInterface
import com.zx.marketnew_base.R
import com.zx.marketnew_base.system.mvp.contract.DevelopContract
import com.zx.marketnew_base.system.mvp.model.DevelopModel
import com.zx.marketnew_base.system.mvp.presenter.DevelopPresenter
import com.zx.module_library.BuildConfig
import com.zx.module_library.app.MyApplication
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.UserManager
import com.zx.zxutils.ZXApp
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.activity_develop.*


/**
 * Create By admin On 2017/7/11
 * 功能：开发者中心
 */
class DevelopActivity : BaseActivity<DevelopPresenter, DevelopModel>(), DevelopContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, DevelopActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_develop
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        if (mSharedPrefUtil.contains("openLog") && mSharedPrefUtil.getBool("openLog")) {
            switch_log.isChecked = true
        }

        //设置当前服务器ip
        val ip = mSharedPrefUtil.getString("base_ip", if (BuildConfig.isRelease) BuildConfig.RELEASE_URL else BuildConfig.DEBUG_URL)
        et_develop_ip.setText(ip.substring(7, ip.lastIndex))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        switch_log.setOnCheckedChangeListener { buttonView, isChecked ->
            mSharedPrefUtil.putBool("openLog", isChecked)
            ZXApp.init(MyApplication.instance, isChecked)
        }

        //ip切换
        tv_develop_saveip.setOnClickListener {
            ZXDialogUtil.showYesNoDialog(this@DevelopActivity, "提示", "是否切换IP？") { _, _ ->
                mSharedPrefUtil.putString("base_ip", "http://" + et_develop_ip.text.toString() + "/")
                MyApplication.instance.init()
                UserManager.loginOut()
                JPushInterface.stopPush(this)
                LoginActivity.startAction(this, false)
            }
        }

        //ip重置
        tv_develop_resetip.setOnClickListener {
            val ip = if (BuildConfig.isRelease) BuildConfig.RELEASE_URL else BuildConfig.DEBUG_URL
            et_develop_ip.setText(ip.substring(7, ip.lastIndex))
        }

        //退出开发者
        tv_develop_close.setOnClickListener {
            ZXDialogUtil.showYesNoDialog(this, "提示", "是否关闭开发者模式？") { _, _ ->
                mSharedPrefUtil.putBool("openDevelop", false)
                mSharedPrefUtil.putBool("openLog", false)
                mSharedPrefUtil.putString("base_ip", if (BuildConfig.isRelease) BuildConfig.RELEASE_URL else BuildConfig.DEBUG_URL)
                ZXApp.init(MyApplication.instance, false)

                finish()
            }
        }
    }

}
