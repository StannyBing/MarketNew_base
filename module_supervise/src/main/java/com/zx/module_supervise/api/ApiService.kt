package com.zx.module_supervise.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.module.daily.bean.DailyListBean
import com.zx.module_supervise.module.supervise.bean.*
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
interface ApiService {

    @GET(ApiConfigModule.URL_SUPERVISE + "taskPlan/getTaskEntityPage.do")
    fun getSuperviseList(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<SuperviseListBean>>>

    @GET(ApiConfigModule.URL_SUPERVISE + "taskPlan/queryDetails.do")
    fun getSuperviseDetailTaskInfo(@QueryMap map: Map<String, String>): Observable<BaseRespose<TaskInfoBean>>

    @GET(ApiConfigModule.URL_SUPERVISE + "taskDo/queryEntityById.do")
    fun getSuperviseDetailEntityInfo(@QueryMap map: Map<String, String>): Observable<BaseRespose<EntityInfoBean>>

    @GET(ApiConfigModule.URL_SUPERVISE + "taskDo/queryItemById.do")
    fun getSuperviseCheckList(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<SuperviseCheckBean>>>

    @GET(ApiConfigModule.URL_SUPERVISE + "taskDo/queryLog.do")
    fun getDetailDynamicList(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<DetailDynamicBean>>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_SUPERVISE + "taskDo/saveResult.do")
    fun submitDispose(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_SUPERVISE + "taskDo/entityInspect.do")
    fun submitAuidt(@Body body: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_SUPERVISE + "taskDo/entityBack.do")
    fun entityBack(@Body body: RequestBody): Observable<BaseRespose<String>>

    @POST(ApiConfigModule.URL_SUPERVISE + "file/upload.do")
    fun uploadFile(@Body body: RequestBody): Observable<BaseRespose<List<String>>>

    @GET(ApiConfigModule.URL_SUPERVISE + "inspect/queryList.do")
    fun getDailyList(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<DailyListBean>>>

}