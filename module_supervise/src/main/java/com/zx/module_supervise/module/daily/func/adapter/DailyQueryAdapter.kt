package com.zx.module_supervise.module.daily.func.adapter

import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.zx.module_supervise.R
import com.zx.module_supervise.module.daily.bean.DailyQueryBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter
import com.zx.zxutils.util.ZXTimeUtil

/**
 * Created by Xiangb on 2019/6/20.
 * 功能：
 */
class DailyQueryAdapter(dataList: List<DailyQueryBean>) : ZXRecyclerQuickAdapter<DailyQueryBean, ZXBaseHolder>(R.layout.item_daily_query, dataList) {
    override fun quickConvert(helper: ZXBaseHolder?, item: DailyQueryBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_daily_itemTitle, item.enterpriseName)
            val spannableString = SpannableString("本年度内检查：${item.yearNum}次，总检查：${item.allNum}次")
            spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.daily_color)), 7, spannableString.indexOf("次"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.daily_color)), spannableString.lastIndexOf("：") + 1, spannableString.lastIndexOf("次"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            helper.setText(R.id.tv_daily_itemNum, spannableString)
            helper.setText(R.id.tv_daily_itemDate, "最近检查日期：" + if (item.checkDate != null) {
                ZXTimeUtil.getTime(item.checkDate!!)
            } else {
                ""
            })
            helper.setText(R.id.tv_daily_type, item.getCompany())
        }
    }

    private fun DailyQueryBean.getCompany(): String {
        if (enterpriseName.isEmpty()) {
            return ""
        } else if (fAddress.isNullOrEmpty()) {
            val companyName = enterpriseName.replace("有限公司|有限责任公司".toRegex(), "")
            if (companyName.length >= 4) {
                return companyName.substring(0, 4)
            } else {
                return companyName
            }
        } else {
            val dividerList = fAddress!!.split("省|市|区|县|镇|乡".toRegex())
            var companyName = enterpriseName
            if (dividerList.size > 1) {
                dividerList.forEach {
                    companyName = companyName.replace(it, "")
                }
                companyName = companyName.replace("省|市|区|县|镇|乡".toRegex(), "")
            }
            companyName = companyName.replace("有限责任公司|有限公司|公司".toRegex(), "")
            if (companyName.length >= 4) {
                return companyName.substring(0, 4)
            } else {
                return companyName
            }
        }
    }
}