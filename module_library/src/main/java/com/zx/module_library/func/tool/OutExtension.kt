package com.zx.module_library.func.tool

import com.zx.module_library.bean.SearchFilterBean

/**
 * Created by Xiangb on 2019/6/27.
 * 功能：统一的扩展方法
 */

/**
 * 过滤器的获取选择的内容
 */
fun ArrayList<SearchFilterBean>.getSelect(position: Int = -1, name: String = ""): String {
    var bean: SearchFilterBean? = null
    if (position != -1) {
        bean = get(position)
    } else {
        forEach {
            if (it.filterName == name) {
                bean = it
            }
        }
    }
    if (bean == null) {
        return ""
    }
    return when (bean!!.filterType) {
        SearchFilterBean.FilterType.EDIT_TYPE -> {
            bean!!.values[0].value
        }
        SearchFilterBean.FilterType.SELECT_TYPE -> {
            var value = ""
            bean!!.values.forEach {
                if (it.isSelect) {
                    value = it.value
                    return@forEach
                }
            }
            value
        }
    }
}
