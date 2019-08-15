package com.zx.module_map.module.bean

/**
 * Created by Xiangb on 2019/7/12.
 * 功能：
 */
data class DicTypeBean(var id: String,
                       var dicType: String?,
                       var dicName: String,
                       var dicValue: String?,
                       var dicNameAlias: String?,
                       var dicSort: String?,
                       var parentId: String?,
                       var dicRemark: String?,
                       var select : Boolean = false) {

}