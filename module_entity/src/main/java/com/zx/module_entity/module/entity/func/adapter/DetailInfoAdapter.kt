package com.zx.module_entity.module.entity.func.adapter

import com.zx.module_entity.R
import com.zx.module_entity.module.entity.bean.DetailInfoBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/6/27.
 * 功能：
 */
class DetailInfoAdapter(dataList: List<DetailInfoBean>) : ZXQuickAdapter<DetailInfoBean, ZXBaseHolder>(R.layout.item_entity_detail_info, dataList) {
    override fun convert(helper: ZXBaseHolder?, item: DetailInfoBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_detailinfo_key, item.name)
            helper.setText(R.id.tv_detailinfo_value, item.value)
            helper.setVisible(R.id.view_edit, item.canEdit)
        }
    }
}