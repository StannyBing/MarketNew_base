package com.zx.module_other.module.workplan.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workstatisics.bean.DocumentBean
import com.zx.module_other.module.workstatisics.bean.WorkStatisicsBean

interface DocumentContract {

    interface View : IView {
        fun getDocumentResult(workStatisicsDatas: List<DocumentBean>)
    }

    interface Model : IModel {
        fun getDocument(map: Map<String, String>): rx.Observable<List<DocumentBean>>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getDocumentList(map: Map<String, String>)
    }
}