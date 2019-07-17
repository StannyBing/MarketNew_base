package com.zx.module_entity.module.entity.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_entity.module.entity.bean.DicTypeBean
import com.zx.module_entity.module.entity.mvp.contract.DetailInfoContract
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailInfoPresenter : DetailInfoContract.Presenter() {
    override fun modifyInfo(body: RequestBody) {
        mModel.infoModifyData(body)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<Any>(mView) {
                    override fun _onNext(t: Any?) {
                        mView.onInfoModifyResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

    override fun getDicTypeList(map: Map<String, String>) {
        mModel.dicListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<DicTypeBean>>() {
                    override fun _onNext(t: List<DicTypeBean>?) {
                        if (t != null) {
                            mView.onDicListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

}