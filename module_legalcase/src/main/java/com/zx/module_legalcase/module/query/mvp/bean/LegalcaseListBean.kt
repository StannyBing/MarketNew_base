package com.zx.module_legalcase.module.query.mvp.bean

import java.io.Serializable

/**
 * Created by Xiangb on 2019/7/7.
 *
 *
 * 功能：
 *
 */
data class LegalcaseListBean(var name: String,
                             var departmentName: String?,
                             var caseNum: String?,
                             var departmentId: String?,
                             var latitude: Double?,
                             var typeName: String?,
                             var regDate: Long?,
                             var foundDate: Long?,
                             var regUser: String?,
                             var typeCode: String?,
                             var isOverdue: String?,
                             var processId: String?,
                             var statusName: String?,
                             var caseName: String?,
                             var id: String?,
                             var enterpriseId: String?,
                             var overdueTime: Long?,
                             var assignee: String?,
                             var enterpriseName: String?,
                             var taskId: String?,
                             var status: String?,
                             var longitude: Double?,
                             var enterpriseAddress: String?,
                             var domainCode: String?,
                             var domainName: String?,
                             var isCompel: String?,
                             var compelStatusName: String?,
                             var compelStatus: String?) : Serializable {}