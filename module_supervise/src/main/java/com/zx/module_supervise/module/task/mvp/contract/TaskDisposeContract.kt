package com.zx.module_supervise.module.task.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import okhttp3.RequestBody
import rx.Observable
import java.io.File

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface TaskDisposeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {

        fun onSubmitResult()

        fun onFileUploadResult(id: String, paths: String, type: Int)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun submitAuditData(body: RequestBody): Observable<String>

        fun submitDisposeData(body: RequestBody) : Observable<String>

        fun submitBackData(body: RequestBody) : Observable<String>

        fun fileUploadData(body: RequestBody): Observable<List<String>>

    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {

        abstract fun submitAudit(body: RequestBody)

        abstract fun submitDispose(body: RequestBody)

        abstract fun submitBack(body: RequestBody)

        abstract fun uploadFile(type: Int, vararg files: File)
    }
}
