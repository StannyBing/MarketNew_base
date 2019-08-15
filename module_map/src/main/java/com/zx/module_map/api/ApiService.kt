package com.zx.module_map.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_map.module.bean.DeptBean
import com.zx.module_map.module.bean.DicTypeBean
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
interface ApiService {
    @GET(ApiConfigModule.URL + "dic/queryList.do")
    fun getStationList(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<DicTypeBean>>>

    @GET(ApiConfigModule.URL + "department/queary.do")
    fun getGridList(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<DeptBean>>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_SUPERVISE + "entity/update.do")
    fun updateEntityInfo(@Body body: RequestBody): Observable<BaseRespose<String>>
}