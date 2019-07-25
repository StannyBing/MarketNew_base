package com.zx.module_supervise.module.daily.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_supervise.module.daily.bean.DailyDetailBean
import com.zx.module_supervise.module.daily.mvp.contract.DailyBaseContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DailyBasePresenter : DailyBaseContract.Presenter() {
    override fun getDailyDetail(map: Map<String, String>) {
        mModel.dailyDetailData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<DailyDetailBean>() {
                    override fun _onNext(t: DailyDetailBean?) {
                        if (t != null) {
                            mView.onDailyDetailResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

}