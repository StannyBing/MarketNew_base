package com.zx.module_legalcase

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppLegalcase : XApp() {

    val HANDLE = XAppBean("综合执法", R.color.legalcase_color, R.drawable.icon_legalcase_handle, RoutePath.ROUTE_LEGALCASE_TASK)

    override fun all() = arrayListOf(HANDLE)

}