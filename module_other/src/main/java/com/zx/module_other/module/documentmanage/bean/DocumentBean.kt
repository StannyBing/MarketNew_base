package com.zx.module_other.module.documentmanage.bean
import com.zx.module_other.module.documentmanage.func.adapter.DocumentAdpater
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity
import java.io.Serializable

data class DocumentBean(
    val children: List<Children>,
    val id: String,
    val name: String,
    val pId: Any?=null,
    val remarks: Any?=null,
    val status: String,
    val type: String,
    val url: Any?=null
): MultiItemEntity{
    override fun getItemType(): Int {
        return DocumentAdpater.TYPE_LEVEL_0
    }
}

data class Children(
    val children: Any?=null,
    val id: String,
    val name: String,
    val pId: String,
    val remarks: Any?=null,
    val status: String,
    val type: String,
    val url: Any?=null
):MultiItemEntity,Serializable{
    override fun getItemType(): Int {
        return DocumentAdpater.TYPE_LEVEL_1
    }
}