package com.zx.module_complain.module.info.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_complain.module.info.bean.DetailBean
import com.zx.module_complain.module.info.mvp.contract.DetailContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailPresenter : DetailContract.Presenter() {
    override fun getDetailInfo(map: Map<String, String>) {
        mModel.detailData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<DetailBean>() {
                    override fun _onNext(t: DetailBean?) {
                        if (t != null) {
                            mView.onDetailResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}