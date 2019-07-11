package com.zx.module_other.module.workplan.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView

interface CreatePlanContract {

    interface View : IView {
        fun getCreateWorkResult(result: String)
    }

    interface Model : IModel {
        fun createWorkPlan(map: Map<String, String>): rx.Observable<String>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun createWorkPlan(map: Map<String, String>)
    }
}