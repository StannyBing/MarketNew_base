package com.zx.module_statistics.module.func.adapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.zx.module_library.bean.XAppBean
import com.zx.module_statistics.R
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/9/4.
 * 功能：
 */
@SuppressLint("NewApi")
class StatisticsItemAdapter(dataList: List<XAppBean>) : ZXQuickAdapter<XAppBean, ZXBaseHolder>(R.layout.item_statistis_item, dataList) {
    override fun convert(helper: ZXBaseHolder?, item: XAppBean?) {
        if (helper != null && item != null) {
            val icon = ContextCompat.getDrawable(mContext, item.appIcon)
            icon?.setTint(ContextCompat.getColor(mContext, item.moduleColor))
            icon?.mutate()
            helper.getView<ImageView>(R.id.iv_statistics_icon).background = icon

            helper.setText(R.id.tv_statistics_name, item.name)
        }
    }
}