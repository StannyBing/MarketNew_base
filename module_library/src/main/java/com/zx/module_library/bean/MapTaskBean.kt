package com.zx.module_library.bean

import android.support.annotation.DrawableRes
import java.io.Serializable

/**
 * Created by Xiangb on 2019/7/7.
 * 功能：
 */
data class MapTaskBean(var typeName : String,
                       @DrawableRes var typeIcon : Int,
                       var name : String,
                       var address : String,
                       var other : String,
                       var id : String,
                       var longtitude : Double?,
                       var latitude : Double?) : Serializable{

}