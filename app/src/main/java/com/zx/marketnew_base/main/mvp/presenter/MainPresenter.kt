package com.zx.marketnew_base.main.mvp.presenter;

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.marketnew_base.main.bean.VersionBean
import com.zx.marketnew_base.main.mvp.contract.MainContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MainPresenter : MainContract.Presenter() {

    override fun getVerson() {
        mModel.versionData()
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<VersionBean>() {
                    override fun _onNext(t: VersionBean?) {
                        if (t != null) {
                            mView.onVersionResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

    override fun getUnread() {
        mModel.countUnreadData()
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<Int>() {
                    override fun _onNext(t: Int?) {
                        mView.onCountUnreadResult(t ?: 0)
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

}