package com.zx.module_other.module.workplan.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import okhttp3.RequestBody
import retrofit2.http.Body

interface CreatePlanContract {

    interface View : IView {
        fun getCreateWorkResult()
    }

    interface Model : IModel {
        fun createWorkPlan(info: RequestBody): rx.Observable<String>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun createWorkPlan(info: RequestBody)
    }
}