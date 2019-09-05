package com.zx.module_statistics.module.func.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.zx.module_statistics.R
import com.zx.module_statistics.XAppStatistics
import com.zx.module_statistics.module.bean.StatisticsBean
import com.zx.module_statistics.module.ui.ChartFragment
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import com.zx.zxutils.util.ZXFragmentUtil

/**
 * Created by Xiangb on 2019/9/4.
 * 功能：
 */
class StatisticsInfoAdapter(dataList: List<StatisticsBean>) : ZXQuickAdapter<StatisticsBean, ZXBaseHolder>(R.layout.item_statistics_info, dataList) {

    override fun convert(helper: ZXBaseHolder?, item: StatisticsBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_info_name, item.name)
            helper.setBackgroundColor(R.id.view_info_marker, ContextCompat.getColor(mContext, XAppStatistics.MAIN.moduleColor))

            val frameView = helper.getView<FrameLayout>(R.id.fm_statistics_info)
            @android.support.annotation.IdRes
            val ID_RESET = (Math.random() * 100000).toInt()
            frameView.id = ID_RESET
            ZXFragmentUtil.addFragment((mContext as AppCompatActivity).supportFragmentManager, ChartFragment.newInstance(item), frameView.id)
        }
    }
}