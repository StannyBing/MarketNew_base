package com.zx.module_entity.api

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

    //搜索
    fun searchParam(pageNo: Int, pageSize: Int, fCondition: String = "", positionList: String = "", radius: String = "", fTags: String = "", fCreditLevel: String = "", fStatus: String = "", fStation: String = "", fGrid : String = ""): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["pageNo"] = pageNo.toString()
        map["pageSize"] = pageSize.toString()
//        if (positionList.isEmpty()) {
            if (fCondition.isNotEmpty()) map["fCondition"] = fCondition
            if (fTags.isNotEmpty()) map["fTags"] = fTags
            if (fCreditLevel.isNotEmpty()) map["fCreditLevelList"] = fCreditLevel
            if (fStatus.isNotEmpty()) map["fStatus"] = fStatus
            if (fStation.isNotEmpty()) map["fStation"] = fStation
            if (fGrid.isNotEmpty()) map["fGrid"] = fGrid
//        } else {
            map["positionList"] = positionList
            map["radius"] = radius
//        }
        return map
    }

    fun tagListParam(dicType: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["dicType"] = dicType
        return map
    }

    fun entityStationParam(parentId: String): Map<String, String> {
        val map = hashMapOf<String, String>()
        map["parentId"] = parentId
        return map
    }

    fun infoModifyParam(fEntityGuid: String, contactName: String = "", fContactPhone: String = "", fContactAddress: String = ""): RequestBody {
        val map = hashMapOf<String, String>()
        map["fEntityGuid"] = fEntityGuid
        if (contactName.isNotEmpty()) map["fContactPeople"] = contactName
        if (fContactPhone.isNotEmpty()) map["fContactPhone"] = fContactPhone
        if (fContactAddress.isNotEmpty()) map["fContactAddress"] = fContactAddress
        return toJson(map)
    }

    fun updateEntityTags(fEntityGuid: String, fTags: String): RequestBody {
        val map = hashMapOf<String, String>()
        map["fEntityGuid"] = fEntityGuid
        map["fTags"] = fTags
        return toJson(map)
    }

}