package com.zx.marketnew_base.api

import com.frame.zxmvp.basebean.BaseRespose
import com.zx.marketnew_base.main.bean.*
import com.zx.marketnew_base.system.bean.VerifiCodeBean
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

    @POST(ApiConfigModule.URL_APP + "sms/getCode.do")
    fun sendSms(@QueryMap map: Map<String, String>): Observable<BaseRespose<VerifiCodeBean>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL + "login.do")
    fun doLogin(@Body info: RequestBody): Observable<BaseRespose<UserBean>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL + "user/update.do")
    fun changePwd(@Body info: RequestBody): Observable<BaseRespose<String>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST(ApiConfigModule.URL + "user/update.do")
    fun changeUserInfo(@Body info: RequestBody): Observable<BaseRespose<String>>

    @POST(ApiConfigModule.URL_APP + "pushMsg/getPushMsgPage.do")
    fun getMessageList(@QueryMap map: Map<String, String>): Observable<BaseRespose<NormalList<MessageBean>>>

    @POST(ApiConfigModule.URL_APP + "pushMsg/getPushMsgInfo.do")
    fun getMessageDetail(@QueryMap map: Map<String, String>): Observable<BaseRespose<MessageBean>>

    @POST(ApiConfigModule.URL_APP + "pushMsg/deletePushMsg.do")
    fun deleteMessage(@Body info: RequestBody): Observable<BaseRespose<String>>

    @GET(ApiConfigModule.URL_BACK + "mobile/queryNewVersion.do")
    fun getVersion(): Observable<BaseRespose<VersionBean>>

    @GET(ApiConfigModule.URL_APP + "pushMsg/countUnread.do")
    fun countUnread(): Observable<BaseRespose<Int>>

    @GET(ApiConfigModule.URL_APP + "user/getUserDeptTree.do")
    fun getMailList(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<MailListBean>>>

    @POST(ApiConfigModule.URL_APP + "office/getTodoList.do")
    fun getTaskLsit(@QueryMap map: Map<String, String>): Observable<BaseRespose<List<TaskBean>>>

    @GET(ApiConfigModule.URL_APP + "office/getOfficeList.do")
    fun getOfficeInfo(): Observable<BaseRespose<OfficeBean>>

    @POST(ApiConfigModule.URL_APP + "optLog/addOptLog.do")
    fun sendXAppOpt(@Body body: RequestBody): Observable<BaseRespose<String>>
}