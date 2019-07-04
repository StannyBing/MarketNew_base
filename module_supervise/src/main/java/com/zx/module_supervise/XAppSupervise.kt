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
                "监管任务" to XAppBean("监管任务", R.color.colorPrimary, R.drawable.ic_camera, R.mipmap.icon_propser_add, RoutePath.ROUTE_SUPERVISE_QUERY)
        )
}