package com.zx.marketnew_base

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppMain : XApp() {

    val MYTASK_ALL = XAppBean("累计待办", R.color.light_gray, 0,  RoutePath.ROUTE_APP_MYTASK, 0)
    val MYTASK_SOON = XAppBean("即将到期", R.color.light_gray, 0,  RoutePath.ROUTE_APP_MYTASK, 0)
    val MYTASK_OVERDUE = XAppBean("已经逾期", R.color.light_gray, 0,  RoutePath.ROUTE_APP_MYTASK, 0)

    override fun all() = arrayListOf(MYTASK_ALL, MYTASK_SOON, MYTASK_OVERDUE)
}