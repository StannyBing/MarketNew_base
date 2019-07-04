package com.zx.marketnew_base.main.bean

import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
data class XAppListBean(var title: String, var type: XTYPE, var xAppList: List<XAppBean>) {

    enum class XTYPE {
        TASK_STATISTICS,
        MY_XAPP,
        NORMAL_XAPP,
        ALL_XAPP
    }

}