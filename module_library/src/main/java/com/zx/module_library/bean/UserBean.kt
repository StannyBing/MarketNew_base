package com.zx.module_library.bean

import java.io.Serializable

/**
 * Updated by dell on 2019-03-05
 */
data class UserBean(
        var id: String,
        var userName: String,
        var listType: Int = 1,//0标题 1子项
        var oldPassword: String = "",
        var newPassword: String = "",
        var jwt: String = "",
        var loginTime: Long = 0,
        var logId: String = "",
        var password: String = "",
        var realName: String = "",
        var department: String = "",
        var departmentCode: String = "",
        var telephone: String = "",
        var officeTel: String = "",
        var station: String = "",
        var stationCode: String = "",
        var status: Int = 0,
        var remark: String = "",
        var userType: String = "",
        var imgUrl: String? = "",
        var role: List<String> = arrayListOf(),
        var module: List<String> = arrayListOf()) : Serializable