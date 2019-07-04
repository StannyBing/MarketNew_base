package com.zx.module_library.app

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
object ConstStrings {
    var ISRELEASE = false
    var code = ""
    var e = ""
    var usename = ""
    var AppCode = ""
    var INI_PATH = ""
    var APP_NAME = "ZHSQ"
    var DEVICE_TYPE = "android_pad"
    val APPNAME = "MarketMobile"
    val RESPONSE_SUCCESS = "1" // 请求成功
    val arcgisKey = "5SKIXc21JlankElJ"
    var LOCAL_PATH: String? = null

    fun getDatabasePath(): String {
        return "$LOCAL_PATH/$APPNAME/DATABASE/"
    }

    fun getCachePath(): String {
        return "$LOCAL_PATH/$APPNAME/SubmitFile/"
    }

    fun getZipPath(): String {
        return "$LOCAL_PATH/$APPNAME/.zip/"
    }

    fun getOnlinePath(): String {
        return "$LOCAL_PATH/$APPNAME/ONLINE/"
    }

    fun getCrashPath(): String {
        return "$LOCAL_PATH/$APPNAME/CRASH/"
    }

    fun getApkPath(): String {
        return "$LOCAL_PATH/$APPNAME/APK/"
    }

    fun getMainPath(): String {
        return "$LOCAL_PATH/$APPNAME"
    }

    fun getLocalPath(): String {
        return "$LOCAL_PATH/$APPNAME"
    }

    //地图默认中心点
    var Longitude = 106.496001
    var Latitude = 29.62016
    //定位时地图比例尺
    var LocationScale = 72142.9670553589

    //两点之间距离容差（在此距离内可视为同一点）
    var TolDistance = 1.0f

}