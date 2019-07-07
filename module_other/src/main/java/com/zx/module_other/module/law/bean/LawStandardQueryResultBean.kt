package com.zx.module_other.module.law.bean

class LawStandardQueryResultBean() {
    var total: Int = -1
    var list: List<LawStandardQueryBean>? = null
    var records: String? = null
    var current: Int = -1
    var pageNo: Int = -1
    var pages: Int = -1
    var pageSize: Int = -1
    var size: Int = -1
    var searchCount: Boolean = false

    constructor(total: Int, list: List<LawStandardQueryBean>, records: String, current: Int, page: Int, pages: Int, pageSize: Int, size: Int, searchCount: Boolean) : this() {
        this.total = total
        this.list = list
        this.records = records;
        this.current = current
        this.pageNo = pageNo
        this.pages = pages
        this.pageSize = pageSize
        this.size = size
        this.searchCount = searchCount
    }
}