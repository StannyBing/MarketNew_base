package com.zx.module_entity.module.entity.func.adapter

import com.zx.module_entity.R
import com.zx.module_entity.module.entity.bean.BusinessBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/7/17.
 * 功能：
 */
class BusinessAdapter(dataList: List<BusinessBean>) : ZXQuickAdapter<BusinessBean, ZXBaseHolder>(R.layout.item_entity_business, dataList) {
    override fun convert(helper: ZXBaseHolder?, item: BusinessBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_business_key, item.key)
            helper.setText(R.id.tv_business_num, "共${item.num}个")
            helper.addOnClickListener(R.id.tv_business_detail)
        }
    }
}