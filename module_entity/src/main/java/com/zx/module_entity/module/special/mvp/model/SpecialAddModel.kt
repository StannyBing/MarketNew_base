package com.zx.module_entity.module.special.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_entity.api.ApiService
import com.zx.module_entity.module.entity.bean.DicTypeBean
import com.zx.module_entity.module.special.bean.DeptBean

import com.zx.module_entity.module.special.mvp.contract.SpecialAddContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class SpecialAddModel : BaseModel(), SpecialAddContract.Model {

    override fun dicListData(map: Map<String, String>): Observable<List<DicTypeBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDicTypeList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun deptListData(map: Map<String, String>): Observable<List<DeptBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDeptList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun areaDeptListData(map: Map<String, String>): Observable<List<DeptBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDeptList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun submitData(body: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .addSpecialEntity(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun fileUploadData(body: RequestBody): Observable<List<String>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .uploadFile(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}