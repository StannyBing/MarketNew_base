package com.zx.module_other.module.workplan.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.mvp.contract.CreatePlanContract
import com.zx.module_other.module.workplan.mvp.contract.WorkStatisicsContract

class WorkStatisicsPresenter : WorkStatisicsContract.Presenter() {
    override fun getWorkStatisicsList(map: Map<String, String>) {
        mModel.getWorkStatisics(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<Int>>(mView) {
                    override fun _onNext(t: List<Int>?) {
                        if (t != null) {
                            mView.getWorkStatisicsResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}