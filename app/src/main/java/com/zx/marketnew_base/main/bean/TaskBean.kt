package com.zx.marketnew_base.main.bean

/**
 * Updated by dell on 2019-03-14
 */
data class TaskBean(var ticker: String,
                    var isPush: Int,
                    var isRead: Int,
                    var title: String,
                    var userId: String,
                    var displayType: String,
                    var messageType: String,
                    var entityName: String,
                    var inserDate: Long,
                    var guid: String,
                    var entityGuid: String,
                    var text: String,
                    var taskId: String)
