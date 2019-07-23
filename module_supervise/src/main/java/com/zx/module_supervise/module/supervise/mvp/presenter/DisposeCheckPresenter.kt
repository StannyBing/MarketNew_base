package com.zx.module_supervise.module.supervise.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_supervise.module.supervise.bean.SuperviseCheckBean
import com.zx.module_supervise.module.supervise.mvp.contract.DisposeCheckContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DisposeCheckPresenter : DisposeCheckContract.Presenter() {
    override fun getCheckList(map: Map<String, String>) {
        mModel.checkListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<SuperviseCheckBean>>() {
                    override fun _onNext(t: List<SuperviseCheckBean>?) {
                        if (t != null) {
                            mView.onCheckListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

}