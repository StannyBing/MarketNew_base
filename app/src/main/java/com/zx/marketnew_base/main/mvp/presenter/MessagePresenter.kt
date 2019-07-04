package com.zx.marketnew_base.main.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.marketnew_base.main.bean.MessageBean
import com.zx.marketnew_base.main.mvp.contract.MessageContract
import com.zx.module_library.bean.NormalList


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MessagePresenter : MessageContract.Presenter() {
    override fun getMessageList(map: Map<String, String>) {
        mModel.messageListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<MessageBean>>() {
                    override fun _onNext(t: NormalList<MessageBean>?) {
                        if (t != null) {
                            mView.onMessageListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

}