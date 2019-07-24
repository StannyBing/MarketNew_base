package com.zx.module_other.module.law.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_library.bean.NormalList
import com.zx.module_other.module.law.bean.LawCollectBean
import com.zx.module_other.module.law.bean.LawCollectResultBean
import com.zx.module_other.module.law.mvp.contract.LawCollectContract

class LawCollectPresenter : LawCollectContract.Presenter() {
    override fun getCollectList(map: Map<String, String>) {
        mModel.lawCollectData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<LawCollectBean>>(mView) {
                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                    override fun _onNext(t: NormalList<LawCollectBean>?) {
                        mView.onLawCollectResult(t)
                    }

                })
    }

}