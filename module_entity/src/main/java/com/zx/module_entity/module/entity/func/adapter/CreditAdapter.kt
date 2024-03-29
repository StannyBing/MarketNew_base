package com.zx.module_entity.module.entity.func.adapter

import android.support.v4.content.ContextCompat
import com.zx.module_entity.R
import com.zx.module_entity.XAppEntity
import com.zx.module_entity.module.entity.bean.CreditBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/7/17.
 * 功能：
 */
class CreditAdapter(dataList: List<CreditBean>) : ZXQuickAdapter<CreditBean, ZXBaseHolder>(R.layout.item_entity_credit, dataList) {
    override fun convert(helper: ZXBaseHolder?, item: CreditBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_credit_title, item.title)
            helper.setText(R.id.tv_credit_year, item.yearCount.toString())
            helper.setText(R.id.tv_credit_all, item.allCount.toString())
            helper.setTextColor(R.id.tv_credit_year, if (item.yearCount == 0) {
                ContextCompat.getColor(mContext, R.color.text_color_noraml)
            } else {
                helper.addOnClickListener(R.id.tv_credit_year)
                ContextCompat.getColor(mContext, XAppEntity.ENTITY.moduleColor)
            })
            helper.setTextColor(R.id.tv_credit_all, if (item.allCount == 0) {
                ContextCompat.getColor(mContext, R.color.text_color_noraml)
            } else {
                helper.addOnClickListener(R.id.tv_credit_all)
                ContextCompat.getColor(mContext, XAppEntity.ENTITY.moduleColor)
            })
            if (helper.adapterPosition == 0) {
                helper.setText(R.id.tv_credit_title, "信用等级")
                helper.setVisible(R.id.tv_credit_year, false)
                helper.setVisible(R.id.tv_credit_all, false)
                helper.setVisible(R.id.tv_credit_tip2, false)
                helper.setText(R.id.tv_credit_tip1, item.title)
            } else {
                helper.setText(R.id.tv_credit_title, item.title)
                helper.setVisible(R.id.tv_credit_year, true)
                helper.setVisible(R.id.tv_credit_all, true)
                helper.setVisible(R.id.tv_credit_tip2, true)
                helper.setText(R.id.tv_credit_tip1, "本年")
            }
        }
    }
}