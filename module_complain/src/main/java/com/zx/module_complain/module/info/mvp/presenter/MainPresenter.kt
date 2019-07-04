package com.zx.module_complain.module.info.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_complain.module.info.bean.ComplainListBean
import com.zx.module_complain.module.info.mvp.contract.MainContract
import com.zx.module_library.bean.NormalList


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MainPresenter : MainContract.Presenter() {
    override fun getMyComplainList(map: Map<String, String>) {
        mModel.myComplainListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<ComplainListBean>>() {
                    override fun _onNext(t: NormalList<ComplainListBean>?) {
                        if (t != null) {
                            mView.onComplainListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

    override fun getAllComplainList(map: Map<String, String>) {
        mModel.allComplainListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<ComplainListBean>>() {
                    override fun _onNext(t: NormalList<ComplainListBean>?) {
                        if (t != null) {
                            mView.onComplainListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}