package com.zx.module_library

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.zx.module_library.bean.XAppBean
import java.io.Serializable

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
abstract class XApp {


    companion object {
        /**
         * 打开Xapp
         */
        fun startXApp(xAppBean: XAppBean, activity: Activity? = null, requestCode: Int = 0, info: (HashMap<String, Any>) -> Unit = {}): Any? {
            val map = hashMapOf<String, Any>()
            info(map)
            val postcard = ARouter.getInstance().build(xAppBean.appRoutePath)
            if (map.isNotEmpty()) {
                map.forEach {
                    if (it.value is String) {
                        postcard.withString(it.key, it.value as String)
                    } else if (it.value is Int) {
                        postcard.withInt(it.key, it.value as Int)
                    } else if (it.value is Double) {
                        postcard.withDouble(it.key, it.value as Double)
                    } else if (it.value is Boolean) {
                        postcard.withBoolean(it.key, it.value as Boolean)
                    } else if (it.value is Serializable) {
                        postcard.withSerializable(it.key, it.value as Serializable)
                    } else {
                        postcard.withObject(it.key, it.value)
                    }
                }
            }
            postcard.withSerializable("xApp", xAppBean)
            if (activity == null) {
                return postcard.navigation()
            } else {
                return postcard.navigation(activity, requestCode)
            }
        }

        fun startXApp(path: String, activity: Activity? = null, requestCode: Int = 0, info: (HashMap<String, Any>) -> Unit = {}): Any? {
            val map = hashMapOf<String, Any>()
            info(map)
            val postcard = ARouter.getInstance().build(path)
            if (map.isNotEmpty()) {
                map.forEach {
                    if (it.value is String) {
                        postcard.withString(it.key, it.value as String)
                    } else if (it.value is Int) {
                        postcard.withInt(it.key, it.value as Int)
                    } else if (it.value is Double) {
                        postcard.withDouble(it.key, it.value as Double)
                    } else if (it.value is Boolean) {
                        postcard.withBoolean(it.key, it.value as Boolean)
                    } else if (it.value is Serializable) {
                        postcard.withSerializable(it.key, it.value as Serializable)
                    } else {
                        postcard.withObject(it.key, it.value)
                    }
                }
            }
            if (activity == null) {
                return postcard.navigation()
            } else {
                return postcard.navigation(activity, requestCode)
            }
        }
    }

    /**
     * 获取单个
     */
    fun get(name: String): XAppBean? {
        if (all().isNotEmpty()) {
            all().forEach {
                if (it.name == name) {
                    return it
                }
            }
        }
        return null
    }

    /**
     * 获取全部
     */
    abstract fun all(): ArrayList<XAppBean>
}