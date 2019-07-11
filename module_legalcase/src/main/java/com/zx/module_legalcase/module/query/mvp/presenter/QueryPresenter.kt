package com.zx.module_legalcase.module.query.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_legalcase.module.query.mvp.bean.LegalcaseListBean
import com.zx.module_legalcase.module.query.mvp.contract.QueryContract
import com.zx.module_library.bean.NormalList


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class QueryPresenter : QueryContract.Presenter() {
    override fun getMyCaseTodoList(map: Map<String, String>) {
        mModel.myCaseTodoListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<LegalcaseListBean>>(){
                    override fun _onNext(t: NormalList<LegalcaseListBean>?) {
                        if (t != null) {
                            mView.onCaseListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

    override fun getMyCaseAlreadyList(map: Map<String, String>) {
        mModel.myCaseAlreadyListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<LegalcaseListBean>>(){
                    override fun _onNext(t: NormalList<LegalcaseListBean>?) {
                        if (t != null) {
                            mView.onCaseListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

    override fun getAllCaseList(map: Map<String, String>) {
        mModel.allCaseListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<LegalcaseListBean>>(){
                    override fun _onNext(t: NormalList<LegalcaseListBean>?) {
                        if (t != null) {
                            mView.onCaseListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}