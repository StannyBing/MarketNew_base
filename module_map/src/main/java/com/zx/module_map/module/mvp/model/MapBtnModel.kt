package com.zx.module_map.module.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_map.api.ApiService
import com.zx.module_map.module.bean.DeptBean
import com.zx.module_map.module.bean.DicTypeBean

import com.zx.module_map.module.mvp.contract.MapBtnContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MapBtnModel : BaseModel(), MapBtnContract.Model {
    override fun changePosData(body: RequestBody): Observable<String> {
        return BaseModel.mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .updateEntityInfo(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun stationListData(map: Map<String, String>): Observable<List<DicTypeBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getStationList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun gridListData(map: Map<String, String>): Observable<List<DeptBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getGridList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }
}