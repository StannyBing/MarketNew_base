package com.zx.module_entity.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_entity.module.entity.bean.DicTypeBean
import com.zx.module_entity.module.entity.bean.EntityDetailBean
import com.zx.module_entity.module.entity.bean.EntityLevelBean
import com.zx.module_entity.module.entity.bean.EntityStationBean
import com.zx.module_entity.module.entity.func.adapter.EntityBean
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
}