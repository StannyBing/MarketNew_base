package com.zx.module_statistics

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppStatistics : XApp() {

    val MAIN = XAppBean("统计分析", R.color.statistics_color, R.drawable.icon_statistics, RoutePath.ROUTE_STATISTICS_MAIN)

    override fun all() = arrayListOf(MAIN)

}