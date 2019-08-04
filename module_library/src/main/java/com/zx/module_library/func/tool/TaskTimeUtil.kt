package com.zx.module_library.func.tool

import java.util.*

/**
 * Created by Xiangb on 2019/3/14.
 * 功能：时间转换工具
 */
object TaskTimeUtil {

    fun displayTime(dateLong: Long): String {
        val cDate = Calendar.getInstance()
        val cNow = Calendar.getInstance()
        cDate.timeInMillis = dateLong
        cNow.timeInMillis = System.currentTimeMillis()
        val yearDate = cDate[Calendar.YEAR]//年
        val monthDate = cDate[Calendar.MONTH] + 1//月
        val dayDate = cDate[Calendar.DAY_OF_MONTH]//日
        val hourDate = cDate[Calendar.HOUR_OF_DAY]//时
        val minuteDate = cDate[Calendar.MINUTE]//分

        val yearNow = cNow[Calendar.YEAR]//年
        val hourNow = cNow[Calendar.HOUR_OF_DAY]//时

        val datetime = System.currentTimeMillis() - dateLong
        val day = Math.floor((datetime.toFloat() / 24f / 60f / 60f / 1000.0f).toDouble()).toLong() + if (hourNow < hourDate) 1 else 0// 天前

        var dateString = ""
        if (day.toInt() == 0) {//今天
            dateString = if (hourDate < 12) {
                "上午$hourDate:${minuteDate.toString().padStart(2, '0')}"
            } else if (hourDate > 12) {
                "下午${hourDate - 12}:${minuteDate.toString().padStart(2, '0')}"
            } else {
                "下午12:${minuteDate.toString().padStart(2, '0')}"
            }
        } else if (day.toInt() == 1) {//昨天
            dateString = "昨天$hourDate:${minuteDate.toString().padStart(2, '0')}"
        } else if (yearDate == yearNow) {
            dateString = "${monthDate}月${dayDate}日"
        } else {
            dateString = "$yearDate/$monthDate/$dayDate"
        }
        return dateString
    }

}