package com.zx.module_entity.module.special.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_entity.api.ApiService
import com.zx.module_entity.module.entity.bean.EntityBean

import com.zx.module_entity.module.special.mvp.contract.SpecialListContract
import com.zx.module_library.bean.NormalList
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class SpecialListModel : BaseModel(), SpecialListContract.Model {
    override fun entityListData(map: Map<String, String>): Observable<NormalList<EntityBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getSpecialList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}