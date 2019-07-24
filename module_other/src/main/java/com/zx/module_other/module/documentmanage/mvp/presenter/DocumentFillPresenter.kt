package com.zx.module_other.module.workplan.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.documentmanage.bean.DocumentBean
import com.zx.module_other.module.documentmanage.bean.TemplateFieldBean
import com.zx.module_other.module.workplan.mvp.contract.DocumentContract
import com.zx.module_other.module.workplan.mvp.contract.DocumentFillContract
import okhttp3.RequestBody
import okhttp3.ResponseBody

class DocumentFillPresenter : DocumentFillContract.Presenter() {
    override fun getDocumentPrintHtml(info : RequestBody) {
        mModel.getDocumentPrint(info)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object :RxSubscriber<ResponseBody>(mView){
                    override fun _onNext(t: ResponseBody?) {
                        mView.getDocumentPrintResult(t!!.string())
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