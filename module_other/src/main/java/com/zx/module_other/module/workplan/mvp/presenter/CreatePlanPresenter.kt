package com.zx.module_other.module.workplan.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.mvp.contract.CreatePlanContract

class CreatePlanPresenter : CreatePlanContract.Presenter() {
    override fun createWorkPlan(map: Map<String, String>) {
        mModel.createWorkPlan(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(t: String?) {
                        if (t != null) {
                            mView.getCreateWorkResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}