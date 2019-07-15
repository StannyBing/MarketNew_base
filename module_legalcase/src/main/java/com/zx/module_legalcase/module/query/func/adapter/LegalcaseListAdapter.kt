package com.zx.module_legalcase.module.query.func.adapter

import com.zx.module_legalcase.R
import com.zx.module_legalcase.module.query.mvp.bean.LegalcaseListBean
import com.zx.module_library.func.tool.TaskTimeUtil
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter

/**
 * Created by Xiangb on 2019/7/8.
 * 功能：
 */
class LegalcaseListAdapter(dataList: List<LegalcaseListBean>) : ZXRecyclerQuickAdapter<LegalcaseListBean, ZXBaseHolder>(R.layout.item_legalcase_normal, dataList) {
    override fun quickConvert(helper: ZXBaseHolder?, item: LegalcaseListBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_legalcase_itemTitle, item.caseName ?: "")
            helper.setText(R.id.tv_legalcase_itemInfo, if (item.enterpriseAddress.isNullOrEmpty()) {
                "暂无地址"
            } else {
                item.enterpriseAddress
            })
            helper.setText(R.id.tv_legalcase_itemDate,
                    if (item.foundDate == null) {
                        ""
                    } else {
                        TaskTimeUtil.displayTime(item.foundDate!!)
//                        ZXTimeUtil.getTime(item.foundDate!!).replace(" ", "\n")
                    })
//            helper.setText(R.id.tv_legalcase_itemStatus, "状态：" + if (item.isCompel == "0") {
//                item.compelStatusName
//            } else {
//                item.statusName
//            })
            if ("01" == item.domainCode) {
                helper.setBackgroundRes(R.id.iv_legalcase_itemPic, R.drawable.case_gs)
                helper.setText(R.id.tv_legalcase_itemType, "市场经营")
            } else if ("02" == item.domainCode) {
                helper.setBackgroundRes(R.id.iv_legalcase_itemPic, R.drawable.case_zj)
                helper.setText(R.id.tv_legalcase_itemType, "产品质量")
            } else if ("03" == item.domainCode) {
                helper.setBackgroundRes(R.id.iv_legalcase_itemPic, R.drawable.case_syj)
                helper.setText(R.id.tv_legalcase_itemType, "食品药品")
            } else {
                helper.setBackgroundRes(R.id.iv_legalcase_itemPic, R.drawable.case_gs)
                helper.setText(R.id.tv_legalcase_itemType, "市场经营")
            }
//            val id = mContext.resources.getIdentifier("legalcase_task_${item.fStatus}", "drawable", mContext.packageName)
//            helper.setBackgroundRes(R.id.iv_legalcase_itemPic, id)
        }
    }
}