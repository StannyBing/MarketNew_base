package com.zx.module_other.module.workplan.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_other.module.documentmanage.bean.DocumentBean
import com.zx.module_other.module.documentmanage.bean.TemplateFieldBean

interface DocumentFillContract {

    interface View : IView {
        fun getDocumentFieldResult(fields: List<TemplateFieldBean>)
        fun getDocumentPrintResult(print:String)
    }

    interface Model : IModel {
        fun getDocumentField(map: Map<String, String>): rx.Observable<List<TemplateFieldBean>>
        fun getDocumentPrint(map: Map<String, String>): rx.Observable<String>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getDocumentFieldList(map: Map<String, String>)
        abstract fun getDocumentPrintHtml(map: Map<String, String>)
    }
}