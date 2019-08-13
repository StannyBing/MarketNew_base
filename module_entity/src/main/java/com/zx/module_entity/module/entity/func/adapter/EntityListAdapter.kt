package com.zx.module_entity.module.entity.func.adapter

import android.annotation.SuppressLint
import com.zx.module_entity.R
import com.zx.module_entity.module.entity.bean.EntityBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter

/**
 * Created by Xiangb on 2019/6/20.
 * 功能：
 */
@SuppressLint("NewApi")
class EntityListAdapter(dataList: List<EntityBean>) : ZXRecyclerQuickAdapter<EntityBean, ZXBaseHolder>(R.layout.item_entity_normal, dataList) {
    override fun quickConvert(helper: ZXBaseHolder?, item: EntityBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_entity_itemTitle, item.fEntityName ?: "")
            helper.setText(R.id.tv_entity_itemAddress, if (item.fAddress.isNullOrEmpty()) {
                "暂无地址"
            } else {
                item.fAddress
            })
            helper.setText(R.id.tv_entity_itemArea, (item.fStation ?: "") + "-" + (item.fGrid ?: ""))
            helper.setText(R.id.tv_entity_type, item.getCompany())
//            helper.setText(R.id.tv_entity_type, "")
//            helper.getView<ImageView>(R.id.iv_entity_type).drawable.mutate().setTint(ContextCompat.getColor(mContext, when (item.fCreditLevel) {
//                "A" -> R.color.greenyellow
//                "B" -> R.color.yellow
//                "C" -> R.color.orange
//                "D" -> R.color.orangered
//                "Z" -> R.color.gray
//                else -> R.color.greenyellow
//            }))
        }
    }

    private fun EntityBean.getCompany(): String {
        if (fEntityName.isNullOrEmpty()) {
            return ""
        } else if (fAddress.isNullOrEmpty()) {
            val companyName = fEntityName!!.replace("有限公司|有限责任公司".toRegex(), "")
            if (companyName.length >= 4) {
                return companyName.substring(0, 4)
            } else {
                return companyName
            }
        } else {
            val dividerList = fAddress!!.split("省|市|区|县|镇|乡".toRegex())
            var companyName = fEntityName!!
            if (dividerList.size > 1) {
                dividerList.forEach {
                    companyName = companyName.replace(it, "")
                }
                companyName = companyName.replace("省|市|区|县|镇|乡".toRegex(), "")
            }
            companyName = companyName.replace("有限责任公司|有限公司|公司".toRegex(), "")
            if (companyName.length >= 4) {
                return companyName.substring(0, 4)
            } else {
                return companyName
            }
        }
    }
}