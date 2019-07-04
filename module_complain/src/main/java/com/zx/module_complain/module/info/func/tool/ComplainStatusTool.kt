package com.zx.module_complain.module.info.func.tool

/**
 * Created by Xiangb on 2019/6/27.
 * 功能：
 */
object ComplainStatusTool {
    fun getStatusString(status: Int?): String {
        val map = hashMapOf<Int, String>()
        map[0] = "信息录入"
        map[10] = "待受理"
        map[20] = "待分流"
        map[30] = "待指派"
        map[40] = "待联系"
        map[50] = "待处置"
        map[60] = "待初审"
        map[70] = "待终审"
        map[80] = "待办结"
        map[90] = "已办结"
        map[100] = "状态监控"
        return if (!map.containsKey(status)) {
            "未知状态"
        } else map[status]!!
    }
}