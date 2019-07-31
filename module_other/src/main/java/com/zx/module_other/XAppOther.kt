package com.zx.module_other

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppOther : XApp() {

    val LAW = XAppBean("法律法规", R.color.law_color, R.drawable.icon_law, RoutePath.ROUTE_OTHER_LAW)
    val PRINT = XAppBean("文件打印", R.color.print_color, R.drawable.icon_print, RoutePath.ROUTE_OTHER_PRINT)
    val DOCUMENT = XAppBean("文书管理", R.color.document_color, R.drawable.icon_docment, RoutePath.ROUTE_OTHER_DOCUMENT)
    val INFOMATION = XAppBean("政务资讯", R.color.infomation_color, R.drawable.icon_infomation, RoutePath.ROUTE_OTHER_INFOMATION)

    override fun all() = arrayListOf(LAW, PRINT, DOCUMENT, INFOMATION)
}