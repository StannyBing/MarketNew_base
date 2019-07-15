package com.zx.module_entity.module.entity.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_entity.api.ApiService
import com.zx.module_entity.module.entity.bean.DicTypeBean
import com.zx.module_entity.module.entity.bean.EntityLevelBean
import com.zx.module_entity.module.entity.bean.EntityStationBean
import com.zx.module_entity.module.entity.func.adapter.EntityBean
import com.zx.module_entity.module.entity.mvp.contract.QueryContract
import com.zx.module_library.bean.NormalList
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class QueryModel : BaseModel(), QueryContract.Model {
    override fun entityListData(map: Map<String, String>): Observable<NormalList<EntityBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getEntityList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun tagListData(map: Map<String, String>): Observable<List<DicTypeBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDicTypeList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun entityLevelData(): Observable<List<EntityLevelBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getEntityLevel()
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun deptListData(): Observable<List<EntityStationBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getEntityStation(hashMapOf())
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