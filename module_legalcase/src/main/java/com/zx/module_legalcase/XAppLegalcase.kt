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
                "案件查询" to XAppBean("案件查询", R.color.legalcase_query_color, R.drawable.icon_legalcase_query,  RoutePath.ROUTE_LEGALASE_QUERY),
                "案件办理" to XAppBean("案件办理", R.color.legalcase_handle_color, R.drawable.icon_legalcase_handle,  RoutePath.ROUTE_LEGALASE_HANDLE)
        )
}