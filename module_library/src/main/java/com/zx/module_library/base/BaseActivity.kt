package com.zx.module_library.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import cn.jpush.android.api.JPushInterface
import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.RxBaseActivity
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.tencent.bugly.crashreport.CrashReport
import com.zx.module_library.BuildConfig
import com.zx.module_library.R
import com.zx.module_library.XApp
import com.zx.module_library.app.*
import com.zx.module_library.bean.UserBean
import com.zx.module_library.func.tool.UserManager
import com.zx.module_library.func.tool.toJson
import com.zx.zxutils.util.*
import com.zx.zxutils.views.SwipeBack.ZXSwipeBackHelper
import com.zx.zxutils.views.ZXStatusBarCompat
import org.json.JSONObject
import rx.functions.Action1

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
abstract class BaseActivity<T : BasePresenter<*, *>, E : BaseModel> : RxBaseActivity<T, E>() {

    val mSharedPrefUtil = ZXSharedPrefUtil()
    val handler = Handler()
    var sIsLoginClear = false
    open var canSwipeBack = true

    private var permessionBack: () -> Unit = {}
    private var permissionArray: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isTranslucent()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        if (canSwipeBack) ZXSwipeBackHelper.onCreate(this)
                .setSwipeBackEnable(true)
                .setSwipeRelateEnable(true)
        MyApplication.instance.addActivity(this)
        ZXStatusBarCompat.setStatusBarLightMode(this)
        ZXCrashUtil.init(BuildConfig.isRelease) { t, e ->
            ZXLogUtil.loge(e.message)
            CrashReport.postCatchedException(e)
        }

