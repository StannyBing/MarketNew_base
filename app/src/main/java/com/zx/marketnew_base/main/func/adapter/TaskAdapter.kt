package com.zx.marketnew_base.main.func.adapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.TaskBean
import com.zx.module_complain.XAppComplain
import com.zx.module_legalcase.XAppLegalcase
import com.zx.module_library.func.tool.TaskTimeUtil
import com.zx.module_supervise.XAppSupervise
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter

/**
 * Created by Xiangb on 2019/3/14.
 * 功能：任务通用适配器
 */
@SuppressLint("NewApi")
class TaskAdapter(databeans: List<TaskBean>) : ZXRecyclerQuickAdapter<TaskBean, ZXBaseHolder>(R.layout.item_task_normal, databeans) {
    override fun quickConvert(helper: ZXBaseHolder?, item: TaskBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_task_type, when (item.businessType) {
                "case" -> XAppLegalcase.HANDLE.name
                "complaint" -> XAppComplain.LIST.name
                "entityTask" -> XAppSupervise.SUPERVISE.name
                else -> ""
            })
            helper.setText(R.id.tv_task_title, when (item.businessType) {
                "case" -> item.caseName ?: ""
                "complaint" -> (item.fName ?: "") + (item.fType ?: "") + (item.fEntityName ?: "")
                "entityTask" -> item.fName ?: ""
                else -> ""
            })
            helper.setText(R.id.tv_task_info, when (item.businessType) {
                "case" -> if (item.enterpriseAddress.isNullOrEmpty()) "暂无地址" else item.enterpriseAddress
                "complaint" -> if (item.fEntityAddress.isNullOrEmpty()) "暂无地址" else item.fEntityAddress
                "entityTask" -> item.fEntityName ?: ""
                else -> ""
            })
            helper.getView<LinearLayout>(R.id.ll_task_typebg).background.setTint(ContextCompat.getColor(mContext, when (item.businessType) {
                "case" -> XAppLegalcase.HANDLE.moduleColor
                "complaint" -> XAppComplain.LIST.moduleColor
                "entityTask" -> XAppSupervise.SUPERVISE.moduleColor
                else -> R.color.colorPrimary
            }))
            helper.setBackgroundRes(R.id.iv_task_type, when (item.businessType) {
                "case" -> XAppLegalcase.HANDLE.appIcon
                "complaint" -> XAppComplain.LIST.appIcon
                "entityTask" -> XAppSupervise.SUPERVISE.appIcon
                else -> R.drawable.logo
            })
            helper.setText(R.id.tv_task_date, when (item.businessType) {
                "case" -> if (item.foundDate == null) "" else TaskTimeUtil.displayTime(item.foundDate!!)
                "complaint" -> if (item.fRegTime == null) "" else TaskTimeUtil.displayTime(item.fRegTime!!)
                "entityTask" -> if (item.fStartDate == null) "" else TaskTimeUtil.displayTime(item.fStartDate!!)
                else -> ""
            })
        }
    }
}