package com.zx.module_other.module.law.func.adapter

import android.widget.ImageView
import android.widget.RelativeLayout
import com.zx.module_other.R
import com.zx.module_other.module.workresults.bean.WorkOverAllBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

class WorkResultAllAdapter<T>(dataBeans: List<T>) : ZXQuickAdapter<T, ZXBaseHolder>(R.layout.item_work_all, dataBeans) {
    override fun convert(helper: ZXBaseHolder?, item: T) {
        if (item is WorkOverAllBean) {
            if (helper != null) {
                if (helper.adapterPosition % 3 == 0 || helper.adapterPosition % 4 == 0) {
                    helper.getView<RelativeLayout>(R.id.rl_work_item).setBackgroundColor(mContext.resources.getColor(R.color.work_satisics_infobg))
                } else {
                    helper.getView<RelativeLayout>(R.id.rl_work_item).setBackgroundColor(mContext.resources.getColor(R.color.work_satisics_bg))
                }
                helper.setText(R.id.tv_report, item.business)
                helper.setText(R.id.tv_report_num, item.num.toString())
                when(item.business){
                    "投诉举报"->helper.getView<ImageView>(R.id.iv_report).setImageResource(R.drawable.icon_complain)
                    "综合执法"->helper.getView<ImageView>(R.id.iv_report).setImageResource(R.drawable.icon_legalcase_handle)
                    "现场检查"->helper.getView<ImageView>(R.id.iv_report).setImageResource(R.drawable.icon_daily)
                    "专项检查"->helper.getView<ImageView>(R.id.iv_report).setImageResource(R.drawable.icon_supervise)
                    "监管任务"->helper.getView<ImageView>(R.id.iv_report).setImageResource(R.drawable.icon_supervise)
                }
            }
        }
    }
}