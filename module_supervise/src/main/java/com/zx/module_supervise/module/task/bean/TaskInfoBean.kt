package com.zx.module_supervise.module.task.bean

/**
 * Created by Xiangb on 2019/7/21.
 * 功能：
 */
data class TaskInfoBean(var fId: String? = null,
                        var fName: String? = null,
                        var fSource: String? = null,
                        var fNum: String? = null,
                        var fDispatchUserId: String? = null,
                        var fDispatchUser: String? = null,
                        var fDispatchTime: Long = 0,
                        var fStartDate: Long? = 0,
                        var fEndDate: Long = 0,
                        var fTipsTime: Long = 0,
                        var fOverdue: String? = null,
                        var fReamrks: String? = null,
                        var fConditions: String? = null,
                        var fStatus: String,
                        var fHandleDepartmentId: String? = null,
                        var fHandleDepartment: String? = null,
                        var fLeaderUserId: String? = null,
                        var fLeaderUser: String? = null,
                        var fReviewUserId: String? = null,
                        var fReviewUser: String? = null) {

}