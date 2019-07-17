package com.zx.module_entity.module.entity.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_entity.api.ApiService
import com.zx.module_entity.module.entity.bean.EntityDetailBean

import com.zx.module_entity.module.entity.mvp.contract.DetailContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailModel : BaseModel(), DetailContract.Model {
    override fun entityDetailNormalData(map: Map<String, String>): Observable<EntityDetailBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getSearchDetail(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun entityDetailSpecialData(map: Map<String, String>): Observable<EntityDetailBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getSearchSpecialDetail(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun entityDetailBizlicData(map: Map<String, String>): Observable<EntityDetailBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getSearchBizlicNumDetail(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}