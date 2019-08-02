package com.zx.marketnew_base.main.func.adapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.MessageBean
import com.zx.module_complain.XAppComplain
import com.zx.module_legalcase.XAppLegalcase
import com.zx.module_library.func.tool.TaskTimeUtil
import com.zx.module_supervise.XAppSupervise
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/3/14.
 * 功能：任务通用适配器
 */
@SuppressLint("NewApi")
class MessageAdapter(databeans: List<MessageBean>) : ZXQuickAdapter<MessageBean, ZXBaseHolder>(R.layout.item_message, databeans) {
    override fun convert(helper: ZXBaseHolder?, item: MessageBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_message_itemTitle, item.title)
            helper.setText(R.id.tv_message_itemInfo, item.ticker)
            helper.setText(R.id.tv_message_itemDate, TaskTimeUtil.displayTime(item.inserDate))
            helper.setBackgroundRes(R.id.iv_message_type, when (item.messageType) {
                "综合执法" -> XAppLegalcase.HANDLE.appIcon
                "投诉举报" -> XAppComplain.LIST.appIcon
                "专项检查" -> XAppSupervise.SUPERVISE.appIcon
                else -> R.drawable.app_msg
            })
            helper.getView<LinearLayout>(R.id.ll_message_typebg).background.setTint(ContextCompat.getColor(mContext, when (item.messageType) {
                "综合执法" -> XAppLegalcase.HANDLE.moduleColor
                "投诉举报" -> XAppComplain.LIST.moduleColor
                "专项检查" -> XAppSupervise.SUPERVISE.moduleColor
                else -> R.color.colorPrimary
            }))
            helper.getView<ImageView>(R.id.iv_msg_read).visibility = if (item.isRead == 0) View.VISIBLE else View.GONE
//            helper.setText(R.id.tv_message_type, if (item.displayType == "通知") {
//                helper.getView<TextView>(R.id.tv_message_type).visibility = View.VISIBLE
//                item.messageType
//            } else {
//                helper.getView<TextView>(R.id.tv_message_type).visibility = View.GONE
//                ""
//            })
        }
    }

}