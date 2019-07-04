package com.zx.marketnew_base.system.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.marketnew_base.system.mvp.contract.LoginContract
import com.zx.module_library.bean.UserBean
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class LoginPresenter : LoginContract.Presenter() {
    override fun doLogin(info: RequestBody) {
        mModel.doLogin(info)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<UserBean>(mView) {
                    override fun _onNext(t: UserBean?) {
                        if (t != null) {
                            mView.onLoginResult(t)
                        }
                    }

                    override fun _onError(code : String?, message: String?) {
                        mView.showToast(message)
                        mView.onLoginError()
                    }
                })
    }


}