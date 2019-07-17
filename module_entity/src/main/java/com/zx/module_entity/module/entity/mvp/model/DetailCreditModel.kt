package com.zx.module_entity.module.entity.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_entity.api.ApiService
import com.zx.module_entity.module.entity.bean.CreditBean

import com.zx.module_entity.module.entity.mvp.contract.DetailCreditContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailCreditModel : BaseModel(), DetailCreditContract.Model {

    override fun creditListData(map: Map<String, String>): Observable<List<CreditBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getCreditInfo(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}