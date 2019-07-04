package com.zx.module_other.module.law.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_other.module.law.bean.LawBean
import com.zx.module_other.module.law.bean.LawDetailBean
import com.zx.module_other.module.law.bean.LawSearchResultBean
import com.zx.module_other.module.law.mvp.contract.LawQueryContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class LawQueryPresenter : LawQueryContract.Presenter() {
    override fun getSearchLaw(map: Map<String, String>) {
        mModel.lawSearchData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<LawSearchResultBean>(mView) {
                    override fun _onNext(t: LawSearchResultBean?) {
                        if (t != null) {
                           mView.onSearchLawResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun getLawList(map: Map<String, String>) {
        mModel.lawListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<LawBean>>(mView) {
                    override fun _onNext(t: List<LawBean>?) {
                        if (t != null) {
                            mView.onLawListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun getLawDetail(map: Map<String, String>) {
        mModel.lawDetailData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<LawDetailBean>(mView) {
                    override fun _onNext(t: LawDetailBean?) {
                        if (t != null) {
                            mView.onLawDetailResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}