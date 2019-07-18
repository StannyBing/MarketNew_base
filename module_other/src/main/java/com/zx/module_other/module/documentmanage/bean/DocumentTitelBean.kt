package com.zx.module_other.module.documentmanage.bean

import com.zx.module_other.module.documentmanage.func.adapter.DocumentAdpater
import com.zx.zxutils.other.QuickAdapter.entity.AbstractExpandableItem
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity

data class DocumentTitelBean(var contnet:String): MultiItemEntity {

    override fun getItemType(): Int {
        return DocumentAdpater.TYPE_LEVEL_0
    }

}