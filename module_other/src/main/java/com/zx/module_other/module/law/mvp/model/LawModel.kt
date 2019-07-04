package com.zx.module_other.module.law.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_library.bean.NormalList
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.law.bean.LawBean

import com.zx.module_other.module.law.mvp.contract.LawContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class LawModel : BaseModel(), LawContract.Model {
    /*override fun lawListData(map: Map<String, String>): Observable<List<LawBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getLawList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }*/
}