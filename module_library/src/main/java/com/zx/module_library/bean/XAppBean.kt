package com.zx.module_library.bean

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import java.io.Serializable

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
data class XAppBean(var name: String, @ColorRes var moduleColor: Int, @DrawableRes var appIconWhite: Int, @DrawableRes var appIconPrimary: Int, var appRoutePath: String, var num: Int = 0) : Serializable {

}