package com.zx.module_other.module.workplan.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.google.gson.JsonArray
import com.zx.module_other.module.workplan.mvp.contract.DocumentSeeContract

class DocumentSeePresenter : DocumentSeeContract.Presenter() {
    override fun getDocumentWeb(map: Map<String, String>) {
        mModel.getDocumentWeb(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<okhttp3.ResponseBody>(mView) {
                    override fun _onNext(t: okhttp3.ResponseBody?) {
                        if (t != null) {
                         mView.getDocumentWebSeeResult(t.string())
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}