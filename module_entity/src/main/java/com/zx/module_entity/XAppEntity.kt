package com.zx.module_entity

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppEntity : XApp() {
    override val map: Map<String, XAppBean>
        get() = mapOf(
                "主体查询" to XAppBean("主体查询", R.color.entity_color, R.drawable.icon_entity, RoutePath.ROUTE_ENTITY_QUERY),
                "特殊主体" to XAppBean("特殊主体", R.color.special_color, R.drawable.icon_special, RoutePath.ROUTE_ENTITY_SPECIALADD)
        )
}