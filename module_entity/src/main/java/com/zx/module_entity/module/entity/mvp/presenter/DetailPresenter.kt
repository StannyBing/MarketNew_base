package com.zx.module_entity.module.entity.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_entity.module.entity.bean.EntityDetailBean
import com.zx.module_entity.module.entity.mvp.contract.DetailContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailPresenter : DetailContract.Presenter() {
    override fun getEntityDetail(type: Int, map: Map<String, String>) {
        val observable = if (type == 0) {
            mModel.entityDetailSpecialData(map)
        } else if (type == 1) {
            mModel.entityDetailNormalData(map)
        } else {
            mModel.entityDetailBizlicData(map)
        }
        observable.compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<EntityDetailBean>() {
                    override fun _onNext(t: EntityDetailBean?) {
                        mView.onEntityDetailResult(t)
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

}