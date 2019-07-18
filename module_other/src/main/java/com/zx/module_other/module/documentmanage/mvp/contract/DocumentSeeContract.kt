package com.zx.module_other.module.workplan.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workstatisics.bean.DocumentBean
import com.zx.module_other.module.workstatisics.bean.WorkStatisicsBean

interface DocumentSeeContract {

    interface View : IView {
        fun getDocumentWebSeeResult(weburl: String)
    }

    interface Model : IModel {
        fun getDocumentWeb(map: Map<String, String>): rx.Observable<String>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getDocumentWeb(map: Map<String, String>)
    }
}