        mRxManager.on("jPush", Action1<Bundle> {
            try {
                if (!isTopActivity()) {
                    return@Action1
                }
                mSharedPrefUtil.putBool("isGetPush", false)
                if (it.getString(JPushInterface.EXTRA_EXTRA).isNotEmpty() && JSONObject(it.getString(JPushInterface.EXTRA_EXTRA)).has("business")) {
                    val jsonObj = JSONObject(it.getString(JPushInterface.EXTRA_EXTRA))
                    when (jsonObj.getInfo("business")) {
                        "专项检查" -> {
                            ZXDialogUtil.showYesNoDialog(this, it.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE), it.getString(JPushInterface.EXTRA_ALERT), "前往处理", "关闭", DialogInterface.OnClickListener { dialog: DialogInterface?, _: Int ->
                                XApp.startXApp(RoutePath.ROUTE_SUPERVISE_TASK_DETAIL) {
                                    it["id"] = jsonObj.getString("businessId")
                                    it["taskId"] = jsonObj.getString("taskId")
                                    it["optable"] = true
                                }
                            }, null)
                            return@Action1
                        }
                        "投诉举报" -> {
                            ZXDialogUtil.showYesNoDialog(this, it.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE), it.getString(JPushInterface.EXTRA_ALERT), "前往处理", "关闭", DialogInterface.OnClickListener { dialog: DialogInterface?, _: Int ->
                                XApp.startXApp(RoutePath.ROUTE_COMPLAIN_TASK_DETAIL) {
                                    it["fGuid"] = jsonObj.getInfo("businessId")
                                }
                            }, null)
                            return@Action1
                        }
                        "综合执法" -> {
                            ZXDialogUtil.showYesNoDialog(this, it.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE), it.getString(JPushInterface.EXTRA_ALERT), "前往处理", "关闭", DialogInterface.OnClickListener { dialog: DialogInterface?, _: Int ->
                                XApp.startXApp(RoutePath.ROUTE_LEGALCASE_TASK_DETAIL) {
                                    it["id"] = jsonObj.getInfo("businessId")
                                    it["taskId"] = jsonObj.getInfo("taskId")
                                    it["optable"] = true
                                    it["processType"] = jsonObj.getInfo("processType")
                                }
                            }, null)
                            return@Action1
                        }
                        "版本更新" -> {
                            XApp.startXApp(RoutePath.ROUTE_APP_SETTING){
                                it["checkVerson"] = true
                            }
                        }
                    }
                }
                if (it.getString(JPushInterface.EXTRA_MESSAGE) != null && !it.getString(JPushInterface.EXTRA_MESSAGE).isEmpty()) {
                    ZXDialogUtil.showInfoDialog(this, it.getString(JPushInterface.EXTRA_TITLE), it.getString(JPushInterface.EXTRA_MESSAGE))
                } else {
                    ZXDialogUtil.showInfoDialog(this, it.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE), it.getString(JPushInterface.EXTRA_ALERT))
                }
            } catch (e: Exception) {
                if (it.getString(JPushInterface.EXTRA_MESSAGE) != null && !it.getString(JPushInterface.EXTRA_MESSAGE).isEmpty()) {
                    ZXDialogUtil.showInfoDialog(this, it.getString(JPushInterface.EXTRA_TITLE), it.getString(JPushInterface.EXTRA_MESSAGE))
                } else {
                    ZXDialogUtil.showInfoDialog(this, it.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE), it.getString(JPushInterface.EXTRA_ALERT))
                }
            }
        })
    }

    private fun JSONObject.getInfo(key: String): String {
        if (has(key)) {
            return getString(key)
        } else {
            return ""
        }
    }

    private fun isTranslucent(): Boolean {
        var isTranslucent = false
        try {
            val styleable = Class.forName("com.android.internal.R" + "$" + "styleable").getField("Window").get(null) as IntArray
            val ta = obtainStyledAttributes(styleable)
            val m = ActivityInfo::class.java.getMethod("isTranslucentOrFloating", TypedArray::class.java)
            m.isAccessible = true
            isTranslucent = m.invoke(null, ta) as Boolean
            m.isAccessible = false
        } catch (e: java.lang.Exception) {

        }
        return isTranslucent && Build.VERSION.SDK_INT == Build.VERSION_CODES.O
    }

    /**
     * 判断当前activity是否在栈顶，避免重复处理
     */
    private fun isTopActivity(): Boolean {
        var isTop = false
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cn = am.getRunningTasks(1)[0].topActivity
        if (cn.className.contains(this.localClassName)) {
            isTop = true
        }
        return isTop
    }


    override fun handleError(code: String?, message: String) {
        showToast(message)
        if (code == "10120") {//未登录或登录超时
            UserManager.loginOut()
//            JPushInterface.stopPush(this)
            XApp.startXApp(RoutePath.ROUTE_APP_LOGIN){
                it["reLogin"] = true
            }
//            showLoginDialog()
        } else if (code == "10000") {//系统错误
            showToast("系统超时，即将重新登录")
            handler.postDelayed({
                BaseConfigModule.TOKEN = ""
                XApp.startXApp(RoutePath.ROUTE_APP_LOGIN) {
                    it["reLogin"] = true
                }
            }, 1000)
        }
    }

    private fun showLoginDialog() {
        val loginView = LayoutInflater.from(this).inflate(R.layout.layout_re_login, null, false)
        val etUserName = loginView.findViewById<EditText>(R.id.et_relogin_phone)
        val etPassword = loginView.findViewById<EditText>(R.id.et_relogin_pwd)
        val btnLogin = loginView.findViewById<Button>(R.id.btn_relogin_do)
        val ivClose = loginView.findViewById<ImageView>(R.id.iv_relogin_cancel)
        ivClose.setOnClickListener {
            ZXDialogUtil.dismissDialog()
            JPushInterface.stopPush(this)
            UserManager.loginOut()
            XApp.startXApp(RoutePath.ROUTE_APP_LOGIN)
        }
        etUserName.setText(UserManager.userName)
//        etPassword.setText(UserManager.passWord)
        btnLogin.setOnClickListener {
            MyApplication.instance.component.repositoryManager()
                    .obtainRetrofitService(ApiService::class.java)
                    .doLogin(hashMapOf("userName" to etUserName.text.toString(), "password" to ApiParamUtil.getBase64(etPassword.text.toString()), "remark" to "app").toJson())
                    .compose(RxHelper.handleResult())
                    .subscribe(object : RxSubscriber<UserBean>() {
                        override fun onStart() {
                            super.onStart()
                            showLoading("正在登录...")
                        }

                        override fun _onNext(t: UserBean?) {
                            showToast("登录成功")
                            if (t != null) {
                                BaseConfigModule.TOKEN = t.jwt
                                UserManager.setUser(t)
                                showToast("登录成功")
                                ZXDialogUtil.dismissDialog()
                                ZXDialogUtil.dismissLoadingDialog()
                            }
                        }

                        override fun _onError(code: String?, message: String?) {
                            showToast("登录失败")
                            ZXDialogUtil.dismissLoadingDialog()
                        }

                    })
        }
        ZXDialogUtil.showCustomViewDialog(this, "", loginView, null).apply {
            window.setDimAmount(0.2f)
            window.setBackgroundDrawableResource(R.color.transparent)
        }
    }

    override fun showToast(message: String) {
        ZXToastUtil.showToast(message)
    }

    override fun showLoading(message: String) {
        ZXDialogUtil.showLoadingDialog(this, message)
    }

    override fun dismissLoading() {
        ZXDialogUtil.dismissLoadingDialog()
    }


    override fun showLoading(message: String, progress: Int) {
        handler.post {
            ZXDialogUtil.showLoadingDialog(this, message, progress)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        onViewListener()
    }

    abstract fun onViewListener()

    fun getPermission(permissionArray: Array<String>, permessionBack: () -> Unit) {
        this.permessionBack = permessionBack
        this.permissionArray = permissionArray
        if (permissionArray.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            if (!ZXLocationUtil.isLocationEnabled()) {
                ZXLocationUtil.openGPS(this)
                return
            }
        }
        if (!ZXPermissionUtil.checkPermissionsByArray(permissionArray)) {
            ZXPermissionUtil.requestPermissionsByArray(this)
        } else {
            this.permessionBack()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionArray == null) {
            return
        }
        if (ZXPermissionUtil.checkPermissionsByArray(permissionArray)) {
            permessionBack()
        } else {
            showToast("未获取到权限！")
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (canSwipeBack) ZXSwipeBackHelper.onPostCreate(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (supportFragmentManager.fragments.isNotEmpty()) {
            supportFragmentManager.fragments.forEach {
                it.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}