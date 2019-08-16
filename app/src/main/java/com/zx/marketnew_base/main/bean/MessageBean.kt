package com.zx.marketnew_base.main.bean

/**
 * Created by Xiangb on 2019/6/17.
 * 功能：
 */
data class MessageBean(var ticker: String,
                       var isPush: Int,
                       var isRead: Int,
                       var title: String,
                       var userId: String,
                       var displayType: String,
                       var messageType: String?,
                       var entityName: String,
                       var inserDate: Long,
                       var guid: String,
                       var entityGuid: String,
                       var text: String?,
                       var taskId: String){
    data class TextBean(var taskId : String,
                        var entityGuid: String,
                        var business : String,
                        var businessId : String,
                        var processType : String)
}