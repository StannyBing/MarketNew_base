package com.zx.module_entity.module.entity.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_entity.module.entity.bean.BusinessBean
import com.zx.module_entity.module.entity.mvp.contract.DetailBusinessContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailBusinessPresenter : DetailBusinessContract.Presenter() {

    override fun getBusinessInfo(map: Map<String, String>) {
        mModel.businessData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<BusinessBean>>() {
                    override fun _onNext(businessBeans: List<BusinessBean>?) {
                        if (businessBeans != null) {
                            mView.onBusinessResult(businessBeans)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}