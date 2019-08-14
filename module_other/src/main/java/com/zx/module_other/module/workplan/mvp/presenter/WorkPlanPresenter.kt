package com.zx.module_other.module.workplan.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.mvp.contract.WorkPlanContract

class WorkPlanPresenter : WorkPlanContract.Presenter() {
    override fun getWorkPlanList(map: Map<String, String>) {
        mModel.getWorkPlan(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<WorkPlanBean>>() {
                    override fun _onNext(t: List<WorkPlanBean>?) {
                        if (t != null) {
                            mView.getWorkPlanResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

}