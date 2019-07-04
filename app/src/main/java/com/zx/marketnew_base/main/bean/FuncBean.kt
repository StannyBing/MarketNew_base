package com.zx.marketnew_base.main.bean

import android.support.annotation.DrawableRes

/**
 * Created by Xiangb on 2019/3/12.
 * 功能：
 */
data class FuncBean(var title: String = "", @DrawableRes var icon: Int? = null,var showBottomDivider : Boolean = false)