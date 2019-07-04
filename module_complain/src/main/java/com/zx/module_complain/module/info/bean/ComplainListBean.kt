package com.zx.module_complain.module.info.bean

/**
 * Created by Xiangb on 2019/6/20.
 * 功能：
 */
data class ComplainListBean(var fGuid: String,
                            var fName: String?,
                            var fType: String?,
                            var fStatus: Int,
                            var longitude: Double,
                            var latitude: Double,
                            var fRegTime: Long?,
                            var fEntityName: String?,
                            var fEntityAddress: String?,
                            var overdue: String,
                            var fEntityGuid: String) {
}