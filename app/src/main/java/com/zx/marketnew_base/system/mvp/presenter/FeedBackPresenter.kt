package com.zx.marketnew_base.system.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.marketnew_base.system.mvp.contract.FeedBackContract
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class FeedBackPresenter : FeedBackContract.Presenter() {
    override fun addFeedBack(body: RequestBody) {
        mModel.feedBackData(body)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(){
                    override fun _onNext(t: String?) {
                        mView.onFeedBackResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}