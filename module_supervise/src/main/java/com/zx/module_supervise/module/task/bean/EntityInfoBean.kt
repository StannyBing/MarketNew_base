package com.zx.module_supervise.module.task.bean

/**
 * Created by Xiangb on 2019/7/21.
 * 功能：
 */
data class EntityInfoBean(var fId: String? = null,
                          var fLegalPerson: String? = null,
                          var fQualify: String? = null,
                          var fLicenses: String? = null,
                          var fAddress: String? = null,
                          var fStartDate: Long = 0,
                          var fLongitude: Double = 0.toDouble(),
                          var fResult: String? = null,
                          var fCreditLevel: String? = null,
                          var fHandleUser: String? = null,
                          var fBizlicNum: String? = null,
                          var fName: String? = null,
                          var fOrgCode: String? = null,
                          var fEntityGuid: String,
                          var isHandle : Boolean,
                          var fContactInfo: String? = null,
                          var fHandleUserId: String? = null,
                          var fStatus: String? = null,
                          var fLatitude: Double = 0.toDouble(),
                          var fTaskId: String? = null,
                          var fEntityName: String? = null,
                          var fEndDate: Long = 0) {

}