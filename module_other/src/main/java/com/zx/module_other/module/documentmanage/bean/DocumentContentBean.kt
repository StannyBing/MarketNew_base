package com.zx.module_other.module.documentmanage.bean

import com.zx.module_other.module.documentmanage.func.adapter.DocumentAdpater
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity
import java.io.Serializable

data class DocumentContentBean(var contnet:String): MultiItemEntity, Serializable {

    override fun getItemType(): Int {
        return DocumentAdpater.TYPE_LEVEL_1
    }

}