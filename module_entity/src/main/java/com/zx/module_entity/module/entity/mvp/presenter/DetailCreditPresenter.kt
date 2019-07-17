package com.zx.module_entity.module.entity.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_entity.module.entity.bean.CreditBean
import com.zx.module_entity.module.entity.mvp.contract.DetailCreditContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailCreditPresenter : DetailCreditContract.Presenter() {
    override fun getCreditInfo(map: Map<String, String>) {
        mModel.creditListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<CreditBean>>(){
                    override fun _onNext(t: List<CreditBean>?) {
                        if (t != null) {
                            mView.onCreditListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}