package com.zx.module_other.module.law.func.adapter

import android.annotation.TargetApi
import android.os.Build
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.zx.module_other.R
import com.zx.module_other.module.law.bean.LawMainBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

class LawRecentlyNotifyAdapter(dataBeans: List<LawMainBean>) : ZXQuickAdapter<LawMainBean, ZXBaseHolder>(R.layout.item_recently_notify, dataBeans) {
    @TargetApi(Build.VERSION_CODES.M)
    override fun convert(helper: ZXBaseHolder?, item: LawMainBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tvLawNotify, item.name)
            if (helper.adapterPosition == this.data.size - 1)
                helper.setVisible(R.id.viewLawDivider, false)
            if (helper.adapterPosition == 0) {
                helper.setVisible(R.id.viewLawDivider, false)
                helper.setGone(R.id.ivLawIcon, false)
                helper.getView<TextView>(R.id.tvLawNotify).setTextAppearance(R.style.common_textView_white_tiny)
                helper.getView<TextView>(R.id.tvLawNotify).setPadding(mContext.resources.getDimensionPixelOffset(R.dimen.dp_12), mContext.resources.getDimensionPixelOffset(R.dimen.dp_8), 0, mContext.resources.getDimensionPixelOffset(R.dimen.dp_8))
                helper.setBackgroundRes(R.id.clLawHead, R.drawable.index_list)
            }
        }
    }
}