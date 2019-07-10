package com.zx.module_legalcase.module.query.func.adapter

import com.zx.module_legalcase.R
import com.zx.module_library.bean.KeyValueBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/6/27.
 * 功能：
 */
class DetailInfoAdapter(dataList: List<KeyValueBean>) : ZXQuickAdapter<KeyValueBean, ZXBaseHolder>(R.layout.item_detail_info, dataList) {
    override fun convert(helper: ZXBaseHolder?, item: KeyValueBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_detailinfo_key, item.key)
            helper.setText(R.id.tv_detailinfo_value, item.value)
        }
    }
}