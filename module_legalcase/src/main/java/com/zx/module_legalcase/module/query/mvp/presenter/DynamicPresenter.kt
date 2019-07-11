package com.zx.module_legalcase.module.query.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_legalcase.module.query.bean.DynamicBean
import com.zx.module_legalcase.module.query.mvp.contract.DynamicContract
import com.zx.module_library.bean.NormalList


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DynamicPresenter : DynamicContract.Presenter() {
    override fun getDynamic(map: Map<String, String>) {
        mModel.dynamicData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<DynamicBean>>() {
                    override fun _onNext(t: NormalList<DynamicBean>?) {
                        if (t != null) {
                            mView.onDynamicResult(t.list)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}