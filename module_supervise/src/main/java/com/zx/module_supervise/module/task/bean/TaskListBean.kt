package com.zx.module_supervise.module.task.bean

/**
 * Updated by dell on 2019-07-23
 */
data class TaskListBean(var fId: String,
                        var fHandleUser: String,
                        var fName: String,
                        var fEntityGuid: String,
                        var fHandleUserId: String,
                        var fStatus: String,
                        var fQualify: String,
                        var fEntityId: String,
                        var fTaskId: String,
                        var fStartDate: Long?,
                        var fEntityName: String,
                        var fEndDate: Long)