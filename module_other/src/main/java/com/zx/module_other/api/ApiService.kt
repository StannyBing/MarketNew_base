package com.zx.module_other.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_other.module.documentmanage.bean.DocumentBean
import com.zx.module_other.module.documentmanage.bean.TemplateFieldBean
import com.zx.module_other.module.law.bean.*
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workstatisics.bean.WorkOverAllBean
import com.zx.module_other.module.workstatisics.bean.WorkStatisicsBean
import retrofit2.Response
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

    @POST(ApiConfigModule.URL_WORKPLAN + "addWorkPlan.do")
    fun createWorkPlan(@QueryMap map: Map<String, String>): Observable<BaseRespose<String>>

    @GET(ApiConfigModule.URL_WORKPLAN + "getWorkResultRecently.do")
    fun getWorkSatistics(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<WorkStatisicsBean>>>


    @GET(ApiConfigModule.URL_WORKPLAN + "getWorkResult.do")
    fun getWorkOverAlls(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<WorkOverAllBean>>>


    @GET(ApiConfigModule.URL_DOCUMENT + "queryTree.do")
    fun getDocument(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<DocumentBean>>>

    @GET(ApiConfigModule.URL_DOCUMENT + "queryField.do")
    fun getDocumentField(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<TemplateFieldBean>>>

    @GET(ApiConfigModule.URL_DOCUMENT + "queryDetailHtml.do")
    fun getDocumentSee(@QueryMap map: Map<String, String>): Observable<okhttp3.ResponseBody>

    @GET(ApiConfigModule.URL_DOCUMENT + "queryField.do")
    fun getDocumentPrintHtml(@QueryMap map: Map<String, String>): Observable<okhttp3.ResponseBody>
}