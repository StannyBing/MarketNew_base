package com.zx.module_supervise.module.daily.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.api.ApiService
import com.zx.module_supervise.module.daily.bean.DailyQueryBean
import com.zx.module_supervise.module.daily.bean.EntityStationBean
import com.zx.module_supervise.module.daily.mvp.contract.DailyQueryContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DailyQueryModel : BaseModel(), DailyQueryContract.Model {
    override fun entitysData(map: Map<String, String>): Observable<NormalList<DailyQueryBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDailyEntitys(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun areaDeptListData(map: Map<String, String>): Observable<List<EntityStationBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getEntityStation(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}