package com.zx.module_legalcase.module.query.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_legalcase.api.ApiService
import com.zx.module_legalcase.module.query.mvp.bean.LegalcaseListBean

import com.zx.module_legalcase.module.query.mvp.contract.QueryContract
import com.zx.module_library.bean.NormalList
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class QueryModel : BaseModel(), QueryContract.Model {
    override fun myCaseTodoListData(map: Map<String, String>): Observable<NormalList<LegalcaseListBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getMyCaseToDoList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun myCaseAlreadyListData(map: Map<String, String>): Observable<NormalList<LegalcaseListBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getMyCaseAlreadyList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun allCaseListData(map: Map<String, String>): Observable<NormalList<LegalcaseListBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getAllCaseList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}