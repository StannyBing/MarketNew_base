package com.zx.module_entity.module.entity.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_entity.api.ApiService
import com.zx.module_entity.module.entity.bean.DicTypeBean

import com.zx.module_entity.module.entity.mvp.contract.DetailInfoContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailInfoModel : BaseModel(), DetailInfoContract.Model {
    override fun infoModifyData(body: RequestBody): Observable<Any> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .modifyInfo(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun dicListData(map: Map<String, String>): Observable<List<DicTypeBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDicTypeList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}