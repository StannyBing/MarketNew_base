package com.zx.module_other.module.law.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.law.bean.LawDetailBean
import com.zx.module_other.module.law.mvp.contract.LawDetailContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class LawDetailPresenter : LawDetailContract.Presenter() {

    override fun getLawDetail(map: Map<String, String>) {
        mModel.lawDetailData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<LawDetailBean>(mView) {
                    override fun _onNext(t: LawDetailBean?) {
                        if (t != null) {
                            mView.onLawDetailResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

}