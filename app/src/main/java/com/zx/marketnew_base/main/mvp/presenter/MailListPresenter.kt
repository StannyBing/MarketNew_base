package com.zx.marketnew_base.main.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.marketnew_base.main.bean.MailListBean
import com.zx.marketnew_base.main.mvp.contract.MailListContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MailListPresenter : MailListContract.Presenter() {
    override fun getMailList(map: Map<String, String>) {
        mModel.mailListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<MailListBean>>() {
                    override fun _onNext(t: List<MailListBean>?) {
                        if (t != null) {
                            mView.onMailListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}