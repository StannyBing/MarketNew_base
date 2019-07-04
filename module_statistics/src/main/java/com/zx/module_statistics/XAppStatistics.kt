package com.zx.module_statistics

import com.zx.module_library.XApp
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppStatistics : XApp() {
    override val map: Map<String, XAppBean>
        get() = mapOf(
//                "统计-投诉举报" to XAppBean("统计-投诉举报", R.color.colorPrimary, R.drawable.ic_camera, R.mipmap.icon_propser_add, RoutePath.ROUTE_STATISTICS_COMPLAIN)
        )
}