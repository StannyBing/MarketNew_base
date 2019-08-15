package com.zx.module_map.module.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_map.module.bean.DeptBean
import com.zx.module_map.module.bean.DicTypeBean
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface MapBtnContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onStationListResult(stationList : List<DicTypeBean>)

        fun onGridListResult(gridList : List<DeptBean>)

        fun onChangePosResult()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun stationListData(map: Map<String, String>): Observable<List<DicTypeBean>>

        fun gridListData(map: Map<String, String>): Observable<List<DeptBean>>

        fun changePosData(body: RequestBody) : Observable<String>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getStationList(map: Map<String, String>)

        abstract fun getGridList(map: Map<String, String>)

        abstract fun changePos(body: RequestBody)
    }
}
