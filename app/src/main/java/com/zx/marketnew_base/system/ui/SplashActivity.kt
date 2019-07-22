package com.zx.marketnew_base.system.ui;

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.R
import com.zx.marketnew_base.api.ApiParamUtil
import com.zx.marketnew_base.main.ui.MainActivity
import com.zx.marketnew_base.system.mvp.contract.SplashContract
import com.zx.marketnew_base.system.mvp.model.SplashModel
import com.zx.marketnew_base.system.mvp.presenter.SplashPresenter
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.UserBean
import com.zx.module_library.func.tool.UserManager
import com.zx.zxutils.util.ZXImageLoaderUtil
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * Create By admin On 2017/7/11
 * 功能：欢迎页
 */
@Route(path = RoutePath.ROUTE_APP_SPLASH)
class SplashActivity : BaseActivity<SplashPresenter, SplashModel>(), SplashContract.View {

    override var canSwipeBack: Boolean = false

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, SplashActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        ZXImageLoaderUtil.display(iv_splash_logo, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551941647480&di=65f6890d593f6d148f53ff31ff96d009&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171118%2Fe24edb51cf474fe79f121d712a7e78ab.jpeg")
        if (UserManager.userName.isEmpty() || UserManager.passWord.isEmpty()) {
            //进入登录
            handler.postDelayed({
                LoginActivity.startAction(this, true)
            }, 1000)
        } else {
            //自动登录
            mPresenter.doLogin(ApiParamUtil.loginParam(UserManager.userName, UserManager.passWord))
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    /**
     * 登录返回
     */
    override fun onLoginResult(userBean: UserBean) {
        UserManager.setUser(userBean)
        BaseConfigModule.TOKEN = userBean.jwt
        MainActivity.startAction(this, true)
    }

    override fun onLoginError() {
        UserManager.loginOut()
        LoginActivity.startAction(this, true)
    }

}
