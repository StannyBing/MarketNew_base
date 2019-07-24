package com.zx.module_other.module.workplan.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.workplan.mvp.contract.CreatePlanContract
import okhttp3.RequestBody

class CreatePlanPresenter : CreatePlanContract.Presenter() {
    override fun createWorkPlan(info: RequestBody) {
        mModel.createWorkPlan(info)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(t: String?) {
                            mView.getCreateWorkResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}