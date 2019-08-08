package com.zx.marketnew_base.main.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.marketnew_base.api.ApiService

import com.zx.marketnew_base.main.mvp.contract.UserDetailContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class UserDetailModel : BaseModel(), UserDetailContract.Model {

    override fun changeUserInfo(info : RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .changeUserInfo(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun fileUploadData(body: RequestBody): Observable<List<String>> {
        return mRepositoryManager.obtainRetrofitService(com.zx.module_supervise.api.ApiService::class.java)
                .uploadFile(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }
}