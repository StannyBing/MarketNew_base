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
                            var isEnable : Boolean = true,
                            var singleFunc : Boolean = false)//是否独立处理，即在该条筛选项变化是否需要立即回调（默认为了节约计算量，不会回调，只会在点击确定后回调）
 {
    enum class FilterType {
        EDIT_TYPE,
        SELECT_TYPE
    }

    data class ValueBean(var key: String, var value: String, var isSelect: Boolean = false)
}