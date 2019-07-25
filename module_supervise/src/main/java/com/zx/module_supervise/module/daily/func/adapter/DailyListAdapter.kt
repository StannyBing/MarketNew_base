package com.zx.module_supervise.module.daily.func.adapter

import com.zx.module_library.func.tool.TaskTimeUtil
import com.zx.module_supervise.R
import com.zx.module_supervise.module.daily.bean.DailyListBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter

/**
 * Created by Xiangb on 2019/6/20.
 * 功能：
 */
class DailyListAdapter(dataList: List<DailyListBean>) : ZXRecyclerQuickAdapter<DailyListBean, ZXBaseHolder>(R.layout.item_daily_normal, dataList) {
    override fun quickConvert(helper: ZXBaseHolder?, item: DailyListBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_daily_itemTitle, item.name)
            helper.setText(R.id.tv_daily_itemInfo, "检查人员：${item.checkPerson}")
            helper.setText(R.id.tv_daily_itemDate,
                    if (item.insertDate == null) {
                        ""
                    } else {
                        TaskTimeUtil.displayTime(item.insertDate!!)
//                        ZXTimeUtil.getTime(item.fRegTime!!).replace(" ", "\n")
                    })
            helper.setText(R.id.tv_daily_status, when (item.result) {
                "0" -> "符合"
                "1" -> "不符合"
                "2" -> "基本\n符合"
                else -> {
                    "添加结\n论补录"
                }
            })
            helper.addOnClickListener(R.id.tv_daily_status)
        }
    }
}