package com.zx.module_complain.module.info.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_complain.module.info.bean.DeptBean
import com.zx.module_library.bean.UserBean
import okhttp3.RequestBody
import rx.Observable
import java.io.File

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface DisposeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onDeptListResult(deptList: List<DeptBean>)

        fun onUserListResult(userList: List<UserBean>)

        fun onDisposeResult()

        fun onFileUploadResult(id: String, paths: String)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun deptListData(map: Map<String, String>): Observable<List<DeptBean>>

        fun userListData(map: Map<String, String>): Observable<List<UserBean>>

        fun disposeData(info: RequestBody): Observable<String>

        fun fileUploadData(info: RequestBody) : Observable<List<String>>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getDeptList(map: Map<String, String>)

        abstract fun getUserList(map: Map<String, String>)

        abstract fun submitDispose(info: RequestBody)

        abstract fun uploadFile(fileName : String, files : List<File>)
    }
}
