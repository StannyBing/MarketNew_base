package com.zx.module_other.module.workplan.func.view

import android.content.Context
import android.util.AttributeSet
import com.necer.calendar.MiuiCalendar
import com.necer.enumeration.CalendarState

class Calendar(context: Context, attrs: AttributeSet?) : MiuiCalendar(context, attrs) {
    /**
     * @param dy 当前滑动的距离 dy>0向上滑动，dy<0向下滑动
     * @return
     */
    override fun getGestureMonthUpOffset(dy: Int): Float {
        val maxOffset: Float
        val monthCalendarOffset: Float
        if (calendarState == CalendarState.MONTH) {
            maxOffset = monthCalendar.pivotDistanceFromTop - Math.abs(monthCalendar.y)
            monthCalendarOffset = monthCalendar.pivotDistanceFromTop.toFloat()
        } else {
            maxOffset = monthCalendar.getDistanceFromTop(weekCalendar.firstDate) - Math.abs(monthCalendar.y)
            monthCalendarOffset = monthCalendar.getDistanceFromTop(weekCalendar.firstDate).toFloat()
        }
        val childLayoutOffset = (monthHeight - weekHeight).toFloat()
        val offset = monthCalendarOffset * dy / childLayoutOffset
        return getOffset(offset, maxOffset)
    }

    /**
     * @param dy 当前滑动的距离 dy>0向上滑动，dy<0向下滑动
     * @return
     */
    override fun getGestureMonthDownOffset(dy: Int): Float {
        val maxOffset = Math.abs(monthCalendar.y)
        val monthCalendarOffset: Float
        if (calendarState == CalendarState.MONTH) {
            monthCalendarOffset = monthCalendar.pivotDistanceFromTop.toFloat()
        } else {
            monthCalendarOffset = monthCalendar.getDistanceFromTop(weekCalendar.firstDate).toFloat()
        }
        val childLayoutOffset = (monthHeight - weekHeight).toFloat()
        val offset = monthCalendarOffset * dy / childLayoutOffset
        return getOffset(Math.abs(offset), maxOffset)
    }

    override fun getGestureChildDownOffset(dy: Int): Float {
        val maxOffset = monthHeight - childView.y
        return getOffset(Math.abs(dy).toFloat(), maxOffset)
    }

    override fun getGestureChildUpOffset(dy: Int): Float {
        val maxOffset = childView.y - weekHeight
        return getOffset(dy.toFloat(), maxOffset)
    }
}