package com.zx.module_supervise.module.daily.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.module.daily.bean.DailyListBean
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface DailyListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onDailyListResult(dailyList : NormalList<DailyListBean>)

        fun onDailyUpdateResult()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun dailyListData(map: Map<String, String>) : Observable<NormalList<DailyListBean>>

        fun dailyUpdate(body: RequestBody): Observable<String>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getDailyList(map: Map<String, String>)

        abstract fun updateDaily(body: RequestBody)
    }
}
