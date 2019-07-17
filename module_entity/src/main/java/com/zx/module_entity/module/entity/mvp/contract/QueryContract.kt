package com.zx.module_entity.module.entity.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_entity.module.entity.bean.DicTypeBean
import com.zx.module_entity.module.entity.bean.EntityLevelBean
import com.zx.module_entity.module.entity.bean.EntityStationBean
import com.zx.module_entity.module.entity.func.adapter.EntityBean
import com.zx.module_library.bean.NormalList
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface QueryContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onEntityListResult(entityList : NormalList<EntityBean>)

        fun onTagListResult(dicTypeList : List<DicTypeBean>)

        fun onEntityLevelResult(entityLevelBeans: List<EntityLevelBean>)

        fun onDeptListResult(stationBeans: List<EntityStationBean>)

        fun onAreaDeptListResult(deptBeans: List<EntityStationBean>)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun entityListData(map: Map<String, String>) : Observable<NormalList<EntityBean>>

        fun tagListData(map: Map<String, String>) : Observable<List<DicTypeBean>>

        fun entityLevelData(): Observable<List<EntityLevelBean>>

        fun deptListData(map: Map<String, String>): Observable<List<EntityStationBean>>

        fun areaDeptListData(map: Map<String, String>): Observable<List<EntityStationBean>>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getEntityList(map: Map<String, String>)

        abstract fun getFilterInfo()

        abstract fun getAreaDeptList(map: Map<String, String>)
    }
}
