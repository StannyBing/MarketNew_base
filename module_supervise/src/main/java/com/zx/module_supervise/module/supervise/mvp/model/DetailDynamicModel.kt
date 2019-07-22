package com.zx.module_supervise.module.supervise.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.api.ApiService
import com.zx.module_supervise.module.supervise.bean.DetailDynamicBean

import com.zx.module_supervise.module.supervise.mvp.contract.DetailDynamicContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailDynamicModel : BaseModel(), DetailDynamicContract.Model {
    override fun dynamicData(map: Map<String, String>): Observable<NormalList<DetailDynamicBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDetailDynamicList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}