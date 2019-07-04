package com.zx.module_complain

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：小应用 投诉举报
 */
object XAppComplain : XApp() {

    override val map: Map<String, XAppBean>
        get() = mapOf(
                "投诉举报" to XAppBean("投诉举报", R.color.xpp_color, R.drawable.ic_camera, R.mipmap.icon_propser_add, RoutePath.ROUTE_COMPLAIN_LIST)
        )
}