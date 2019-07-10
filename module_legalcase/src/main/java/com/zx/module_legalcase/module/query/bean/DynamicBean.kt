package com.zx.module_legalcase.module.query.bean

/**
 * Created by Xiangb on 2019/7/9.
 * 功能：
 */
data class DynamicBean(var id : String = "",
                       var caseId : String = "",
                       var name : String = "",
                       var process : String = "",
                       var handleUser : String = "",
                       var handleDate : Long? = 0L,
                       var handleUserId : String = "",
                       var acceptUserId : String = "",
                       var acceptUser : String = "",
                       var isAgree : Int = 0,
                       var remark : String = "",
                       var taskId : String = "") {
}