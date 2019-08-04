package com.zx.module_other.module.workplan.func.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        //日期转时间戳
        fun timeStringToStamp(timeString: String): Long {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return simpleDateFormat.parse(timeString).time
        }

        //日期转时间戳2
        fun timeStringToStamp2(timeString: String): Long {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            return simpleDateFormat.parse(timeString).time
        }

        //日期转星期
        fun dateToWeek(datetime: String): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val weekDays = arrayListOf<String>("周日", "周一", "周二", "周三", "周四", "周五", "周六")
            var cal: Calendar = Calendar.getInstance(); // 获得一个日历
            var datet: Date? = null;
            try {
                datet = simpleDateFormat.parse(datetime);
                cal.setTime(datet);
            } catch (e: ParseException) {
                e.printStackTrace();
            }
            var w: Int = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
            if (w < 0)
                w = 0;
            return weekDays[w];
        }

        //时间戳转
        fun stampToTime(stamp: Long): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            return simpleDateFormat.format(Date(stamp))
        }

    }
}