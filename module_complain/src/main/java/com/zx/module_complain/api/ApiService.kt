package com.zx.module_complain.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.module_complain.module.info.bean.ComplainListBean
import com.zx.module_complain.module.info.bean.DeptBean
import com.zx.module_complain.module.info.bean.DetailBean
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
    @GET(ApiConfigModule.URL_COMPLAIN + "complaint/getComplaintByStatus.do")
    fun getMyComplainList(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<ComplainListBean>>>

    @GET(ApiConfigModule.URL_COMPLAIN + "complaint/getComplaintPage.do")
    fun getAllComplainList(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<ComplainListBean>>>

    @GET(ApiConfigModule.URL_COMPLAIN + "complaint/getComplaintInfo.do")
    fun getDetailInfo(@QueryMap map: Map<String, String>): Observable<BaseRespose<DetailBean>>

    @GET(ApiConfigModule.URL + "department/queary.do")
    fun getDeptList(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<DeptBean>>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL_COMPLAIN + "complaint/optComplaint.do")
    fun submitDispose(@Body info: RequestBody): Observable<BaseRespose<String>>

    @GET(ApiConfigModule.URL + "user/query.do")
    fun getUserList(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<UserBean>>>

    @POST(ApiConfigModule.URL_SUPERVISE + "file/upload.do")
    abstract fun uploadFile(@Body body: RequestBody): Observable<BaseRespose<List<String>>>
}