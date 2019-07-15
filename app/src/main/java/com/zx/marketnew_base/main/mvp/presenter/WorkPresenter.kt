package com.zx.marketnew_base.main.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.marketnew_base.main.bean.OfficeBean
import com.zx.marketnew_base.main.mvp.contract.WorkContract
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class WorkPresenter : WorkContract.Presenter() {
    override fun sendXappOpt(body: RequestBody) {
        mModel.xappOptData(body)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>() {
                    override fun _onNext(t: String?) {

                    }

                    override fun _onError(code: String?, message: String?) {

                    }

                })
    }

    override fun getOfficeInfo() {
        mModel.officeData()
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<OfficeBean>() {
                    override fun _onNext(t: OfficeBean?) {
                        mView.onOfficeResult(t)
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.onOfficeResult(null)
                    }

                })
    }


}