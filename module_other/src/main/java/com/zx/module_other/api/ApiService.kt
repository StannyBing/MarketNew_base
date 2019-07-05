package com.zx.module_other.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_other.module.law.bean.LawBean
import com.zx.module_other.module.law.bean.LawDetailBean
import com.zx.module_other.module.law.bean.LawSearchResultBean
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
    fun postAddWeixinCollect(): Observable<BaseRespose<Int>>;
}