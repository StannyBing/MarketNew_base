package com.zx.module_supervise.module.daily.func.adapter

import com.zx.module_supervise.R
import com.zx.module_supervise.module.daily.bean.TemplateBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import com.zx.zxutils.util.ZXTimeUtil

/**
 * Created by Xiangb on 2019/7/24.
 * 功能：
 */
class TemplateAdapter(data: List<TemplateBean>) : ZXQuickAdapter<TemplateBean, ZXBaseHolder>(R.layout.item_template_view, data) {
    override fun convert(helper: ZXBaseHolder?, item: TemplateBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_template_name, item.templateName)
            helper.setText(R.id.tv_template_time, ZXTimeUtil.getTime(item.updateDate))
            helper.addOnClickListener(R.id.tv_template_edit)
        }
    }
}