package com.zx.module_statistics.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_statistics.module.bean.ChartBean
import retrofit2.http.GET
import retrofit2.http.QueryMap
import rx.Observable

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
interface ApiService {
    @GET(ApiConfigModule.URL_SUPERVISE + "inspect/countByArea.do")
    fun getDailyByArea(@QueryMap map: Map<String, String>) : Observable<BaseRespose<List<ChartBean>>>

    @GET(ApiConfigModule.URL_SUPERVISE + "inspect/countByTrend.do")
    fun getDailyByTrend(@QueryMap map: Map<String, String>) : Observable<BaseRespose<List<ChartBean>>>

    @GET(ApiConfigModule.URL_SUPERVISE + "inspect/countByResult.do")
    fun getDailyByResult(@QueryMap map: Map<String, String>) : Observable<BaseRespose<List<ChartBean>>>
}