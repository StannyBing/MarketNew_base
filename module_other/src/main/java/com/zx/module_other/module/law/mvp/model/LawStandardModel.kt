package com.zx.module_other.module.law.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.law.bean.LawCollectBean
import com.zx.module_other.module.law.bean.LawCollectResultBean
import com.zx.module_other.module.law.bean.LawStandardQueryResultBean
import com.zx.module_other.module.law.mvp.contract.LawCollectContract
import com.zx.module_other.module.law.mvp.contract.LawStandardQueryContract
import rx.Observable

class LawStandardModel : BaseModel(), LawStandardQueryContract.Model {
    override fun lawStandardData(map: Map<String, String>): Observable<LawStandardQueryResultBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .selectDiscretionStandard(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }
}