package com.zx.module_other.module.law.func.adapter

import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.zx.module_other.R
import com.zx.module_other.module.law.bean.LawSearchBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter

class LawQueryListAdapter<T>(dataBeans: List<T>) : ZXRecyclerQuickAdapter<T, ZXBaseHolder>(R.layout.item_law_detail, dataBeans) {
    override fun quickConvert(helper: ZXBaseHolder?, item: T) {
        if (helper != null && item != null) {
            if (item is LawSearchBean) {
//                if (item.itemType == 1) {
//                    helper.setVisible(R.id.viewItemDivider, true)
//                    helper.setTextColor(R.id.tvLawName, ContextCompat.getColor(mContext, R.color.text_color_drak))
//                    helper.getView<TextView>(R.id.tvLawName).setPadding(mContext.resources.getDimensionPixelOffset(R.dimen.dp_16), mContext.resources.getDimensionPixelOffset(R.dimen.dp_8), 0, mContext.resources.getDimensionPixelOffset(R.dimen.dp_8))
//                    helper.setText(R.id.tvLawName, item.itemName)
//                } else if (item.itemType == 2) {
                    helper.setText(R.id.tvLawQueryId, (helper.adapterPosition+1).toString())
                    helper.setText(R.id.tvLawQueryName, item.content.replace("<br/>", "\n"))
                    helper.setText(R.id.tvLawQueryParentName, String.format("来自：%s", item.name))
                    helper.setVisible(R.id.tvLawQueryTitle, if (item.chapter.isEmpty() || item.chapter.equals("")) false else true)
                    helper.setText(R.id.tvLawQueryTitle, item.chapter)
//                }
            }
        }
    }

}