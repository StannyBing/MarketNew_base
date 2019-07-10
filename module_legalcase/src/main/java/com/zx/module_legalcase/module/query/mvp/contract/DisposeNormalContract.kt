package com.zx.module_legalcase.module.query.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_legalcase.module.query.bean.DeptBean
import com.zx.module_library.bean.UserBean
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface DisposeNormalContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onDeptListResult(deptList: List<DeptBean>)

        fun onUserListResult(userList: List<UserBean>)

        fun onDisposeResult()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun deptListData(map: Map<String, String>): Observable<List<DeptBean>>

        fun userListData(map: Map<String, String>): Observable<List<UserBean>>

        fun caseStartData(info: RequestBody): Observable<String>

        fun caseIsCaseData(info: RequestBody): Observable<String>

        fun caseApplyData(info: RequestBody): Observable<String>

        fun caseOtherData(info: RequestBody): Observable<String>

        fun caseAuditingData(info: RequestBody): Observable<String>

        fun caseExamineData(info: RequestBody): Observable<String>

        fun caseReportData(info: RequestBody): Observable<String>

        fun caseStopCaseData(info: RequestBody): Observable<String>

        fun caseFirstTrialData(info: RequestBody): Observable<String>

        fun caseSecondTrialData(info: RequestBody): Observable<String>

        fun caseNoticeData(info: RequestBody): Observable<String>

        fun caseHearingData(info: RequestBody): Observable<String>

        fun caseDecideData(info: RequestBody): Observable<String>

        fun caseDecideAuditingData(info: RequestBody): Observable<String>

        fun caseSendData(info: RequestBody): Observable<String>

        fun caseExecuteData(info: RequestBody): Observable<String>

        fun caseEndCaseData(info: RequestBody): Observable<String>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getDeptList(map: Map<String, String>)

        abstract fun getUserList(map: Map<String, String>)

        abstract fun doCaseStart(info: RequestBody)

        abstract fun doCaseIsCase(info: RequestBody)

        abstract fun doCaseApply(info: RequestBody)

        abstract fun doCaseOther(info: RequestBody)

        abstract fun doCaseAuditing(info: RequestBody)

        abstract fun doCaseExamine(info: RequestBody)

        abstract fun doCaseReport(info: RequestBody)

        abstract fun doCaseStopCase(info: RequestBody)

        abstract fun doCaseFirstTrial(info: RequestBody)

        abstract fun doCaseSecondTrial(info: RequestBody)

        abstract fun doCaseNotice(info: RequestBody)

        abstract fun doCaseHearing(info: RequestBody)

        abstract fun doCaseDecide(info: RequestBody)

        abstract fun doCaseDecideAuditing(info: RequestBody)

        abstract fun doCaseSend(info: RequestBody)

        abstract fun doCaseExecute(info: RequestBody)

        abstract fun doCaseEndCase(info: RequestBody)
    }
}
