package com.zx.module_other.module.workplan.func.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import com.zx.module_other.R
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.func.util.DateUtil
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

class WorkPlanAdpater<T>(dataBeans: List<T>) : ZXQuickAdapter<T, ZXBaseHolder>(R.layout.item_work_plan, dataBeans) {
    @SuppressLint("ResourceAsColor")
    override fun convert(helper: ZXBaseHolder?, item: T) {
        if (item is WorkPlanBean) {
            helper!!.setText(R.id.tv_work_title, item.business)
            helper!!.setText(R.id.tv_work_content, item.content)
            val time = DateUtil.stampToTime(item.endDate);
            val dayTime = DateUtil.stampToTime(System.currentTimeMillis())
            helper!!.setText(R.id.tv_day, time.substring(time.length-2,time.length))
            helper!!.setText(R.id.tv_week, DateUtil.dateToWeek(DateUtil.stampToTime(item.endDate)))
            if (DateUtil.timeStringToStamp2(time)<DateUtil.timeStringToStamp2(dayTime)){
                helper!!.getView<TextView>(R.id.tv_day).setTextColor(R.color.gray_cc)
                helper!!.getView<TextView>(R.id.tv_week).setTextColor(R.color.gray_cc)
            } else if (DateUtil.timeStringToStamp2(time)==DateUtil.timeStringToStamp2(dayTime)){
                helper!!.getView<TextView>(R.id.tv_day).setTextColor(R.color.qr_blue)
                helper!!.getView<TextView>(R.id.tv_week).setTextColor(R.color.qr_blue)
            }else{
                helper!!.getView<TextView>(R.id.tv_day).setTextColor(R.color.text_color_noraml)
                helper!!.getView<TextView>(R.id.tv_week).setTextColor(R.color.text_color_noraml)
            }
        }
    }

}