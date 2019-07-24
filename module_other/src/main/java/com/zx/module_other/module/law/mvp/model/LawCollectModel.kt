package com.zx.module_other.module.law.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_library.bean.NormalList
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.law.bean.LawCollectBean
import com.zx.module_other.module.law.bean.LawCollectResultBean
import com.zx.module_other.module.law.mvp.contract.LawCollectContract
import rx.Observable

class LawCollectModel : BaseModel(), LawCollectContract.Model {
    override fun lawCollectData(map: Map<String, String>): Observable<NormalList<LawCollectBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getCollectLaw(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}