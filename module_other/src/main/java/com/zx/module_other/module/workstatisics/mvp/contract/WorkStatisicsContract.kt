package com.zx.module_other.module.workplan.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workstatisics.bean.WorkOverAllBean
import com.zx.module_other.module.workstatisics.bean.WorkStatisicsBean

interface WorkStatisicsContract {

    interface View : IView {
        fun getWorkStatisicsResult(workStatisicsDatas: List<WorkStatisicsBean>)
        fun getWorkOverallResult(workOverAlls: List<WorkOverAllBean>)

    }

    interface Model : IModel {
        fun getWorkStatisics(map: Map<String, String>): rx.Observable<List<WorkStatisicsBean>>
        fun getWorkOverall(map: Map<String, String>): rx.Observable<List<WorkOverAllBean>>

    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getWorkStatisicsList(map: Map<String, String>)
        abstract fun getWorkOverallList(map: Map<String, String>)

    }
}