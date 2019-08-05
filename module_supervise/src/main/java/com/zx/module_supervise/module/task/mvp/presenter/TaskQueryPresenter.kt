package com.zx.module_supervise.module.task.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.module.task.bean.TaskListBean
import com.zx.module_supervise.module.task.mvp.contract.TaskQueryContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class TaskQueryPresenter : TaskQueryContract.Presenter() {
    override fun getSuperviseList(map: Map<String, String>) {
        mModel.superviseListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<TaskListBean>>() {
                    override fun _onNext(t: NormalList<TaskListBean>?) {
                        if (t != null) {
                            mView.onSuperviseListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}