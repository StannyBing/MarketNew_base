package com.zx.module_supervise.module.supervise.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.module.supervise.bean.SuperviseListBean
import com.zx.module_supervise.module.supervise.mvp.contract.SuperviseQueryContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class SuperviseQueryPresenter : SuperviseQueryContract.Presenter() {
    override fun getSuperviseList(map: Map<String, String>) {
        mModel.superviseListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<SuperviseListBean>>() {
                    override fun _onNext(t: NormalList<SuperviseListBean>?) {
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