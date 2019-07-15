package com.zx.module_entity.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_entity.module.entity.bean.DicTypeBean
import com.zx.module_entity.module.entity.bean.EntityLevelBean
import com.zx.module_entity.module.entity.bean.EntityStationBean
import com.zx.module_entity.module.entity.func.adapter.EntityBean
import com.zx.module_library.bean.NormalList
import retrofit2.http.GET
import retrofit2.http.QueryMap
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
    abstract fun getEntityStation(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<EntityStationBean>>>
}