package com.zx.module_complain.api

import android.util.Base64
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

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

    //投诉举报列表
    fun complainListParam(pageNo: Int, pageSize: Int = 15, fCondition: String = "", fType: String = "", fStatus: String = "", overdue: String = ""): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["pageNo"] = pageNo.toString()
        map["pageSize"] = pageSize.toString()
        map["queryType"] = "todo"
        if (fStatus.isNotEmpty()) map["fStatus"] = fStatus
        if (fCondition.isNotEmpty()) map["fCondition"] = fCondition
        if (fType.isNotEmpty()) map["fType"] = fType
        if (overdue.isNotEmpty()) map["overdue"] = overdue
        return map
    }

    //投诉举报详情
    fun detailParam(fGuid: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["fGuid"] = fGuid
        return map
    }

    //投诉处理-分流人员列表
    fun deptListParam(type: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["type"] = type
        return map
    }

    //投诉举报普通处理
    fun disposeParam(fGuid: String, fStatus: Int, fDispose: String, fDisposeRemark: String): RequestBody {
        val map = HashMap<String, String>()
        map["fGuid"] = fGuid
        map["fStatus"] = fStatus.toString()
        map["fDispose"] = fDispose
        map["fDisposeRemark"] = fDisposeRemark
        return toJson(map)
    }

    //投诉举报-指派人员列表
    fun userListParam(departmentCode: String, role: String): Map<String, String> {
        val map = HashMap<String, String>()
        map["departmentCode"] = departmentCode
        map["role"] = role
        return map
    }

    //投诉举报-分流
    fun shuntParam(fGuid: String, fStatus: Int, fDispose: String, fDisposeRemark: String, fShunt: String): RequestBody {
        val map = HashMap<String, String>()
        map["fGuid"] = fGuid
        map["fStatus"] = fStatus.toString()
        map["fDispose"] = fDispose
        map["fDisposeRemark"] = fDisposeRemark
        map["fShunt"] = fShunt
        return toJson(map)
    }

    //投诉举报-指派
    fun assignParam(fGuid: String, fStatus: Int, fDispose: String, fDisposeRemark: String, fDisposeUser: String): RequestBody {
        val map = HashMap<String, String>()
        map["fGuid"] = fGuid
        map["fStatus"] = fStatus.toString()
        map["fDispose"] = fDispose
        map["fDisposeRemark"] = fDisposeRemark
        map["fDisposeUser"] = fDisposeUser
        return toJson(map)
    }

    //投诉举报-联系
    fun contractParam(fGuid: String, fStatus: Int, fDispose: String, fDisposeRemark: String, fContactBool: String, fContactAvenue: String): RequestBody {
        val map = HashMap<String, String>()
        map["fGuid"] = fGuid
        map["fStatus"] = fStatus.toString()
        map["fDispose"] = fDispose
        map["fDisposeRemark"] = fDisposeRemark
        map["fContactBool"] = fContactBool
        map["fContactAvenue"] = fContactAvenue
        return toJson(map)
    }

    //投诉举报-处置
    fun handleParam(fGuid: String, fStatus: Int, fDispose: String, fMediationResult: String, fAcceptType: String, fFeedbackContent: String, fFilename: String): RequestBody {
        val map = HashMap<String, String>()
        map["fGuid"] = fGuid
        map["fStatus"] = fStatus.toString() + ""
        map["fDispose"] = fDispose
        map["fMediationResult"] = fMediationResult
        map["fAcceptType"] = fAcceptType
        map["fFeedbackContent"] = fFeedbackContent
        map["fFilename"] = fFilename
        return toJson(map)
    }

}