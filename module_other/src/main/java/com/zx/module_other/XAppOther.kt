package com.zx.module_other

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppOther : XApp() {
    override val map: Map<String, XAppBean>
        get() = mapOf(
                "法律法规" to XAppBean("法律法规", R.color.colorPrimary, R.drawable.ic_camera, R.mipmap.icon_propser_add, RoutePath.ROUTE_OTHER_LAW),
                "文件打印" to XAppBean("文件打印", R.color.colorPrimary, R.drawable.ic_camera, R.mipmap.icon_propser_add, RoutePath.ROUTE_OTHER_PRINT)
        )
}