package com.zx.marketnew_base.main.mvp.model;

import com.frame.zxmvp.base.BaseModel;
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.marketnew_base.api.ApiService
import com.zx.marketnew_base.main.bean.VersionBean

import com.zx.marketnew_base.main.mvp.contract.MainContract;
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MainModel : BaseModel(), MainContract.Model {

    override fun versionData(): Observable<VersionBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getVersion()
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


    override fun countUnreadData(): Observable<Int> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .countUnread()
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}