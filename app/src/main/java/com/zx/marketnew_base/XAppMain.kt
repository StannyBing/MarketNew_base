package com.zx.marketnew_base

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppMain : XApp() {
    override val map: Map<String, XAppBean>
        get() = mapOf(
                "累计待办" to XAppBean("累计待办", R.color.light_gray, 0, 0, RoutePath.ROUTE_APP_MYTASK, 5),
                "即将逾期" to XAppBean("即将逾期", R.color.light_gray, 0, 0, RoutePath.ROUTE_APP_MYTASK, 2),
                "已经逾期" to XAppBean("已经逾期", R.color.light_gray, 0, 0, RoutePath.ROUTE_APP_MYTASK, 0)
        )
}