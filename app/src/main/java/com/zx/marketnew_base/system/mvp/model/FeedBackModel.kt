package com.zx.marketnew_base.system.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.marketnew_base.api.ApiService

import com.zx.marketnew_base.system.mvp.contract.FeedBackContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class FeedBackModel : BaseModel(), FeedBackContract.Model {
    override fun feedBackData(body: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .addFeedBack(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}