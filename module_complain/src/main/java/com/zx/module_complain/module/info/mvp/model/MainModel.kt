package com.zx.module_complain.module.info.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_complain.api.ApiService
import com.zx.module_complain.module.info.bean.ComplainListBean

import com.zx.module_complain.module.info.mvp.contract.MainContract
import com.zx.module_library.bean.NormalList
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MainModel : BaseModel(), MainContract.Model {
    override fun myComplainListData(map: Map<String, String>): Observable<NormalList<ComplainListBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getMyComplainList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


    override fun allComplainListData(map: Map<String, String>): Observable<NormalList<ComplainListBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getAllComplainList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}