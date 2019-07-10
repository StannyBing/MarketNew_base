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
interface DisposeForceContract {
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

        fun forceStart(info: RequestBody): Observable<String>

        fun forceCRBaudit(info: RequestBody): Observable<String>

        fun forceFZRaudit(info: RequestBody): Observable<String>

        fun forceJLDaudit(info: RequestBody): Observable<String>

        fun forceCBRexecute(info: RequestBody): Observable<String>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getDeptList(map: Map<String, String>)

        abstract fun getUserList(map: Map<String, String>)

        abstract fun doForceStart(info: RequestBody)

        abstract fun doForceCRBaudit(info: RequestBody)

        abstract fun doForceFZRaudit(info: RequestBody)

        abstract fun doForceJLDaudit(info: RequestBody)

        abstract fun doForceCBRexecute(info: RequestBody)
    }
}
