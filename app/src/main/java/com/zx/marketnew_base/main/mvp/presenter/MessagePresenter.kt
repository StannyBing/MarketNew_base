package com.zx.marketnew_base.main.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.marketnew_base.main.bean.MessageBean
import com.zx.marketnew_base.main.mvp.contract.MessageContract
import com.zx.module_library.bean.NormalList
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MessagePresenter : MessageContract.Presenter() {
    override fun deleteMessage(body : RequestBody) {
        mModel.messageDeleteData(body)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(){
                    override fun _onNext(t: String?) {
                        mView.onMessageDeleteResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

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

    override fun getMessageDetail(map: Map<String, String>) {
        mModel.messageDetailData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<MessageBean>(){
                    override fun _onNext(t: MessageBean?) {
                        if (t != null) {
                            mView.onMessageDetailResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}