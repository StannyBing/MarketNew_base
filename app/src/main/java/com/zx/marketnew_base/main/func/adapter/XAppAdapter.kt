package com.zx.marketnew_base.main.func.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.widget.RelativeLayout
import android.widget.TextView
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.XAppListBean
import com.zx.module_library.bean.XAppBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：XApp的适配器
 */
@SuppressLint("NewApi")
class XAppAdapter(dataBeans: List<XAppBean>, val xtype: XAppListBean.XTYPE) : ZXQuickAdapter<XAppBean, ZXBaseHolder>(R.layout.item_xapp, dataBeans) {
    override fun convert(helper: ZXBaseHolder?, item: XAppBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_xapp_name, item.name)
            val gradientDrawable = helper.getView<RelativeLayout>(R.id.rl_xapp_bg).background as GradientDrawable
            gradientDrawable.setColor(ContextCompat.getColor(mContext, item.moduleColor))
            gradientDrawable.mutate()
            if (xtype == XAppListBean.XTYPE.TASK_STATISTICS) {
                helper.getView<TextView>(R.id.tv_xapp_icon).background = null
                helper.setText(R.id.tv_xapp_icon, item.num.toString())
                if (item.num.toString().length <= 2) {
                    helper.getView<TextView>(R.id.tv_xapp_icon).textSize = 19f
                } else if (item.num.toString().length == 3) {
                    helper.getView<TextView>(R.id.tv_xapp_icon).textSize = 15f
                } else {
                    helper.getView<TextView>(R.id.tv_xapp_icon).textSize = 11f
                }
            } else {
                val icon = ContextCompat.getDrawable(mContext, item.appIcon)!!
                icon.setTint(ContextCompat.getColor(mContext, R.color.white))
                icon.mutate()
                helper.getView<TextView>(R.id.tv_xapp_icon).background = icon
                helper.setText(R.id.tv_xapp_icon, "")
            }
        }
    }
}