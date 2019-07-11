package com.zx.module_legalcase.module.query.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_legalcase.api.ApiService
import com.zx.module_legalcase.module.query.bean.DeptBean

import com.zx.module_legalcase.module.query.mvp.contract.DisposeSimpleContract
import com.zx.module_library.bean.UserBean
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DisposeSimpleModel : BaseModel(), DisposeSimpleContract.Model {


    override fun deptListData(map: Map<String, String>): Observable<List<DeptBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDeptList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun userListData(map: Map<String, String>): Observable<List<UserBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getUserList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


    override fun easyStart(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseEasyStart(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun easyAuditing(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseEasyAuditing(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }
}