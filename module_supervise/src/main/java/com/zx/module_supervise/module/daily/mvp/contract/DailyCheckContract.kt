package com.zx.module_supervise.module.daily.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_supervise.module.supervise.bean.SuperviseCheckBean
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface DailyCheckContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onCheckListResult(checkList : List<SuperviseCheckBean>)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun checkListData(map: Map<String, String>) : Observable<List<SuperviseCheckBean>>

        fun dailyCheckData(map: Map<String, String>): Observable<List<SuperviseCheckBean>>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getCheckList(map: Map<String, String>)

        abstract fun getDailyCheckList(map: Map<String, String>)
    }
}
