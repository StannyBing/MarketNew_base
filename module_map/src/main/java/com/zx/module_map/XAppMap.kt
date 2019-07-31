package com.zx.module_map

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean


/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppMap : XApp() {

    val MAP = XAppBean("地图", R.color.map_color, R.drawable.icon_map, RoutePath.ROUTE_MAP_MAP)

    override fun all() = arrayListOf(MAP)
}