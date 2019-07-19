package com.zx.module_entity.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_entity.module.entity.bean.*
import com.zx.module_entity.module.entity.bean.EntityBean
import com.zx.module_entity.module.special.bean.DeptBean
import com.zx.module_library.bean.NormalList
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
interface ApiService {
    @GET(ApiConfigModule.URL_SUPERVISE + "taskDo/getEntityPage.do")
    fun getEntityList(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<EntityBean>>>

    @GET(ApiConfigModule.URL + "dic/queryList.do")
    fun getDicTypeList(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<DicTypeBean>>>

    @GET(ApiConfigModule.URL_SUPERVISE + "entity/getEntityLevel.do")
    fun getEntityLevel(): Observable<BaseRespose<List<EntityLevelBean>>>

    @GET(ApiConfigModule.URL_SUPERVISE + "entity/getEntityStation.do")
    fun getEntityStation(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<EntityStationBean>>>

    @GET(ApiConfigModule.URL_SUPERVISE + "entity/getEntityInfo.do")
    fun getSearchDetail(@QueryMap map: Map<String, String>): Observable<BaseRespose<EntityDetailBean>>

    @GET(ApiConfigModule.URL_SUPERVISE + "entitySpecial/getEntitySpecialInfo.do")
    fun getSearchSpecialDetail(@QueryMap map: Map<String, String>): Observable<BaseRespose<EntityDetailBean>>

    @GET(ApiConfigModule.URL_SUPERVISE + "entity/getEntityDetailInfo.do")
    fun getSearchBizlicNumDetail(@QueryMap map: Map<String, String>): Observable<BaseRespose<EntityDetailBean>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_SUPERVISE + "entity/update.do")
    fun modifyInfo(@Body body: RequestBody): Observable<BaseRespose<Any>>

    @GET(ApiConfigModule.URL_SUPERVISE + "entity/getEntityCreditInfo.do")
    fun getCreditInfo(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<CreditBean>>>

    @GET(ApiConfigModule.URL_SUPERVISE + "entity/getEntityBusinessInfo.do")
    fun getBusinessInfo(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<BusinessBean>>>

    @POST(ApiConfigModule.URL_SUPERVISE + "file/upload.do")
    fun uploadFile(@Body body: RequestBody): Observable<BaseRespose<List<String>>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_SUPERVISE + "file/delete.do")
    fun deleteFile(@Body body: RequestBody): Observable<BaseRespose<Any>>

    @GET(ApiConfigModule.URL + "department/queary.do")
    fun getDeptList(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<DeptBean>>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_SUPERVISE + "entitySpecial/addEntitySpecialInfo.do")
    fun addSpecialEntity(@Body body: RequestBody): Observable<BaseRespose<String>>

    @GET(ApiConfigModule.URL_SUPERVISE + "entitySpecial/getEntitySpecialPage.do")
    fun getSpecialList(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<EntityBean>>>
}