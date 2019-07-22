package com.zx.module_supervise.module.supervise.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_supervise.module.supervise.mvp.contract.SuperviseDisposeContract
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class SuperviseDisposePresenter : SuperviseDisposeContract.Presenter() {
    override fun submitTask(body: RequestBody) {
        mModel.submitTaskData(body)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(mView){
                    override fun _onNext(t: String?) {
                        mView.onSubmitResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}