package com.zx.module_other.module.workplan.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.documentmanage.bean.DocumentBean
import com.zx.module_other.module.documentmanage.bean.TemplateFieldBean
import com.zx.module_other.module.workplan.mvp.contract.DocumentContract
import com.zx.module_other.module.workplan.mvp.contract.DocumentFillContract

class DocumentFillPresenter : DocumentFillContract.Presenter() {
    override fun getDocumentPrintHtml(map: Map<String, String>) {
        mModel.getDocumentPrint(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object :RxSubscriber<String>(mView){
                    override fun _onNext(t: String?) {
                        mView.getDocumentPrintResult(t!!)
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

    override fun getDocumentFieldList(map: Map<String, String>) {
        mModel.getDocumentField(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<TemplateFieldBean>>(mView) {
                    override fun _onNext(t: List<TemplateFieldBean>?) {
                        if (t != null) {
                            mView.getDocumentFieldResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}