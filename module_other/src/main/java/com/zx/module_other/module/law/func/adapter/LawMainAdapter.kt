package com.zx.module_other.module.law.func.adapter

import com.zx.module_other.R
import com.zx.module_other.module.law.bean.LawMainBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

class LawMainAdapter<T>(dataBeans: List<T>) :ZXQuickAdapter<T,ZXBaseHolder>(R.layout.item_string,dataBeans) {
    override fun convert(helper: ZXBaseHolder?, item: T) {
       if (item is LawMainBean){
           if (helper!=null){
               helper.setText(R.id.tvLawName,item.name)
           }
       }
    }
}