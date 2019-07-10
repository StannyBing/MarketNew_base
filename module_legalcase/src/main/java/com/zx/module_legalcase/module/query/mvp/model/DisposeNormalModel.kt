package com.zx.module_legalcase.module.query.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_legalcase.api.ApiService
import com.zx.module_legalcase.module.query.bean.DeptBean
import com.zx.module_legalcase.module.query.mvp.contract.DisposeNormalContract
import com.zx.module_library.bean.UserBean
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DisposeNormalModel : BaseModel(), DisposeNormalContract.Model {
    override fun deptListData(map: Map<String, String>): Observable<List<DeptBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDeptList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun userListData(map: Map<String, String>): Observable<List<UserBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getUserList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun caseStartData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseStart(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseIsCaseData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseIsCase(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseApplyData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseApply(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseOtherData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseOther(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseAuditingData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseAuditing(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseExamineData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseExamine(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseReportData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseReport(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseStopCaseData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseStopCase(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseFirstTrialData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseFirstTrial(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseSecondTrialData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseSecondTrial(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseNoticeData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseNotice(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseHearingData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseHearing(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseDecideData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseDecide(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseDecideAuditingData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseDecideAuditing(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseSendData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseSend(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseExecuteData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseExecute(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

    override fun caseEndCaseData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .caseEndCase(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main<String>())
    }

}