package com.zx.module_entity.module.special.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_entity.module.entity.bean.DicTypeBean
import com.zx.module_entity.module.special.bean.DeptBean
import okhttp3.RequestBody
import rx.Observable
import java.io.File

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface SpecialAddContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onDicListResult(dicTypeBeans: List<DicTypeBean>)

        fun onDeptListResult(deptBeans: List<DeptBean>)

        fun onAreaDeptListResult(deptBeans: List<DeptBean>)

        fun onSubmitResult()

        fun onFileUploadResult(id: String, paths: String)

        fun onIdentifyListResult(dicTypeBeans: List<DicTypeBean>)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun dicListData(map: Map<String, String>): Observable<List<DicTypeBean>>

        fun deptListData(map: Map<String, String>): Observable<List<DeptBean>>

        fun areaDeptListData(map: Map<String, String>): Observable<List<DeptBean>>

        fun submitData(body: RequestBody): Observable<String>

        fun fileUploadData(body: RequestBody): Observable<List<String>>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getNormalInfo()

        abstract fun getAreaDeptList(map: Map<String, String>)

        abstract fun doSubmit(body: RequestBody)

        abstract fun uploadFile(files: List<File>)

    }
}
