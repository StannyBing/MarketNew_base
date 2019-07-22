package com.zx.module_supervise.module.supervise.func.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.zx.module_supervise.R
import com.zx.module_supervise.module.supervise.bean.DetailDynamicBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import com.zx.zxutils.util.ZXTimeUtil

/**
 * Created by Xiangb on 2019/7/1.
 * 功能：
 */
class DynamicAdapter(dataList: List<DetailDynamicBean>) : ZXQuickAdapter<DetailDynamicBean, ZXBaseHolder>(R.layout.item_supervise_dynamic, dataList) {

    private var moduleColor: Int = 0

    override fun convert(helper: ZXBaseHolder?, item: DetailDynamicBean?) {
        if (helper != null && item != null) {
            val tvDate = helper.getView<TextView>(R.id.tv_dynamic_date)
            val tvPerson = helper.getView<TextView>(R.id.tv_dynamic_person)
            val tvOpt = helper.getView<TextView>(R.id.tv_dynamic_opt)
            val tvRemark = helper.getView<TextView>(R.id.tv_dynamic_remark)
            if (helper.adapterPosition == 0) {
                tvDate.setTextColor(Color.WHITE)
                tvPerson.setTextColor(Color.WHITE)
                tvOpt.setTextColor(Color.WHITE)
                tvRemark.setTextColor(Color.WHITE)
                if (moduleColor != 0) {
                    tvDate.setBackgroundColor(moduleColor)
                    tvPerson.setBackgroundColor(moduleColor)
                    tvOpt.setBackgroundColor(moduleColor)
                    tvRemark.setBackgroundColor(moduleColor)
                }
            } else {
                tvDate.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_noraml))
                tvPerson.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_noraml))
                tvOpt.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_noraml))
                tvRemark.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_noraml))
                tvDate.setBackgroundColor(Color.WHITE)
                tvPerson.setBackgroundColor(Color.WHITE)
                tvOpt.setBackgroundColor(Color.WHITE)
                tvRemark.setBackgroundColor(Color.WHITE)
            }
            if (helper.adapterPosition == 0) {
                tvDate.text = "时间"
                tvPerson.text = "人员"
                tvOpt.text = "操作"
                tvRemark.text = "备注"
            } else {
                tvDate.text = if (item.fHandleTime == null || item.fHandleTime == 0L) "" else ZXTimeUtil.millis2String(item.fHandleTime!!)
                tvPerson.text = item.fHandleUser
                tvOpt.text = item.fHandleOpt
                tvRemark.text = item.fHandleRemark
            }
        }
    }

    fun setModuleColor(moduleColor: Int) {
        this.moduleColor = moduleColor
    }
}