package com.zx.module_other.module.law.bean

import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity

class LawBean(wordUrl: String = "",
              isFile: Int = -1,
              departmentId: String = "",
              name: String = "",
              htmlUrl: String = "",
              orders: Int = -1,
              id: Int = -1,
              type: String = "",
              userId: String = "",
              parentId: Int = -1, var itemTypeDef: Int = -1,
              var itemName: String = "",
              var children: List<LawBean>?=null): LawBaseBean(wordUrl, isFile, departmentId, name, htmlUrl, orders, id, type, userId, parentId), MultiItemEntity {

    override fun getItemType(): Int {
        return itemTypeDef
    }

}