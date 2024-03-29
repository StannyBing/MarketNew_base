package com.zx.module_other.module.workplan.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_other.module.workplan.bean.WorkPlanBean

interface WorkPlanContract {

    interface View : IView {
        fun getWorkPlanResult(workPlanBeans: List<WorkPlanBean>)
    }

    interface Model : IModel {
        fun getWorkPlan(map: Map<String, String>): rx.Observable<List<WorkPlanBean>>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getWorkPlanList(map: Map<String, String>)
    }
}