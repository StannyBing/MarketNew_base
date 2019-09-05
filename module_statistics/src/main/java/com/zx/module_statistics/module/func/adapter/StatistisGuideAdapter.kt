package com.zx.module_statistics.module.func.adapter

import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.zx.module_statistics.R
import com.zx.module_statistics.XAppStatistics
import com.zx.module_statistics.module.bean.StatisticsBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/9/4.
 * 功能：
 */
class StatistisGuideAdapter(dataList: List<StatisticsBean>) : ZXQuickAdapter<StatisticsBean, ZXBaseHolder>(R.layout.item_statistics_guide, dataList) {

    var selectItem = 0

    override fun convert(helper: ZXBaseHolder?, item: StatisticsBean?) {
        if (helper != null && item != null) {
            val tvName = helper.getView<TextView>(R.id.tv_guide_name)
            tvName.text = item.name
            if (helper.adapterPosition == selectItem) {
                tvName.setTextColor(ContextCompat.getColor(mContext, XAppStatistics.MAIN.moduleColor))
            } else {
                tvName.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_light))
            }
        }
    }
}