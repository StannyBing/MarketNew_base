package com.zx.module_supervise.module.daily.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.api.ApiService
import com.zx.module_supervise.module.daily.bean.DailyListBean

import com.zx.module_supervise.module.daily.mvp.contract.DailyListContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DailyListModel : BaseModel(), DailyListContract.Model {
    override fun dailyListData(map: Map<String, String>): Observable<NormalList<DailyListBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDailyList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun dailyUpdate(body: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .updateDailyInfo(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}