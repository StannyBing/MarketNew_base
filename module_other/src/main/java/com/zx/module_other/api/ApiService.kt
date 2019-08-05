package com.zx.module_other.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_library.bean.NormalList
import com.zx.module_other.module.documentmanage.bean.DocumentBean
import com.zx.module_other.module.documentmanage.bean.TemplateFieldBean
import com.zx.module_other.module.law.bean.*
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workresults.bean.WorkOverAllBean
import com.zx.module_other.module.workresults.bean.WorkStatisicsBean
import okhttp3.RequestBody
import retrofit2.http.*
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
    fun getSearchLaw(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<LawSearchBean>>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_LAW + "Law/addWeixinCollect.do")
    fun addWeixinCollectLaw(@Body info: RequestBody): Observable<BaseRespose<String>>;

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_LAW + "Law/deleteWeixinCollectLaw.do")
    fun deleteWeixinCollectLaw(@Body info: RequestBody): Observable<BaseRespose<Int>>

    @GET(ApiConfigModule.URL_LAW + "Law/selectWeixinCollectLaw.do")
    fun getCollectLaw(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<LawCollectBean>>>

    @GET(ApiConfigModule.URL_LAW + "Law/selectWeixinCollectLaw.do")
    fun getCollectLawAll(@QueryMap map: Map<String, String>): Observable<BaseRespose<LawCollectResultBean>>

    @GET(ApiConfigModule.URL_LAW + "Law/selectDiscretionStandard.do")
    fun selectDiscretionStandard(@QueryMap map: Map<String, String>): Observable<BaseRespose<LawStandardQueryResultBean>>

    @GET(ApiConfigModule.URL_WORKPLAN + "getWorkPlan.do")
    fun getWorkPlan(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<WorkPlanBean>>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_WORKPLAN + "addWorkPlan.do")
    fun createWorkPlan(@Body info: RequestBody): Observable<BaseRespose<String>>

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

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_PRINT + "printingHtml.do")
    fun getDocumentPrintHtml(@Body info: RequestBody): Observable<okhttp3.ResponseBody>
}