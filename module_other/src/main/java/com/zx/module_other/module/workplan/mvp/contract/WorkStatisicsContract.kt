package com.zx.module_other.module.workplan.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_other.module.workplan.bean.WorkPlanBean

interface WorkStatisicsContract {

    interface View : IView {
        fun getWorkStatisicsResult(workPlanBeans: List<Int>)
    }

    interface Model : IModel {
        fun getWorkStatisics(map: Map<String, String>): rx.Observable<List<Int>>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getWorkStatisicsList(map: Map<String, String>)
    }
}