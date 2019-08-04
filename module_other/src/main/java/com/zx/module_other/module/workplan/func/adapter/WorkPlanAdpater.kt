package com.zx.module_other.module.workplan.func.adapter

import com.zx.module_other.R
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.func.util.DateUtil
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

class WorkPlanAdpater<T>(dataBeans: List<T>) : ZXQuickAdapter<T, ZXBaseHolder>(R.layout.item_work_plan, dataBeans) {
    override fun convert(helper: ZXBaseHolder?, item: T) {
        if (item is WorkPlanBean) {
            helper!!.setText(R.id.tv_work_title, item.business)
            helper!!.setText(R.id.tv_work_content, item.content)
            val time = DateUtil.stampToTime(item.endDate);
            helper!!.setText(R.id.tv_day, time.substring(time.length-2,time.length))
            helper!!.setText(R.id.tv_week, DateUtil.dateToWeek(DateUtil.stampToTime(item.endDate)))
        }
    }

}