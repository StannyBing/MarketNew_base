package com.zx.module_legalcase.module.query.bean

import java.io.Serializable

/**
 * Updated by dell on 2019-07-08
 */
data class DetailBean(var info: InfoBean, var files: List<FileBean>?) : Serializable {
    data class InfoBean(var createUserId: String,
                        var endDate: String?,
                        var departmentId: String,
                        var enterpriseCreditCode: String?,
                        var caseName: String,
                        var id: String,
                        var sysRegUser: String,
                        var disOpinion: String?,
                        var overdueTime: Long?,
                        var enterpriseBizlicNum: String,
                        var longitude: Double?,
                        var departmentName: String,
                        var sysDisUser: String?,
                        var caseNum: String,
                        var updateUserId: String,
                        var foundDate: Long?,
                        var isPause: String?,
                        var completedDate: String?,
                        var enterprisePerson: String,
                        var isExpand: String?,
                        var checkTime: Long,
                        var status: String,
                        var provideId: String?,
                        var provideAddress: String?,
                        var updateDate: Long,
                        var latitude: Double?,
                        var typeName: String,
                        var regDate: Long?,
                        var provideName: String,
                        var provideContact: String?,
                        var regUser: String?,
                        var disDate: String?,
                        var sysDisUserId: String?,
                        var enterpriseContact: String?,
                        var processId: String,
                        var isExpandCheck: String?,
                        var enterpriseAddress: String,
                        var statusName: String,
                        var caseTime: String?,
                        var isCase: Int,
                        var enterpriseName: String,
                        var createDate: Long,
                        var disUser: String?,
                        var updateUser: String,
                        var typeCode: String,
                        var isOverdue: String?,
                        var regContent: String,
                        var sysRegUserId: String,
                        var createUser: String,
                        var enterpriseId: String,
                        var taskId: String,
                        var domainCode: String,
                        var domainName: String,
                        var illegalityCode: String,
                        var illegalityName: String,
                        var assignee: String,
                        var isCompel: String,
                        var compelStatusName: String,
                        var compelStatus: String) : Serializable

    data class FileBean(var id: String,
                        var caseId: String,
                        var departmentId: String,
                        var name: String,
                        var url: String,
                        var type: String,
                        var updateUser: String,
                        var updateUserId: String,
                        var updateDate: Long) : Serializable
}