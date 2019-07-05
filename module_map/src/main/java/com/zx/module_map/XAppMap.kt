package com.zx.module_map

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean


/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppMap : XApp() {
    override val map: Map<String, XAppBean>
        get() = mapOf(
                "地图" to XAppBean("地图", R.color.map_color, R.drawable.ic_camera, R.mipmap.icon_propser_add, RoutePath.ROUTE_MAP_MAP)
        )
}