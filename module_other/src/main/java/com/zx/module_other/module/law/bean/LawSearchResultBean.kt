package com.zx.module_other.module.law.bean

class LawSearchResultBean(){
    var total: Int = -1
    var list: List<LawSearchBean>? = null
    var pageNO: Int = -1
    var pageSize: Int = -1
    var pages: Int = -1
    var size: Int = -1

    constructor(total: Int, list: List<LawSearchBean>, pageNO: Int, pageSize: Int, pages: Int, size: Int) : this() {
        this.total = total
        this.list = list
        this.pageNO = pageNO
        this.pageSize = pageSize
        this.pages = pages
        this.size = size
    }
}