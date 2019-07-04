package com.zx.marketnew_base.system.mvp.model;

import com.frame.zxmvp.base.BaseModel;
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.marketnew_base.api.ApiService

import com.zx.marketnew_base.system.mvp.contract.SplashContract;
import com.zx.module_library.bean.UserBean
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class SplashModel : BaseModel(), SplashContract.Model {
    override fun doLogin(info: RequestBody): Observable<UserBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .doLogin(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}