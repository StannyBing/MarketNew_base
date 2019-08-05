package com.zx.module_other.module.workplan.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IView
import com.frame.zxmvp.base.IModel
import com.zx.module_other.module.workplan.bean.WorkPlanBean

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface WorkListContract {
    interface View : IView {
        fun getWorkPlanResult(workPlanBeans: List<WorkPlanBean>)
        fun getWorkDatesResult(dates: ArrayList<String>)
    }

    interface Model : IModel {
        fun getWorkPlan(map: Map<String, String>): rx.Observable<List<WorkPlanBean>>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getWorkPlanList(map: Map<String, String>)
        abstract fun getWorkDatesList()
    }
}
