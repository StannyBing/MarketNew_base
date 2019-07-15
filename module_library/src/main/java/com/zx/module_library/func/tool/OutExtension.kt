package com.zx.module_library.func.tool

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
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

fun ArrayList<SearchFilterBean>.getItem(name: String): SearchFilterBean? {
    if (isNotEmpty()) {
        forEach {
            if (it.filterName == name) {
                return it
            }
        }
    }
    return null
}

/**
 * Recycler滚动到顶部
 */
fun RecyclerView.animateToTop(position: Int) {
    if (layoutManager is LinearLayoutManager) {
        val scroller = TopSmoothScroll(context)
        scroller.targetPosition = position
        layoutManager.startSmoothScroll(scroller)
    }
}

private class TopSmoothScroll(context: Context) : LinearSmoothScroller(context) {

    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
        return 15f / displayMetrics.densityDpi
    }

    override fun getHorizontalSnapPreference(): Int {
        return SNAP_TO_START
    }

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }
}

private class EndSmoothScroll(context: Context) : LinearSmoothScroller(context) {
    override fun getHorizontalSnapPreference(): Int {
        return SNAP_TO_END
    }

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_END
    }
}
