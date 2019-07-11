package com.zx.module_legalcase.api

import com.zx.module_library.app.BaseConfigModule

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
class ApiConfigModule : BaseConfigModule(ApiService::class.java, "legalcase"){
    companion object {
        const val URL = APP_HEAD + "sso/"
        const val URL_CASE = APP_HEAD + "case/"
    }
}