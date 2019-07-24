package com.zx.module_other.module.law.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_other.module.law.bean.LawCollectResultBean
import com.zx.module_other.module.law.bean.LawDetailBean
import okhttp3.RequestBody

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface LawDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onLawDetailResult(lawDetail: LawDetailBean)
        fun onLawAddCollect(collect:String)
        fun onLawDeleteCollect(data:Int)
        fun onLawCollectResult(lawCollectResultBean: LawCollectResultBean?)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun lawDetailData(map: Map<String, String>): rx.Observable<LawDetailBean>
        fun lawAllCollect(info: RequestBody): rx.Observable<String>
        fun lawDeleteCollect(info: RequestBody):rx.Observable<Int>
        fun lawCollectData(map: Map<String, String>): rx.Observable<LawCollectResultBean>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getLawDetail(map: Map<String, String>)
        abstract fun AddWeixinCollectLaw(info: RequestBody)
        abstract fun DeleteWeixinCollectLaw(info: RequestBody)
        abstract fun getCollectList(map: Map<String, String>)
    }
}
