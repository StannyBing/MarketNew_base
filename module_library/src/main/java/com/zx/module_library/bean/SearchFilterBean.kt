package com.zx.module_library.bean

/**
 * Created by Xiangb on 2019/6/26.
 * 功能：
 */
data class SearchFilterBean(var filterName: String,
                            var filterType: FilterType,
                            var values: ArrayList<ValueBean> = arrayListOf(),
                            var addDefalut: Boolean = true,
                            var visibleBy : Pair<String, String>? = null,
                            var isEnable : Boolean = true) {
    enum class FilterType {
        EDIT_TYPE,
        SELECT_TYPE
    }

    data class ValueBean(var key: String, var value: String, var isSelect: Boolean = false)
}