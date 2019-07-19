package com.zx.module_other.module.workplan.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.documentmanage.bean.DocumentBean
import com.zx.module_other.module.workplan.mvp.contract.DocumentContract

class DocumentPresenter : DocumentContract.Presenter() {
    override fun getDocumentList(map: Map<String, String>) {
        mModel.getDocument(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<DocumentBean>>(mView) {
                    override fun _onNext(t: List<DocumentBean>?) {
                        if (t != null) {
                            mView.getDocumentResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}