package com.zx.module_other.module.workplan.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView

interface WorkPlanContract {

    interface View : IView {

    }

    interface Model : IModel {

    }

    abstract class Presenter : BasePresenter<View, Model>() {

    }
}