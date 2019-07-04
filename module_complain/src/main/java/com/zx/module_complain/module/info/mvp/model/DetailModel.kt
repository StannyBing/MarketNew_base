package com.zx.module_complain.module.info.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_complain.api.ApiService
import com.zx.module_complain.module.info.bean.DetailBean

import com.zx.module_complain.module.info.mvp.contract.DetailContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailModel : BaseModel(), DetailContract.Model {
    override fun detailData(map: Map<String, String>): Observable<DetailBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDetailInfo(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}