package com.zx.module_supervise.module.task.bean

/**
 * Created by Xiangb on 2019/7/21.
 * 功能：
 */
data class TaskCheckBean(var fId: String,
                         var fUpdateRemark: String? = null,
                         var fCheckResult: String? = null,
                         var fIsLeaf: String? = null,
                         var fValueMax: String? = null,
                         var pId: String? = null,
                         var fDel: String? = null,
                         var fNum: String? = null,
                         var fUpdateTime: String? = null,
                         var fName: String? = null,
                         var fValueType: String? = null,
                         var fUpdateUser: String? = null,
                         var fValueMin: String? = null,
                         var children: List<TaskCheckBean>? = null) {

    data class ResultBean(var fItemId : String, var fCheckResult : String)

}