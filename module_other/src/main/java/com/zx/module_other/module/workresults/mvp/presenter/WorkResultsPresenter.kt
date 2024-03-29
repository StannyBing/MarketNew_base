package com.zx.module_other.module.workresults.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.workresults.bean.WorkOverAllBean
import com.zx.module_other.module.workresults.bean.WorkStatisicsBean
import com.zx.module_other.module.workresults.mvp.contract.WorkResultsContract

class WorkResultsPresenter : WorkResultsContract.Presenter() {
    override fun getWorkOverallList(map: Map<String, String>) {
        mModel.getWorkOverall(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<WorkOverAllBean>>() {
                    override fun _onNext(t: List<WorkOverAllBean>?) {
                        if (t != null) {
                            mView.getWorkOverallResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
    override fun getWorkStatisicsList(map: Map<String, String>) {
        mModel.getWorkStatisics(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<WorkStatisicsBean>>() {
                    override fun _onNext(t: List<WorkStatisicsBean>?) {
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