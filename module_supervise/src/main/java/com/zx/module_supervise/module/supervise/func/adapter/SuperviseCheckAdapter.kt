package com.zx.module_supervise.module.supervise.func.adapter

import com.zx.module_supervise.R
import com.zx.module_supervise.module.supervise.bean.SuperviseCheckBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/7/21.
 * 功能：
 */
class SuperviseCheckAdapter(dataList: List<SuperviseCheckBean>) : ZXQuickAdapter<SuperviseCheckBean, ZXBaseHolder>(R.layout.item_supervise_check, dataList) {
    override fun convert(helper: ZXBaseHolder?, item: SuperviseCheckBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_check_key, "${helper.adapterPosition}.${item.fName}")
            helper.setText(R.id.tv_check_value, when (item.fValueType) {
                "0" -> {
                    if (item.fCheckResult == "0") "是" else if (item.fCheckResult == "1") "否" else "不适用"
                }
                else -> {
                    item.fCheckResult
                }
            })
        }
    }
}