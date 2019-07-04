package com.zx.marketnew_base.system.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.marketnew_base.system.bean.VerifiCodeBean
import com.zx.marketnew_base.system.mvp.contract.ForgetPwdContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class ForgetPwdPresenter : ForgetPwdContract.Presenter() {
    override fun sendSms(map: Map<String, String>) {
        mModel.smsData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<VerifiCodeBean>(mView) {
                    override fun _onNext(bean: VerifiCodeBean?) {
                        if (bean != null) {
                            mView.onSmsSendResult(bean)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}