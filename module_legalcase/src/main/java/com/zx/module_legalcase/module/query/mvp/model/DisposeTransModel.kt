package com.zx.module_legalcase.module.query.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_legalcase.api.ApiService

import com.zx.module_legalcase.module.query.mvp.contract.DisposeTransContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DisposeTransModel : BaseModel(), DisposeTransContract.Model {
    override fun caseTransData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseTransfer(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}