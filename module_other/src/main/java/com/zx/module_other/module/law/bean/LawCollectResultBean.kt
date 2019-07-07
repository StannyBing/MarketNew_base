package com.zx.module_other.module.law.bean

class LawCollectResultBean() {
    var total: Int = -1
    var list: List<LawCollectBean>? = null
    var records: String? = null
    var current: Int = -1

    constructor(total: Int, list: List<LawCollectBean>, records: String, current: Int) : this() {
        this.total = total
        this.list = list
        this.records = records;
        this.current = current
    }
}