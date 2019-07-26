package com.zx.marketnew_base.main.bean

import java.io.Serializable

/**
 * Updated by dell on 2019-07-12
 */
data class OfficeBean(var banner: List<Banner>,
                      var notice: List<Notice>,
                      var todo: Todo,
                      var myXApp: List<String>,
                      var normalXApp: List<String>,
                      var allXApp: List<String>) : Serializable {

    data class Banner(var id: String,
                      var type: String,
                      var image: String,
                      var date: Long,
                      var name: String,
                      var info: String) : Serializable

    data class Notice(var id: String,
                      var type: String,
                      var image: String,
                      var date: Long,
                      var name: String,
                      var info: String) : Serializable

    data class Todo(var allTask: Int,
                    var willOverdue: Int,
                    var overdue: Int,
                    var caseNum: Int,
                    var complaintNum: Int,
                    var taskNum: Int) : Serializable
}