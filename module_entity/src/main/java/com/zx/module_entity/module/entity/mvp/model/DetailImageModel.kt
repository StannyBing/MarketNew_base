package com.zx.module_entity.module.entity.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_entity.api.ApiService

import com.zx.module_entity.module.entity.mvp.contract.DetailImageContract
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailImageModel : BaseModel(), DetailImageContract.Model {
    override fun fileUploadData(body: RequestBody): Observable<List<String>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .uploadFile(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun modityData(body: RequestBody): Observable<Any> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .modifyInfo(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun deleteFileData(body: RequestBody): Observable<Any> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .deleteFile(body)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}