package com.zx.marketnew_base.main.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.marketnew_base.main.bean.TaskBean
import com.zx.marketnew_base.main.mvp.contract.MyTaskContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MyTaskPresenter : MyTaskContract.Presenter() {
    override fun getTaskList(map: Map<String, String>) {
        mModel.taskListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<TaskBean>>(){
                    override fun _onNext(t: List<TaskBean>?) {
                        if (t != null) {
                            mView.onTaskListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}