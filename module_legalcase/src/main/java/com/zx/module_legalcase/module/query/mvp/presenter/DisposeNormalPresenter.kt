package com.zx.module_legalcase.module.query.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_legalcase.module.query.bean.DeptBean
import com.zx.module_legalcase.module.query.mvp.contract.DisposeNormalContract
import com.zx.module_library.bean.UserBean
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DisposeNormalPresenter : DisposeNormalContract.Presenter() {

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

    override fun doCaseStart(info: RequestBody) {
        mModel.caseStartData(info)
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

    override fun doCaseIsCase(info: RequestBody) {
        mModel.caseIsCaseData(info)
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

    override fun doCaseApply(info: RequestBody) {
        mModel.caseApplyData(info)
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

    override fun doCaseOther(info: RequestBody) {
        mModel.caseOtherData(info)
                .compose(RxHelper.bindToLifecycle<String>(mView))
                .subscribe(object : RxSubscriber<String>() {
                    override fun _onNext(s: String) {
                        mView.onDisposeResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun doCaseAuditing(info: RequestBody) {
        mModel.caseAuditingData(info)
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

    override fun doCaseExamine(info: RequestBody) {
        mModel.caseExamineData(info)
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

    override fun doCaseReport(info: RequestBody) {
        mModel.caseReportData(info)
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

    override fun doCaseStopCase(info: RequestBody) {
        mModel.caseStopCaseData(info)
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

    override fun doCaseFirstTrial(info: RequestBody) {
        mModel.caseFirstTrialData(info)
                .compose(RxHelper.bindToLifecycle<String>(mView))
                .subscribe(object : RxSubscriber<String>() {
                    override fun _onNext(s: String) {
                        mView.onDisposeResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun doCaseSecondTrial(info: RequestBody) {
        mModel.caseSecondTrialData(info)
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

    override fun doCaseNotice(info: RequestBody) {
        mModel.caseNoticeData(info)
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

    override fun doCaseHearing(info: RequestBody) {
        mModel.caseHearingData(info)
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

    override fun doCaseDecide(info: RequestBody) {
        mModel.caseDecideData(info)
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

    override fun doCaseDecideAuditing(info: RequestBody) {
        mModel.caseDecideAuditingData(info)
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

    override fun doCaseSend(info: RequestBody) {
        mModel.caseSendData(info)
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

    override fun doCaseExecute(info: RequestBody) {
        mModel.caseExecuteData(info)
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

    override fun doCaseEndCase(info: RequestBody) {
        mModel.caseEndCaseData(info)
                .compose(RxHelper.bindToLifecycle<String>(mView))
                .subscribe(object : RxSubscriber<String>() {
                    override fun _onNext(s: String) {
                        mView.onDisposeResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}