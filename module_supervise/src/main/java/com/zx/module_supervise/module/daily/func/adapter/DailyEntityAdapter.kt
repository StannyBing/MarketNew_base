package com.zx.module_supervise.module.daily.func.adapter

import com.zx.module_supervise.R
import com.zx.module_supervise.module.daily.bean.EntityBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter

/**
 * Created by Xiangb on 2019/6/20.
 * 功能：
 */
class DailyEntityAdapter(dataList: List<EntityBean>) : ZXRecyclerQuickAdapter<EntityBean, ZXBaseHolder>(R.layout.item_daily_entity, dataList) {
    override fun quickConvert(helper: ZXBaseHolder?, item: EntityBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_entity_itemTitle, item.fEntityName)
            helper.setText(R.id.tv_entity_itemInfo, (item.fStation ?: "") + "-" + (item.fGrid ?: ""))
        }
    }
}