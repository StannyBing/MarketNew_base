package com.zx.module_other.module.law.func.adapter

import com.zx.module_other.R
import com.zx.module_other.module.law.bean.LawCollectBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter

class LawCollectAdapter<T>(dataBeans: List<T>) : ZXRecyclerQuickAdapter<T, ZXBaseHolder>(R.layout.item_law_collect, dataBeans) {
    override fun quickConvert(helper: ZXBaseHolder?, item: T) {
        if (item is LawCollectBean) {
            helper!!.setText(R.id.tv_law_collect_name, item.name)
            when (item.type) {
                "1" -> helper!!.setText(R.id.tv_law_collect_type, "法律法规")
            }
        }
    }

}