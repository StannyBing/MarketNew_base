package com.zx.module_legalcase

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppLegalcase : XApp() {

    override val map: Map<String, XAppBean>
        get() = mapOf(
                "案件执法" to XAppBean("案件执法", R.color.colorPrimary, R.drawable.ic_camera, R.mipmap.icon_propser_add, RoutePath.ROUTE_LEGALASE_QUERY)
        )
}