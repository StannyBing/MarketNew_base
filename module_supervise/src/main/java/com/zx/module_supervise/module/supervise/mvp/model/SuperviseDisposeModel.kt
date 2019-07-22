package com.zx.module_supervise.module.supervise.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_supervise.api.ApiService

import com.zx.module_supervise.module.supervise.mvp.contract.SuperviseDisposeContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class SuperviseDisposeModel : BaseModel(), SuperviseDisposeContract.Model {
    override fun submitTaskData(body: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .submitDispose(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}