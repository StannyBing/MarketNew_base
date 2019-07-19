package com.zx.module_entity.module.special.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_entity.module.entity.bean.EntityBean
import com.zx.module_entity.module.special.mvp.contract.SpecialListContract
import com.zx.module_library.bean.NormalList


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class SpecialListPresenter : SpecialListContract.Presenter() {
    override fun getEntityList(map: Map<String, String>) {
        mModel.entityListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<EntityBean>>() {
                    override fun _onNext(t: NormalList<EntityBean>?) {
                        if (t != null) {
                            mView.onEntityListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

}