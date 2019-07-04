package com.zx.marketnew_base.system.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.marketnew_base.api.ApiService

import com.zx.marketnew_base.system.mvp.contract.ChangePwdContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class ChangePwdModel : BaseModel(), ChangePwdContract.Model {
    override fun changePwdData(info : RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .changePwd(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}