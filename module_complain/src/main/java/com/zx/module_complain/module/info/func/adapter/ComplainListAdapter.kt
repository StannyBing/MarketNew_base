package com.zx.module_complain.module.info.func.adapter

import com.zx.module_complain.R
import com.zx.module_complain.module.info.bean.ComplainListBean
import com.zx.module_library.func.tool.TaskTimeUtil
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter

/**
 * Created by Xiangb on 2019/6/20.
 * 功能：
 */
class ComplainListAdapter(dataList: List<ComplainListBean>) : ZXRecyclerQuickAdapter<ComplainListBean, ZXBaseHolder>(R.layout.item_complain_normal, dataList) {
    override fun quickConvert(helper: ZXBaseHolder?, item: ComplainListBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_complain_itemTitle, (item.fName ?: "") +
                    (item.fType ?: "") +
                    (item.fEntityName ?: ""))
            helper.setText(R.id.tv_complain_itemInfo, if (item.fEntityAddress.isNullOrEmpty()) {
                "暂无地址"
            } else {
                item.fEntityAddress
            })
            helper.setText(R.id.tv_complain_itemDate,
                    if (item.fRegTime == null) {
                        ""
                    } else {
                        TaskTimeUtil.displayTime(item.fRegTime!!)
//                        ZXTimeUtil.getTime(item.fRegTime!!).replace(" ", "\n")
                    })
            val id = mContext.resources.getIdentifier("complain_task_${item.fStatus}", "drawable", mContext.packageName)
            helper.setBackgroundRes(R.id.iv_complain_itemPic, id)
        }
    }
}