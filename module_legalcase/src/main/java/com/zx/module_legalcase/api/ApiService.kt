package com.zx.module_legalcase.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_legalcase.module.query.bean.DeptBean
import com.zx.module_legalcase.module.query.bean.DetailBean
import com.zx.module_legalcase.module.query.bean.DynamicBean
import com.zx.module_legalcase.module.query.mvp.bean.LegalcaseListBean
import com.zx.module_library.bean.NormalList
import com.zx.module_library.bean.UserBean
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
interface ApiService {
    @GET(ApiConfigModule.URL_CASE + "case/selectToDo.do")
    fun getMyCaseToDoList(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<LegalcaseListBean>>>

    @GET(ApiConfigModule.URL_CASE + "case/selectAlready.do")
    fun getMyCaseAlreadyList(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<LegalcaseListBean>>>

    @GET(ApiConfigModule.URL_CASE + "case/getCasePage.do")
    fun getAllCaseList(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<LegalcaseListBean>>>

    @GET(ApiConfigModule.URL_CASE + "case/getCaseDetail.do")
    fun getDetail(@QueryMap map: Map<String, String>) : Observable<BaseRespose<DetailBean>>

    @GET(ApiConfigModule.URL_CASE + "caseFlow/flowLog.do")
    fun getDynamic(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<DynamicBean>>>

    @GET(ApiConfigModule.URL + "department/queary.do")
    fun getDeptList(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<DeptBean>>>

    @GET(ApiConfigModule.URL + "user/query.do")
    fun getUserList(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<UserBean>>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/start.do")
    fun caseStart(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/isCase.do")
    fun caseIsCase(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/apply.do")
    fun caseApply(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/other.do")
    fun caseOther(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/auditing.do")
    fun caseAuditing(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/examine.do")
    fun caseExamine(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/report.do")
    fun caseReport(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/stopCase.do")
    fun caseStopCase(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/firstTrial.do")
    fun caseFirstTrial(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/secondTrial.do")
    fun caseSecondTrial(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/notice.do")
    fun caseNotice(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/hearing.do")
    fun caseHearing(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/decide.do")
    fun caseDecide(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/decideAuditing.do")
    fun caseDecideAuditing(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/send.do")
    fun caseSend(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/execute.do")
    fun caseExecute(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/endCase.do")
    fun caseEndCase(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "easyFlow/start.do")
    fun caseEasyStart(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "easyFlow/auditing.do")
    fun caseEasyAuditing(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "caseFlow/transfer.do")
    fun caseTransfer(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "compelFlow/start.do")
    fun caseForceStart(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "compelFlow/auditCBR.do")
    fun caseForceCBRaudit(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "compelFlow/auditFZR.do")
    fun caseForceFZRaudit(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "compelFlow/auditJLD.do")
    fun caseForceJLDaudit(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_CASE + "compelFlow/executeCBR.do")
    fun caseForceCRBexecute(@Body body: RequestBody): Observable<BaseRespose<String>>
}