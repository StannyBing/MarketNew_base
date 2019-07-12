package com.zx.module_other.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_other.module.law.bean.*
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workstatisics.bean.WorkStatisicsBean
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap
import rx.Observable

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
interface ApiService {
    @GET(ApiConfigModule.URL_LAW + "Law/select.do")
    fun getLawList(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<LawBean>>>

    @GET(ApiConfigModule.URL_LAW + "Law/getLawInfo.do")
    fun getLawDetail(@QueryMap map: Map<String, String>): Observable<BaseRespose<LawDetailBean>>

    @GET(ApiConfigModule.URL_LAW + "Law/selectLaw.do")
    fun getSearchLaw(@QueryMap map: Map<String, String>): Observable<BaseRespose<LawSearchResultBean>>

    @POST(ApiConfigModule.URL_LAW + "Law/addWeixinCollect.do")
    fun addWeixinCollectLaw(@QueryMap map: Map<String, String>): Observable<BaseRespose<String>>;

    @POST(ApiConfigModule.URL_LAW + "Law/deleteWeixinCollectLaw.do")
    fun deleteWeixinCollectLaw(@QueryMap map: Map<String, String>): Observable<BaseRespose<Int>>

    @GET(ApiConfigModule.URL_LAW + "Law/selectWeixinCollectLaw.do")
    fun getCollectLaw(@QueryMap map: Map<String, String>): Observable<BaseRespose<LawCollectResultBean>>

    @GET(ApiConfigModule.URL_LAW + "Law/selectDiscretionStandard.do")
    fun selectDiscretionStandard(@QueryMap map: Map<String, String>): Observable<BaseRespose<LawStandardQueryResultBean>>

    @GET(ApiConfigModule.URL_WORKPLAN + "getWorkPlan.do")
    fun getWorkPlan(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<WorkPlanBean>>>

    @GET(ApiConfigModule.URL_LAW + "")
    fun createWorkPlan(@QueryMap map: Map<String, String>): Observable<BaseRespose<String>>

    @GET(ApiConfigModule.URL_WORKPLAN + "getWorkResultRecently.do")
    fun getWorkSatistics(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<WorkStatisicsBean>>>
}