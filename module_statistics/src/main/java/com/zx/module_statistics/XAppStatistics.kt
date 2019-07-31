package com.zx.module_statistics

import com.zx.module_library.XApp
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppStatistics : XApp() {
    override fun all() = arrayListOf<XAppBean>()

}