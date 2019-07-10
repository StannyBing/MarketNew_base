package com.zx.module_legalcase.module.query.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_legalcase.module.query.mvp.contract.DisposeTransContract
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DisposeTransPresenter : DisposeTransContract.Presenter() {

    override fun caseTrans(info: RequestBody) {
        mModel.caseTransData(info)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onDisposeResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

}