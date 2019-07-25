package com.zx.module_supervise.module.daily.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.api.ApiService
import com.zx.module_supervise.module.daily.bean.DailyDetailBean
import com.zx.module_supervise.module.daily.bean.EntityBean
import com.zx.module_supervise.module.daily.mvp.contract.DailyAddContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DailyAddModel : BaseModel(), DailyAddContract.Model {
    override fun entityListData(map: Map<String, String>): Observable<NormalList<EntityBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getEntityList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun fileUploadData(body: RequestBody): Observable<List<String>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .uploadFile(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun saveDailyData(body: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .saveDailyInfo(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }



}