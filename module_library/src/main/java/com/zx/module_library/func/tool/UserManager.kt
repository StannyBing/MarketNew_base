package com.zx.module_library.func.tool

import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.app.MyApplication
import com.zx.module_library.bean.UserBean
import com.zx.zxutils.util.ZXSharedPrefUtil

/**
 * Created by Xiangb on 2019/3/5.
 * 功能：用户管理器
 */
object UserManager {

    private var user: UserBean? = null

    var userName: String = ""
        set(value) {
            val sharedPref = MyApplication.instance.mSharedPrefUtil
            sharedPref.putString("m_username", value)
            field = value
        }
        get() {
            if (field.isEmpty()) {
                val sharedPref = MyApplication.instance.mSharedPrefUtil
                return sharedPref.getString("m_username")
            } else {
                return field
            }
        }

    var passWord: String = ""
        set(value) {
            val sharedPref = MyApplication.instance.mSharedPrefUtil
            sharedPref.putString("m_password", value)
            field = value
        }
        get() {
            if (field.isEmpty()) {
                val sharedPref = MyApplication.instance.mSharedPrefUtil
                return sharedPref.getString("m_password")
            } else {
                return field
            }
        }

    fun getUser(): UserBean {
        if (user == null) {
            val sharedPref = MyApplication.instance.mSharedPrefUtil
            user = sharedPref.getObject("userBean")
            if (user == null) {
                user = UserBean("", "")
            }
        }
        return user!!
    }

    fun setUser(userBean: UserBean) {
        user = userBean
        saveUser()
    }

    fun saveUser() {
        val sharedPref = MyApplication.instance.mSharedPrefUtil
        sharedPref.putObject("userBean", user)
    }

    fun loginOut() {
        if (ZXSharedPrefUtil().contains("openInterface") && ZXSharedPrefUtil().getBool("openInterface")) {
            ZXSharedPrefUtil().putString("request_list", "")
        }
        BaseConfigModule.TOKEN = ""
        passWord = ""
        saveUser()
    }

}