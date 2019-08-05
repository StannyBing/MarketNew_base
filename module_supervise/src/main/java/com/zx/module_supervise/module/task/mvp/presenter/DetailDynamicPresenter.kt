package com.zx.module_supervise.module.task.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.module.task.bean.DetailDynamicBean
import com.zx.module_supervise.module.task.mvp.contract.DetailDynamicContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailDynamicPresenter : DetailDynamicContract.Presenter() {
    override fun getDynamicList(map: Map<String, String>) {
        mModel.dynamicData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<DetailDynamicBean>>() {
                    override fun _onNext(t: NormalList<DetailDynamicBean>?) {
                        if (t?.list != null) {
                            mView.onDynamicListResult(t.list)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}