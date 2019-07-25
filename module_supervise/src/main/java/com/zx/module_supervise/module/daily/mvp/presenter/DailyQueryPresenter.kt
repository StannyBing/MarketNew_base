package com.zx.module_supervise.module.daily.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.module.daily.bean.DailyListBean
import com.zx.module_supervise.module.daily.mvp.contract.DailyQueryContract
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DailyQueryPresenter : DailyQueryContract.Presenter() {
    override fun getDailyList(map: Map<String, String>) {
        mModel.dailyListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<DailyListBean>>() {
                    override fun _onNext(t: NormalList<DailyListBean>?) {
                        if (t != null) {
                            mView.onDailyListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun updateDaily(body: RequestBody) {
        mModel.dailyUpdate(body)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onDailyUpdateResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

}