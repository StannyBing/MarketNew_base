package com.zx.module_complain

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：小应用 投诉举报
 */
object XAppComplain : XApp() {
    val LIST = XAppBean("投诉举报", R.color.complain_color, R.drawable.icon_complain, RoutePath.ROUTE_COMPLAIN_QUERY)

    override fun all() = arrayListOf(LIST)
}