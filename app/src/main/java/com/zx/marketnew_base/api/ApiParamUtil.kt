package com.zx.marketnew_base.api

import android.util.Base64
import com.google.gson.Gson
import com.zx.module_library.app.ApiParamUtil
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
object ApiParamUtil {

    fun toObjectJson(map: Map<String, Any>): RequestBody {
        val json = Gson().toJson(map)
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
    }

    fun toJson(map: Map<String, String>): RequestBody {
        val json = Gson().toJson(map)
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
    }

    fun getBase64(password: String): String {
        try {
            return Base64.encodeToString(MessageDigest.getInstance("MD5")
                    .digest(password.toByteArray(charset("utf-8"))), Base64.NO_WRAP)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return ""
    }

    //验证码
    fun verifiCodeParam(phone: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["telephone"] = phone
        return map
    }

    //登录
    fun loginParam(userName: String, password: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["userName"] = userName
        map["password"] = ApiParamUtil.getBase64(password)
        return ApiParamUtil.toJson(map)
    }

    //修改密码
    fun changePwdParam(id: String, newPasssword: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["id"] = id
        map["password"] = ApiParamUtil.getBase64(newPasssword)
        return toJson(map)
    }

    //消息列表
    fun messageListParam(userId: String, pageNo: Int, searchText: String = "", pageSize: Int = 15): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["userId"] = userId
        map["pageNo"] = pageNo.toString()
        map["pageSize"] = pageSize.toString()
        if (searchText.isNotEmpty()) map["entityName"] = searchText
        return map
    }

    //消息列表
    fun taskListParam(userId: String, pageNo: Int, pageSize: Int = 15): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["userId"] = userId
        map["pageNo"] = pageNo.toString()
        map["pageSize"] = pageSize.toString()
        return map
    }

    //添加xapp操作日志
    fun xappOptParam(sysId: String, firstModule: String, secendModule: String, threeModule: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["sysId"] = sysId
        map["firstModule"] = firstModule
        map["secendModule"] = secendModule
        map["threeModule"] = threeModule
        return toJson(map)
    }

}