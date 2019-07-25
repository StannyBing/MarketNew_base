package com.zx.module_supervise.module.daily.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_supervise.module.daily.mvp.contract.DailyCheckContract
import com.zx.module_supervise.module.supervise.bean.SuperviseCheckBean


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DailyCheckPresenter : DailyCheckContract.Presenter() {
    override fun getDailyCheckList(map: Map<String, String>) {
        mModel.dailyCheckData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<SuperviseCheckBean>>(){
                    override fun _onNext(t: List<SuperviseCheckBean>?) {
                        if (t != null) {
                            mView.onCheckListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun getCheckList(map: Map<String, String>) {
        mModel.checkListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<SuperviseCheckBean>>(){
                    override fun _onNext(t: List<SuperviseCheckBean>?) {
                        if (t != null) {
                            mView.onCheckListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}