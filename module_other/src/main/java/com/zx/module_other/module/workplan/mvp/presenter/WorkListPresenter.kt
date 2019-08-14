package com.zx.module_other.module.workplan.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.func.util.DateUtil
import com.zx.module_other.module.workplan.mvp.contract.WorkListContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class WorkListPresenter : WorkListContract.Presenter() {
    override fun getWorkDatesList() {
        var dates = arrayListOf<String>()
        val date = DateUtil.stampToTime(System.currentTimeMillis())
        val year = date.substring(0, 4).toInt()
        val month = date.substring(5, 7).toInt()
        for (j in 0..10) {
            var i = 10 - j
            if (month - i <= 0) {
                if (month - i + 12 < 10) {
                    dates.add((year - 1).toString() + "-0" + (month - i + 12))
                } else {
                    dates.add((year - 1).toString() + "-" + (month - i + 12))
                }
            } else if (month - i < 10) {
                dates.add(year.toString() + "-0" + (month - i))
            } else {
                dates.add(year.toString() + "-" + (month - i))
            }
        }

        for (i in 1..10) {
            if (month + i > 12) {
                if (month + i - 12 < 10) {
                    dates.add((year + 1).toString() + "-0" + (month + i - 12))
                } else {
                    dates.add((year + 1).toString() + "-" + (month + i - 12))
                }
            } else if (month + i < 10) {
                dates.add(year.toString() + "-0" + (month + i))
            } else {
                dates.add(year.toString() + "-" + (month + i))
            }
        }
        mView.getWorkDatesResult(dates)
    }

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