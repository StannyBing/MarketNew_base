package com.zx.module_statistics.module.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_statistics.module.bean.ChartBean
import com.zx.module_statistics.module.mvp.contract.ChartContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class ChartPresenter : ChartContract.Presenter() {
    override fun getDailyByArea(map: Map<String, String>) {
        mModel.dailyByAreaData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<ChartBean>>() {
                    override fun _onNext(t: List<ChartBean>?) {
                        if (t != null) {
                            mView.onChartListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun getDailyByTrend(map: Map<String, String>) {
        mModel.dailyByTrendData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<ChartBean>>() {
                    override fun _onNext(t: List<ChartBean>?) {
                        if (t != null) {
                            mView.onChartListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun getDailyByResult(map: Map<String, String>) {
        mModel.dailyByResultData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<ChartBean>>() {
                    override fun _onNext(t: List<ChartBean>?) {
                        if (t != null) {
                            mView.onChartListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

}