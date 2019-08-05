package com.zx.module_other.module.law.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_library.bean.NormalList
import com.zx.module_other.module.law.bean.LawBean
import com.zx.module_other.module.law.bean.LawSearchBean
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
                .subscribe(object : RxSubscriber<NormalList<LawSearchBean>>(mView) {
                    override fun _onNext(t: NormalList<LawSearchBean>?) {
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

}