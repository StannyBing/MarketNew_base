package com.zx.module_other.api

import com.zx.module_library.app.BaseConfigModule

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
class ApiConfigModule : BaseConfigModule(ApiService::class.java, "other"){
    companion object {
        const val URL_LAW = APP_HEAD + "law/"
        const val URL_WORKPLAN = APP_HEAD + "app/workPlan/"
    }
}
