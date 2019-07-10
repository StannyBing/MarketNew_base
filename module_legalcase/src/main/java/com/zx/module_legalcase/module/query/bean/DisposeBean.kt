package com.zx.module_legalcase.module.query.bean

/**
 * Created by Xiangb on 2019/7/2.
 * 功能：
 */
data class DisposeBean(var disposeType: DisposeType = DisposeType.Edit,
                       var disposeName: String,
                       var disposeValue: ArrayList<ValueBean> = arrayListOf(),
                       var isRequired: Boolean = false,//是否必填/必选
                       var hasDefalut: Boolean = true,//是否有默认值
                       var resultValue: String = "",
                       var resultKey : String = "") {

    enum class DisposeType {
        Text,
        Edit,
        Spinner,
        Time
    }

    data class ValueBean(var key: String, var value: String, var isSelect: Boolean = false) {

    }

}