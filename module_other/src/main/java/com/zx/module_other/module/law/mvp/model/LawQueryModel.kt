package com.zx.module_other.module.law.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.law.bean.LawBean
import com.zx.module_other.module.law.bean.LawDetailBean
import com.zx.module_other.module.law.bean.LawSearchResultBean

import com.zx.module_other.module.law.mvp.contract.LawQueryContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class LawQueryModel : BaseModel(), LawQueryContract.Model {
    override fun lawSearchData(map: Map<String, String>): Observable<LawSearchResultBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getSearchLaw(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun lawListData(map: Map<String, String>): Observable<List<LawBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getLawList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun lawDetailData(map: Map<String, String>): Observable<LawDetailBean> {
         return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getLawDetail(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}