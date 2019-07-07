package com.zx.module_other.module.law.bean

import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity
import java.io.Serializable

class LawStandardQueryBean(var violateContent: String?=null, var illegalAct: String?=null, var punishRule: String?=null, var standardSource: String?=null,
                           var violateLaw: String?=null, var num: String?=null, var violateRule: String?=null, var punishContent: String?=null, var type: String?=null,
                           var userId: String?=null, var punishLaw: String?=null, var guid: String?=null, var discretionStandard: String?=null, var itemTypeDef: Int = -1) : Serializable,MultiItemEntity {

    override fun getItemType(): Int {
        return itemTypeDef
    }
}