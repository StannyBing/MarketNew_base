package com.zx.marketnew_base.system.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.zx.marketnew_base.api.ApiService
import com.zx.marketnew_base.system.bean.VerifiCodeBean

import com.zx.marketnew_base.system.mvp.contract.ForgetPwdContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class ForgetPwdModel : BaseModel(), ForgetPwdContract.Model {
    override fun smsData(map: Map<String, String>): Observable<VerifiCodeBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .sendSms(map)
                .compose(RxHelper.handleResult())
    }


}