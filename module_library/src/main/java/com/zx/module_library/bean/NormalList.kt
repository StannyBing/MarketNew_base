package com.zx.module_library.bean

/**
 * Created by Xiangb on 2019/3/14.
 * 功能：列表基类
 */
data class NormalList<T>(var total: Int, var pageNo: Int, var pageSize: Int, var pages: Int, var size: Int, var list: List<T>)