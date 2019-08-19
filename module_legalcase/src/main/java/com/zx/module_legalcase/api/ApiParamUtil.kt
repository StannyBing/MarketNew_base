package com.zx.module_legalcase.api

import android.util.Base64
import com.google.gson.Gson
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

    //我的案件
    fun caseListParam(pageNo: Int, pageSize: Int, process: String, condition: String, domainCode: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["pageNo"] = pageNo.toString()
        map["pageSize"] = pageSize.toString()
        map["orderByClause"] = "found_date desc"
        if (process.isNotEmpty()) map["process"] = process
        if (domainCode.isNotEmpty()) map["domainCode"] = domainCode
        if (condition.isNotEmpty()) map["condition"] = condition
        return map
    }

    fun workCaseListParam(pageNo: Int, pageSize: Int, condition: String, monthNum: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["pageNo"] = pageNo.toString()
        map["pageSize"] = pageSize.toString()
        map["orderByClause"] = "found_date desc"
        map["queryType"] = "workResult"
        map["monthNum"] = monthNum
        if (condition.isNotEmpty()) map["condition"] = condition
        return map
    }

    //详情
    fun detailParam(id: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["id"] = id
        return map
    }

    //流程轨迹
    fun dynamicParam(caseId: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["caseId"] = caseId
        map["pageNo"] = 1.toString()
        map["pageSize"] = 999.toString()
        return map
    }

    //部门列表
    fun deptListParam(parentId: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["parentId"] = parentId
        return map
    }

    //人员列表
    fun userListParam(departmentCode: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["departmentCode"] = departmentCode
        return map
    }

    fun caseStartParam(caseId: String, remark: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["caseId"] = caseId
        map["remark"] = remark
        return toJson(map)
    }

    fun caseDisposeWithAgreeParam(isAgree: String, caseId: String, taskId: String?, acceptUserId: String, acceptUser: String, remark: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["isAgree"] = isAgree
        map["caseId"] = caseId
        map["taskId"] = taskId ?: ""
        map["acceptUserId"] = acceptUserId
        map["acceptUser"] = acceptUser
        map["remark"] = remark
        return toJson(map)
    }

    fun caseDisposeSimpleParam(caseId: String, taskId: String?, acceptUserId: String, acceptUser: String, remark: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["caseId"] = caseId
        map["taskId"] = taskId ?: ""
        map["acceptUserId"] = acceptUserId
        map["acceptUser"] = acceptUser
        map["remark"] = remark
        return toJson(map)
    }

    fun caseEndParam(caseId: String, taskId: String?, remark: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["caseId"] = caseId
        map["taskId"] = taskId ?: ""
        map["remark"] = remark
        return toJson(map)
    }

    fun caseEasyStartParam(caseId: String, remark: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["caseId"] = caseId
        map["remark"] = remark
        return toJson(map)
    }

    fun caseEasyAuditingParam(isAgree: String, caseId: String, taskId: String?, acceptUserId: String, acceptUser: String, remark: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["isAgree"] = isAgree
        map["caseId"] = caseId
        map["taskId"] = taskId ?: ""
        map["acceptUserId"] = acceptUserId
        map["acceptUser"] = acceptUser
        map["remark"] = remark
        return toJson(map)
    }

    fun caseTransParam(caseId: String, processId: String, department: String, doc: String, transferType: String, transferDate: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["caseId"] = caseId
        map["processId"] = processId
        map["department"] = department
        map["doc"] = doc
        map["transferType"] = transferType
        map["transferDate"] = transferDate
        return toJson(map)
    }

    fun caseForceStartParam(caseId: String, remark: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["caseId"] = caseId
        map["remark"] = remark
        return toJson(map)
    }

    fun caseForceWithAgreeParam(isAgree: String, caseId: String, taskId: String?, acceptUserId: String, acceptUser: String, remark: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["isAgree"] = isAgree
        map["caseId"] = caseId
        map["taskId"] = taskId ?: ""
        map["acceptUserId"] = acceptUserId
        map["acceptUser"] = acceptUser
        map["remark"] = remark
        return toJson(map)
    }

    fun caseForceSimpleParam(caseId: String, taskId: String?, acceptUserId: String, acceptUser: String, remark: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["caseId"] = caseId
        map["taskId"] = taskId ?: ""
        map["acceptUserId"] = acceptUserId
        map["acceptUser"] = acceptUser
        map["remark"] = remark
        return toJson(map)
    }
}