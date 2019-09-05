package com.zx.module_statistics.module.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_statistics.module.bean.ChartBean
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface ChartContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onChartListResult(chartList : List<ChartBean>)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun dailyByAreaData(map: Map<String, String>) : Observable<List<ChartBean>>

        fun dailyByTrendData(map: Map<String, String>) : Observable<List<ChartBean>>

        fun dailyByResultData(map: Map<String, String>) : Observable<List<ChartBean>>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getDailyByArea(map: Map<String, String>)

        abstract fun getDailyByTrend(map: Map<String, String>)

        abstract fun getDailyByResult(map: Map<String, String>)
    }
}