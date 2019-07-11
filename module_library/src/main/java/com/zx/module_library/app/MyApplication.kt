package com.zx.module_library.app

import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.support.multidex.MultiDex
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.launcher.ARouter
import com.frame.zxmvp.baseapp.BaseApplication
import com.frame.zxmvp.di.component.AppComponent
import com.github.moduth.blockcanary.BlockCanary
import com.github.moduth.blockcanary.BlockCanaryContext
import com.tencent.bugly.crashreport.CrashReport
import com.zx.module_library.BuildConfig
import com.zx.zxutils.ZXApp
import com.zx.zxutils.util.ZXSharedPrefUtil
import com.zx.zxutils.util.ZXSystemUtil


/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
open class MyApplication : BaseApplication() {

    companion object {
        lateinit var instance: MyApplication

//        fun getInstance(): MyApplication {
//            return
//        }
    }

    lateinit var mSharedPrefUtil: ZXSharedPrefUtil

    lateinit var mContest: Context

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        //配置ZXUtils
        ZXApp.init(this, !BuildConfig.isRelease)
        //配置阿里巴巴路由
        if (!BuildConfig.isRelease) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        //配置极光推送
        JPushInterface.setDebugMode(!BuildConfig.isRelease)
        JPushInterface.init(this)//初始化JPush
        JPushInterface.setLatestNotificationNumber(this, 1)
        //配置Bugly
        if (BuildConfig.isRelease) {
            CrashReport.initCrashReport(this, "cb2f1fd59d", false)
        }
        mSharedPrefUtil = ZXSharedPrefUtil()
        instance = this
        mContest = this
        component = BaseApplication.baseApplication.appComponent

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
        }

        // 在主进程初始化调用
        BlockCanary.install(this, BlockCanaryContext()).start()

        //初始化
        ConstStrings.LOCAL_PATH = ZXSystemUtil.getSDCardPath()
        ConstStrings.INI_PATH = filesDir.path
        //Bugly 热更新
        // 调试时，将第三个参数改为true
       // Bugly.init(this, "cb2f1fd59d", true);
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}