package com.zx.module_other.module.documentmanage.func.adapter

import com.zx.module_other.R
import com.zx.module_other.module.workstatisics.bean.DocumentBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXMultiItemQuickAdapter
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity


class DocumentAdpater<T : MultiItemEntity?>(dataBeans: List<T>) : ZXMultiItemQuickAdapter<T, ZXBaseHolder>(dataBeans) {
    companion object {
        val TYPE_LEVEL_0 = 0
        val TYPE_LEVEL_1 = 1
    }

    init {
        addItemType(TYPE_LEVEL_0, R.layout.item_document_title)
        addItemType(TYPE_LEVEL_1, R.layout.item_document_title)
    }

    override fun convert(helper: ZXBaseHolder?, item: T) {
        when (helper!!.itemViewType) {
            TYPE_LEVEL_0 -> {
            }
            TYPE_LEVEL_1 -> {
            }
        }
    }


}