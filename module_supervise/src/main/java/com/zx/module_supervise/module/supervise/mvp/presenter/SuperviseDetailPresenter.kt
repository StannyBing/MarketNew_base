package com.zx.module_supervise.module.supervise.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_supervise.module.supervise.bean.EntityInfoBean
import com.zx.module_supervise.module.supervise.mvp.contract.SuperviseDetailContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class SuperviseDetailPresenter : SuperviseDetailContract.Presenter() {
    override fun getDetailInfo(id: String, taskId: String) {
        mModel.taskInfoData(hashMapOf("fTaskId" to taskId))
                .flatMap {
                    mView.onTaskInfoResult(it)
                    mModel.entityInfoData(hashMapOf("fId" to id))
                }
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<EntityInfoBean>(){
                    override fun _onNext(t: EntityInfoBean?) {
                        if (t != null) {
                            mView.onEntityInfoResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}