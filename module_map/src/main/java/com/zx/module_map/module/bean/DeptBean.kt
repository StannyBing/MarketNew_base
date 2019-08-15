package com.zx.module_map.module.bean

/**
 * Created by Xiangb on 2019/7/2.
 * 功能：
 */
data class DeptBean(var id: String, var name: String, var code: String, var parentId: String, var type: String, var childs: List<DeptBean>) {
}