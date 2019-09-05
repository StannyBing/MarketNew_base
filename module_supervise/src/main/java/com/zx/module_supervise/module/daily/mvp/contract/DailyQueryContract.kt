package com.zx.module_supervise.module.daily.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.module.daily.bean.DailyQueryBean
import com.zx.module_supervise.module.daily.bean.EntityStationBean
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface DailyQueryContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onEntitysResult(entitys : NormalList<DailyQueryBean>)

        fun onDeptListResult(stationBeans: List<EntityStationBean>)

        fun onAreaDeptListResult(deptBeans: List<EntityStationBean>)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun entitysData(map: Map<String, String>) : Observable<NormalList<DailyQueryBean>>

        fun areaDeptListData(map: Map<String, String>): Observable<List<EntityStationBean>>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getEntitys(map: Map<String, String>)

        abstract fun getDeptList(map: Map<String, String>)

        abstract fun getAreaDeptList(map: Map<String, String>)
    }
}
