package com.zx.module_entity.module.entity.bean

/**
 * Created by Xiangb on 2019/7/16.
 * 功能：
 */
data class EntityDetailBean(var fEntityGuid: String?,
                            var fEntityName: String?,
                            var fCreditLevel: String?,
                            var fBizlicNum: String?,
                            var fOrgCode: String?,
                            var fLicenses: String?,
                            var fLegalPerson: String?,
                            var fAddress: String?,
                            var fContactInfo: String?,
                            var fStation: String?,
                            var fGrid: String?,
                            var fLongitude: Double?,
                            var fLatitude: Double?,
                            var fBizTypes: String?,
                            var fCreditInfo: String?,
                            var fIndustry: String?,
                            var fEntityType: String?,
                            var fTags: String?,
                            var tagList: List<String>?,
                            var fFoundDate: Long?,
                            var fContactAddress: String?,
                            var fContactPhone: String?,
                            var fContactPeople: String?,
                            var fEntityId: String?,
                            var fEnttype: String?,
                            var fEnttypename: String?,
                            var fPhoneModify: String?,
                            var fAddrMatch: Int?,
                            var fUniscid: String?,
                            var fZmqType: String?,
                            var fBuildingGuid: String?,
                            var fFloor: String?,
                            var fMatchedType: String?,
                            var fPqType: String?,
                            var fIsOnline: Int?,
                            var fBizScope: String?,
                            var fGridCode: String?,
                            var fStationCode: String?,
                            var fGovernOrg: String?,
                            var fGovernOrgCode: String?,
                            var fImgUrl: String?,
                            var fFile: List<FFileBean>?,
                            var fType: String?,
                            var fInsertDate: Long?,
                            var fInsertPerson: String?,
                            var fRealName: String?) {
    data class FFileBean(var id: String?,
                         var realName: String?,
                         var saveName: String?,
                         var savePath: String?,
                         var saveDate: Long?,
                         var creditCode: String?,
                         var userId: String?,
                         var tableName: String?,
                         var itemId: String?,
                         var field: String?)

}