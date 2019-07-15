package com.zx.module_entity.api

import com.zx.module_library.app.BaseConfigModule

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
class ApiConfigModule : BaseConfigModule(ApiService::class.java, "entity"){
    companion object {
        const val URL = APP_HEAD + "sso/"
        const val URL_SUPERVISE = APP_HEAD + "supervise/"
    }
}