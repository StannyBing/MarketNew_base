package com.zx.module_other.module.documentmanage.bean
import com.zx.module_other.module.documentmanage.func.adapter.DocumentAdpater
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity
import java.io.Serializable

data class DocumentBean(
    val children: List<Children>,
    val id: String,
    val name: String,
    val pId: Any,
    val remarks: Any,
    val status: String,
    val type: String,
    val url: Any
): MultiItemEntity{
    override fun getItemType(): Int {
        return DocumentAdpater.TYPE_LEVEL_0
    }
}

data class Children(
    val children: Any,
    val id: String,
    val name: String,
    val pId: String,
    val remarks: Any,
    val status: String,
    val type: String,
    val url: Any
):MultiItemEntity,Serializable{
    override fun getItemType(): Int {
        return DocumentAdpater.TYPE_LEVEL_1
    }
}