package com.zx.module_supervise.module.task.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_supervise.module.task.bean.TaskCheckBean
import com.zx.module_supervise.module.task.mvp.contract.DetailCheckInfoContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailCheckInfoPresenter : DetailCheckInfoContract.Presenter() {
    override fun getCheckList(map: Map<String, String>) {
        mModel.checkListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<TaskCheckBean>>(){
                    override fun _onNext(t: List<TaskCheckBean>?) {
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