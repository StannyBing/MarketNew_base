package com.zx.module_entity.module.entity.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_entity.api.ApiService
import com.zx.module_entity.module.entity.bean.BusinessBean

import com.zx.module_entity.module.entity.mvp.contract.DetailBusinessContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailBusinessModel : BaseModel(), DetailBusinessContract.Model {
    override fun businessData(map: Map<String, String>): Observable<List<BusinessBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getBusinessInfo(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}