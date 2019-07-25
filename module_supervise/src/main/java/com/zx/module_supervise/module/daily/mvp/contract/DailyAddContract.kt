package com.zx.module_supervise.module.daily.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.module.daily.bean.EntityBean
import okhttp3.RequestBody
import rx.Observable
import java.io.File

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface DailyAddContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onEntityListResult(entityList : NormalList<EntityBean>)

        fun onFileUploadResult(id: String, paths: String, type: Int)

        fun onDailySaveResult()


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun entityListData(map: Map<String, String>) : Observable<NormalList<EntityBean>>

        fun fileUploadData(body: RequestBody): Observable<List<String>>

        fun saveDailyData(body: RequestBody): Observable<String>


    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getEntityList(map: Map<String, String>)

        abstract fun uploadFile(type: Int, files: List<File>)

        abstract fun saveDailyInfo(body: RequestBody)


    }
}
