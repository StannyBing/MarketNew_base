package com.zx.module_statistics.api

import com.zx.module_library.app.BaseConfigModule

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
class ApiConfigModule : BaseConfigModule(ApiService::class.java, "statistics"){
    companion object {
        const val URL_SUPERVISE = APP_HEAD + "supervise/"
    }
}
