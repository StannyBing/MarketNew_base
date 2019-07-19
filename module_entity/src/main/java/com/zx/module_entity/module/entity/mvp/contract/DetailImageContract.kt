package com.zx.module_entity.module.entity.mvp.contract

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
interface DetailImageContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onFileUploadResult(id: String, paths: String)

        fun onInfoModifyResult()

        fun onFileDeleteResult(position : Int)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun fileUploadData(body: RequestBody) : Observable<List<String>>

        fun modityData(body: RequestBody): Observable<Any>

        fun deleteFileData(body: RequestBody): Observable<Any>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun uploadFile(fileName : String, files : List<File>)

        abstract fun doModify(info: RequestBody)

        abstract fun deleteFile(position : Int, info: RequestBody)
    }
}
