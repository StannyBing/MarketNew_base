package com.zx.module_legalcase.module.query.func.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.zx.module_legalcase.R
import com.zx.module_legalcase.module.query.bean.DynamicBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import com.zx.zxutils.util.ZXTimeUtil

/**
 * Created by Xiangb on 2019/7/1.
 * 功能：
 */
class DynamicAdapter(dataList: List<DynamicBean>) : ZXQuickAdapter<DynamicBean, ZXBaseHolder>(R.layout.item_legalcase_dynamic, dataList) {

    private var moduleColor: Int = 0

    override fun convert(helper: ZXBaseHolder?, item: DynamicBean?) {
        if (helper != null && item != null) {
            val tvDate = helper.getView<TextView>(R.id.tv_dynamic_date)
            val tvName = helper.getView<TextView>(R.id.tv_dynamic_name)
            val tvProcess = helper.getView<TextView>(R.id.tv_dynamic_process)
            val tvPerson = helper.getView<TextView>(R.id.tv_dynamic_person)
            val tvRemark = helper.getView<TextView>(R.id.tv_dynamic_remark)
            if (helper.adapterPosition == 0) {
                tvDate.setTextColor(Color.WHITE)
                tvName.setTextColor(Color.WHITE)
                tvProcess.setTextColor(Color.WHITE)
                tvPerson.setTextColor(Color.WHITE)
                tvRemark.setTextColor(Color.WHITE)
                if (moduleColor != 0) {
                    tvDate.setBackgroundColor(moduleColor)
                    tvName.setBackgroundColor(moduleColor)
                    tvProcess.setBackgroundColor(moduleColor)
                    tvPerson.setBackgroundColor(moduleColor)
                    tvRemark.setBackgroundColor(moduleColor)
                }
            } else {
                tvDate.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_noraml))
                tvName.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_noraml))
                tvProcess.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_noraml))
                tvPerson.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_noraml))
                tvRemark.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_noraml))
                tvDate.setBackgroundColor(Color.WHITE)
                tvName.setBackgroundColor(Color.WHITE)
                tvProcess.setBackgroundColor(Color.WHITE)
                tvPerson.setBackgroundColor(Color.WHITE)
                tvRemark.setBackgroundColor(Color.WHITE)
            }
            if (helper.adapterPosition == 0) {
                tvDate.text = "时间"
                tvName.text = "流程名称"
                tvPerson.text = "流程环节"
                tvPerson.text = "审批人"
                tvRemark.text = "备注"
            } else {
                tvDate.text = if (item.handleDate == null || item.handleDate == 0L) "" else ZXTimeUtil.millis2String(item.handleDate!!)
                tvName.text = item.name
                tvProcess.text = item.process
                tvPerson.text = item.handleUser
                tvRemark.text = item.remark
            }
        }
    }

    fun setModuleColor(moduleColor: Int) {
        this.moduleColor = moduleColor
    }
}