package com.zx.module_supervise.module.daily.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_supervise.module.supervise.bean.SuperviseCheckBean
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface CheckListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onCheckListResult(checkDetailBeans: List<SuperviseCheckBean>, pid: String)

        fun onTempletCheckListResult(checkDetailBeans: List<SuperviseCheckBean>)

        fun onSaveTempletResult(id: String, templateName: String)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun checkListData(map: Map<String, String>): Observable<List<SuperviseCheckBean>>

        fun queryItemData(map: Map<String, String>): Observable<List<SuperviseCheckBean>>

        fun templetCheckListData(map: Map<String, String>): Observable<List<SuperviseCheckBean>>

        fun saveTempletData(info: RequestBody): Observable<String>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getCheckList(map: Map<String, String>, pid: String)

        abstract fun queryItemList(map: Map<String, String>)

        abstract fun getTempletCheckList(map: Map<String, String>)

        abstract fun saveTemplet(info: RequestBody, templateName: String)
    }
}
