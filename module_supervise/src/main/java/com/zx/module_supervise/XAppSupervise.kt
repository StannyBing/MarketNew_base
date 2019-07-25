package com.zx.module_supervise

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppSupervise : XApp() {
    override val map: Map<String, XAppBean>
        get() = mapOf(
                "专项检查" to XAppBean("专项检查", R.color.supervise_color, R.drawable.icon_supervise, RoutePath.ROUTE_SUPERVISE_QUERY),
                "现场检查" to XAppBean("现场检查", R.color.daily_color, R.drawable.icon_daily, RoutePath.ROUTE_DAILY_QUERY)
        )
}