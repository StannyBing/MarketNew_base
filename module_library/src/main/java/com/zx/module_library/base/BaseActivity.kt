package com.zx.module_library.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import cn.jpush.android.api.JPushInterface
import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.RxBaseActivity
import com.tencent.bugly.crashreport.CrashReport
import com.zx.module_library.BuildConfig
import com.zx.module_library.XApp
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.app.MyApplication
import com.zx.module_library.app.RoutePath
import com.zx.module_library.func.tool.UserManager
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

    private lateinit var permessionBack: () -> Unit
    private lateinit var permissionArray: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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
                if (it.getString(JPushInterface.EXTRA_EXTRA).isNotEmpty() && JSONObject(it.getString(JPushInterface.EXTRA_EXTRA)).has("type")) {
                    val jsonObj = JSONObject(it.getString(JPushInterface.EXTRA_EXTRA))
                    when (jsonObj.getInt("type")) {

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
            JPushInterface.stopPush(this)
            UserManager.loginOut()
            XApp.startXApp(RoutePath.ROUTE_APP_LOGIN)
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