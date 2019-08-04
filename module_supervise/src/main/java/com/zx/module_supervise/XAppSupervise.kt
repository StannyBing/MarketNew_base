package com.zx.module_supervise

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppSupervise : XApp() {

    val SUPERVISE = XAppBean("专项检查", R.color.supervise_color, R.drawable.icon_supervise, RoutePath.ROUTE_SUPERVISE_QUERY)
    val DAILY = XAppBean("现场检查", R.color.daily_color, R.drawable.icon_daily, RoutePath.ROUTE_DAILY_QUERY)
    val EQUIPMENT = XAppBean("特种设备监管", R.color.equipment_color, R.drawable.icon_equipment, RoutePath.ROUTE_SUPERVISE_EQUIPMENTQUERY)
    val DRUGS = XAppBean("药品安全监管", R.color.drugs_color, R.drawable.icon_drugs, RoutePath.ROUTE_SUPERVISE_DRUGSQUERY)
    val FOODS = XAppBean("食品安全监管", R.color.foods_color, R.drawable.icon_foods, RoutePath.ROUTE_SUPERVISE_FOODSQUERY)

    override fun all() = arrayListOf(SUPERVISE, DAILY, EQUIPMENT, DRUGS, FOODS)
}