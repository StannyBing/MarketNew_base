package com.zx.module_other.module.law.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.module_other.module.law.bean.LawCollectBean
import com.zx.module_other.module.law.bean.LawCollectResultBean

/**
 * Create By lyc On 2019/07/06
 * 功能：
 */
interface LawCollectContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onLawCollectResult(lawCollectResultBean: LawCollectResultBean?)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun lawCollectData(map: Map<String, String>): rx.Observable<LawCollectResultBean>
    }

    //方法
    abstract class Presenter : BasePresenter<LawCollectContract.View, Model>() {
        abstract fun getCollectList(map: Map<String, String>)
    }
}