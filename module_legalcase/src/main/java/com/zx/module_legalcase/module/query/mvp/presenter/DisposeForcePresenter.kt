package com.zx.module_legalcase.module.query.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_legalcase.module.query.bean.DeptBean
import com.zx.module_legalcase.module.query.mvp.contract.DisposeForceContract
import com.zx.module_library.bean.UserBean
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DisposeForcePresenter : DisposeForceContract.Presenter() {

    override fun getDeptList(map: Map<String, String>) {
        mModel.deptListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<DeptBean>>() {
                    override fun _onNext(t: List<DeptBean>?) {
                        if (t != null) {
                            mView.onDeptListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


    override fun getUserList(map: Map<String, String>) {
        mModel.userListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<UserBean>>() {
                    override fun _onNext(t: List<UserBean>?) {
                        if (t != null) {
                            mView.onUserListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


    override fun doForceStart(info: RequestBody) {
        mModel.forceStart(info)
                .compose(RxHelper.bindToLifecycle<String>(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onDisposeResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun doForceCRBaudit(info: RequestBody) {
        mModel.forceCRBaudit(info)
                .compose(RxHelper.bindToLifecycle<String>(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onDisposeResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun doForceFZRaudit(info: RequestBody) {
        mModel.forceFZRaudit(info)
                .compose(RxHelper.bindToLifecycle<String>(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onDisposeResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun doForceJLDaudit(info: RequestBody) {
        mModel.forceJLDaudit(info)
                .compose(RxHelper.bindToLifecycle<String>(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onDisposeResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun doForceCBRexecute(info: RequestBody) {
        mModel.forceCBRexecute(info)
                .compose(RxHelper.bindToLifecycle<String>(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onDisposeResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}