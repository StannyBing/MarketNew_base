package com.zx.marketnew_base.main.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.marketnew_base.api.ApiService
import com.zx.marketnew_base.main.bean.OfficeBean

import com.zx.marketnew_base.main.mvp.contract.WorkContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class WorkModel : BaseModel(), WorkContract.Model {
    override fun xappOptData(body: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .sendXAppOpt(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun officeData(): Observable<OfficeBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getOfficeInfo()
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}