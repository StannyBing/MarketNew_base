package com.zx.module_library.app

import com.frame.zxmvp.basebean.BaseRespose
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
interface ApiService {
    @POST(BaseConfigModule.APP_HEAD + "sso/user/addUserPath.do")
    fun addUserPath(@Body body: RequestBody): Observable<BaseRespose<String>>
}