package com.zx.module_other.module.law.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.law.bean.LawCollectBean
import com.zx.module_other.module.law.bean.LawCollectResultBean
import com.zx.module_other.module.law.bean.LawStandardQueryResultBean
import com.zx.module_other.module.law.mvp.contract.LawCollectContract
import com.zx.module_other.module.law.mvp.contract.LawStandardQueryContract

class LawStandardPresenter : LawStandardQueryContract.Presenter() {
    override fun getStandardData(map: Map<String, String>) {
        mModel.lawStandardData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<LawStandardQueryResultBean>(mView) {
                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                    override fun _onNext(t: LawStandardQueryResultBean?) {
                        mView.onLawStandardResult(t!!)
                    }

                })
    }
}