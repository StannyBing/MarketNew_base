package com.zx.marketnew_base.system.mvp.contract

import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.IModel
import com.frame.zxmvp.base.IView
import com.zx.marketnew_base.system.bean.VerifiCodeBean
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
interface ForgetPwdContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onSmsSendResult(codeBean: VerifiCodeBean)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun smsData(map: Map<String, String>): Observable<VerifiCodeBean>
    }

    //方法
    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun sendSms(map: Map<String, String>)
    }
}
