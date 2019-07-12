package com.zx.module_other.api

import android.text.TextUtils
import android.util.Base64
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import org.w3c.dom.Text
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

    /**
     * 法律法规分类查询
     * @param departmentCode 科室id
     * @param id 用户id
     */
    fun lawSelectParam(departmentCode: String, id: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["departmentCode"] = departmentCode
        map["id"] = id
        return map
    }

    /**
     * 法律法规详情
     * @param id 法律法规id
     */
    fun lawDetailParam(id: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["id"] = id
        return map
    }

    /**
     * 法律法规搜索
     * @param content查询内容
     */
    fun lawSearchParam(content: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["content"] = content
        return map
    }

    /**
     * 法律法规添加收藏
     * @param lawMenuId
     * @param openId
     * @param name
     * @param type
     */
    fun lawAddCollectParam(lawMenuId: String, openId: String, name: String, type: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["lawMenuId"] = lawMenuId
        map["openId"] = openId
        map["name"] = name
        map[type] = type
        return map
    }

    /**
     * 删除收藏
     */
    fun lawDeleteCollectParam(id: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["id"] = id
        return map
    }

    /**
     * 我的收藏
     */
    fun lawMyCollectParam(openId: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["openId"] = openId
        return map
    }

    /**
     * 裁量标准
     */
    fun lawStandardParam(illegalAct: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["illegalAct"] = illegalAct
        map["pageNo"] = "1"
        map["pageSize"] = "8"
        return map
    }

    /**
     * 获取工作计划
     */
    fun workPlanParam(startDateMin: String, startDateMax: String): Map<String, String> {
        val map = hashMapOf<String, String>()
//        if (!TextUtils.isEmpty(startDateMin)) {
//            map["startDateMin"] = startDateMin
//        }
//        if (!TextUtils.isEmpty(startDateMax)) {
//            map["startDateMax"] = startDateMax
//        }
        return map;
    }

    /**
     * 创建工作计划
     */
    fun createWorkPlanParam(title: String, data: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["title"] = title
        map["data"] = data
        return map;
    }

    /**
     * 获取工作成果
     */
    fun workStatisicsPlanParam(monthNum: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["monthNum"] = monthNum
        return map;
    }
}