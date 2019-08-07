package com.zx.marketnew_base.system.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.R
import com.zx.marketnew_base.api.ApiParamUtil
import com.zx.marketnew_base.main.ui.MainActivity
import com.zx.marketnew_base.system.mvp.contract.LoginContract
import com.zx.marketnew_base.system.mvp.model.LoginModel
import com.zx.marketnew_base.system.mvp.presenter.LoginPresenter
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.app.MyApplication
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.UserBean
import com.zx.module_library.func.tool.UserManager
import com.zx.zxutils.util.ZXSystemUtil
import kotlinx.android.synthetic.main.activity_login.*


/**
 * Create By admin On 2017/7/11
 * 功能：登录
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_APP_LOGIN)
class LoginActivity : BaseActivity<LoginPresenter, LoginModel>(), LoginContract.View {

    override var canSwipeBack: Boolean = false
    private var reLogin = false

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        //设置手机号
        et_login_phone.setText(UserManager.userName)

        //设置用户名
        tv_login_user.text = if (UserManager.getUser().realName.isEmpty()) {
            "Hello!"
        } else {
            "Hello!${UserManager.getUser().realName}"
        }

        btn_login_do.background.setTint(ContextCompat.getColor(this, R.color.colorPrimary))

        reLogin = if (intent.hasExtra("reLogin")) intent.getBooleanExtra("reLogin", false) else false
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //登录
        btn_login_do.setOnClickListener {
            if (et_login_phone.text.isEmpty()) {
                showToast("请输入用户名")
            } else if (et_login_pwd.text.isEmpty()) {
                showToast("请输入密码")
            } else {
                mPresenter.doLogin(ApiParamUtil.loginParam(et_login_phone.text.toString(), et_login_pwd.text.toString()))
            }
            try {
                ZXSystemUtil.closeKeybord(this)
            } catch (e: Exception) {
            }
        }
        //忘记密码
        tv_login_forgetpwd.setOnClickListener { ForgetPwdActivity.startAction(this, false, et_login_phone.text.toString()) }
    }

    /**
     * 登录返回
     */
    override fun onLoginResult(userBean: UserBean) {
        BaseConfigModule.TOKEN = userBean.jwt
        UserManager.userName = et_login_phone.text.toString()
        UserManager.passWord = et_login_pwd.text.toString()
        userBean.password = et_login_pwd.text.toString()
        UserManager.setUser(userBean)
        showToast("登录成功")
        if (reLogin) {
            finish()
        } else {
            MainActivity.startAction(this, true)
        }
    }

    override fun onLoginError() {
        UserManager.loginOut()
    }

    override fun onBackPressed() {
        if (reLogin) {
            JPushInterface.stopPush(this)
            showToast("请先登录后使用")
            handler.postDelayed({
                MyApplication.instance.exit()
            }, 1000)
        } else {
            super.onBackPressed()
        }
    }
}
