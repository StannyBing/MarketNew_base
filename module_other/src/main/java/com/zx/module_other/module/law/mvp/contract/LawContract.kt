package com.zx.module_other.module.law.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.frame.zxmvp.http.download.DownInfo
import com.zx.module_library.bean.NormalList
import com.zx.module_other.module.law.bean.LawBean
import okhttp3.RequestBody
import java.util.*

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface LawContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        //fun onLawListResult(complainList : List<LawBean>)
}

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        //fun lawListData(map: Map<String, String>) : rx.Observable<List<LawBean>>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        //abstract fun getLawList(map: Map<String, String>)
    }
}
