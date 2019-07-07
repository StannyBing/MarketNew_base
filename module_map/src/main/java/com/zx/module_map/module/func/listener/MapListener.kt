package com.zx.module_map.module.func.listener

import com.esri.android.map.MapView

/**
 * Created by Xiangb on 2019/7/4.
 * 功能：
 */
interface MapListener {

    fun getMap() : MapView

    fun doLocation()

    fun changeMap(type : String)
}