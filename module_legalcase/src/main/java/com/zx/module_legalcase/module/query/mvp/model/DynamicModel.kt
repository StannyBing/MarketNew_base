package com.zx.module_legalcase.module.query.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_legalcase.api.ApiService
import com.zx.module_legalcase.module.query.bean.DynamicBean

import com.zx.module_legalcase.module.query.mvp.contract.DynamicContract
import com.zx.module_library.bean.NormalList
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DynamicModel : BaseModel(), DynamicContract.Model {
    override fun dynamicData(map: Map<String, String>): Observable<NormalList<DynamicBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDynamic(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}