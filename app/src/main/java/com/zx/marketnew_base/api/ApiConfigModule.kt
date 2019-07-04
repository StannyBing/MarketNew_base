package com.zx.marketnew_base.api

import com.zx.module_library.app.BaseConfigModule

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
class ApiConfigModule : BaseConfigModule(ApiService::class.java, "app") {
    companion object {
        const val URL = APP_HEAD + "sso/"
        const val URL_APP = APP_HEAD + "app/"
        const val URL_BACK = APP_HEAD + "superviseBack/"
    }
}