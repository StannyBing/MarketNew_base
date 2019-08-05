package com.zx.module_supervise.module.task.func.adapter

import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.zx.module_library.func.tool.TaskTimeUtil
import com.zx.module_supervise.R
import com.zx.module_supervise.module.task.bean.TaskListBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter

/**
 * Created by Xiangb on 2019/6/20.
 * 功能：
 */
class TaskListAdapter(dataList: List<TaskListBean>) : ZXRecyclerQuickAdapter<TaskListBean, ZXBaseHolder>(R.layout.item_supervise_normal, dataList) {
    override fun quickConvert(helper: ZXBaseHolder?, item: TaskListBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_supervise_itemTitle, item.fName ?: "")
            helper.setText(R.id.tv_supervise_itemInfo, item.fEntityName)
            helper.setText(R.id.tv_supervise_itemDate,
                    if (item.fStartDate == null) {
                        ""
                    } else {
                        TaskTimeUtil.displayTime(item.fStartDate!!)
//                        ZXTimeUtil.getTime(item.fRegTime!!).replace(" ", "\n")
                    })
            val drawable = ContextCompat.getDrawable(mContext, when (item.fStatus) {
                "101" -> R.drawable.supervise_handle
                "102" -> R.drawable.supervise_audit0
                "103" -> R.drawable.supervise_audit1
                "104" -> R.drawable.supervise_audit2
                else -> R.drawable.supervise_over
            })
            helper.getView<ImageView>(R.id.iv_supervise_itemPic).setImageDrawable(drawable)
        }
    }
}