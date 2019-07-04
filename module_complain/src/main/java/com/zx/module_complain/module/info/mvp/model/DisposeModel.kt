package com.zx.module_complain.module.info.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_complain.api.ApiService
import com.zx.module_complain.module.info.bean.DeptBean
import com.zx.module_complain.module.info.mvp.contract.DisposeContract
import com.zx.module_library.bean.UserBean
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DisposeModel : BaseModel(), DisposeContract.Model {
    override fun disposeData(info : RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .submitDispose(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun deptListData(map: Map<String, String>): Observable<List<DeptBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDeptList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun userListData(map: Map<String, String>): Observable<List<UserBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getUserList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun fileUploadData(info: RequestBody): Observable<List<String>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .uploadFile(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}