package com.zx.module_supervise.module.daily.bean

/**
 * Created by Xiangb on 2019/7/12.
 * 功能：
 */
data class EntityBean(var fLegalPerson: String?,
                      var fAddress: String?,
                      var fEnttype: String? = null,
                      var fLongitude: Double? = null,
                      var fCreditLevel: String? = null,
                      var fBizlicNum: String? = null,
                      var fStation: String? = null,
                      var fGrid: String? = null,
                      var fContactInfo: String?,
                      var fContactPhone: String? = null,
                      var fEntityGuid: String?,
                      var fStatus: String? = null,
                      var fLatitude: Double? = null,
                      var fTags: String? = null,
                      var position: String? = null,
                      var fEntityName: String?) {
}