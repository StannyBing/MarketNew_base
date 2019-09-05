package com.zx.module_statistics.module.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_statistics.api.ApiService
import com.zx.module_statistics.module.bean.ChartBean

import com.zx.module_statistics.module.mvp.contract.ChartContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class ChartModel : BaseModel(), ChartContract.Model {
    override fun dailyByAreaData(map: Map<String, String>): Observable<List<ChartBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDailyByArea(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun dailyByTrendData(map: Map<String, String>): Observable<List<ChartBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDailyByTrend(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun dailyByResultData(map: Map<String, String>): Observable<List<ChartBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDailyByResult(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }
}