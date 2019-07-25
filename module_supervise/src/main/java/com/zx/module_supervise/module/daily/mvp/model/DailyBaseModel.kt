package com.zx.module_supervise.module.daily.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_supervise.api.ApiService
import com.zx.module_supervise.module.daily.bean.DailyDetailBean

import com.zx.module_supervise.module.daily.mvp.contract.DailyBaseContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DailyBaseModel : BaseModel(), DailyBaseContract.Model {
    override fun dailyDetailData(map: Map<String, String>): Observable<DailyDetailBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getSuperviseDailyDetail(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}