package com.zx.module_supervise.module.supervise.bean

/**
 * Updated by dell on 2019-07-21
 */
data class SuperviseListBean(var soonCount: Int,
                             var allCount: Int,
                             var entity: Entity?,
                             var overdueCount: Int) {
    data class Entity(var records: String,
                      var total: Int,
                      var current: Int,
                      var pageNo: Int,
                      var pageSize: Int,
                      var pages: Int,
                      var size: Int,
                      var searchCount: Boolean,
                      var list: List<ItemBean>) {
        data class ItemBean(var fId: String,
                            var fBizlicNum: String,
                            var fName: String,
                            var fEntityGuid: String,
                            var fStatus: String,
                            var fAddress: String?,
                            var fLatitude: Double,
                            var fStartDate: Long?,
                            var fTaskId: String,
                            var fLongitude: Double,
                            var fEntityName: String,
                            var fOverdue: String)
    }
}