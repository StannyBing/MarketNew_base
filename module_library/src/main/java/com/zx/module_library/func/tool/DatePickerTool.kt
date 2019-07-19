package com.zx.module_library.func.tool

import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Xiangb on 2019/7/19.
 * 功能：
 */
object DatePickerTool {
    fun showDatePicker(context: Context, tvDate: TextView, initDate: Long = 0L, timeFormat: SimpleDateFormat? = null, minDate: Long = 0L, maxDate: Long = 0L, callBack: (String) -> Unit = {}) {
        val calendar = Calendar.getInstance()
        if (initDate == 0L) {
            calendar.timeInMillis = System.currentTimeMillis()
        } else {
            calendar.timeInMillis = initDate
        }
        val dialog = DatePickerDialog(context, { view, year, month, dayOfMonth ->
            if (timeFormat == null) {
                tvDate.text = "$year-${if (month + 1 > 9) month + 1 else "0" + (month + 1)}-${if (dayOfMonth > 9) dayOfMonth else "0" + dayOfMonth}"
            } else {
                tvDate.text = timeFormat.format(Date(year - 1900, month, dayOfMonth)).toString()
            }
            callBack(tvDate.text.toString())
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        if (minDate != 0L) dialog.datePicker.minDate = minDate
        if (maxDate != 0L) dialog.datePicker.maxDate = maxDate
        dialog.show()
    }
}