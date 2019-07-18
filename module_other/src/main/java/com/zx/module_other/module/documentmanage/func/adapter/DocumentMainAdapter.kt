package com.zx.module_other.module.documentmanage.func.adapter

import com.zx.module_other.R
import com.zx.module_other.module.documentmanage.bean.DocumentMainBean
import com.zx.module_other.module.law.bean.LawMainBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

class DocumentMainAdapter<T>(dataBeans: List<T>) :ZXQuickAdapter<T,ZXBaseHolder>(R.layout.item_document_string,dataBeans) {
    override fun convert(helper: ZXBaseHolder?, item: T) {
       if (item is DocumentMainBean){
           if (helper!=null){
               helper.setText(R.id.tv_document_name,item.name)
           }
       }
    }
}