package com.zx.module_supervise.module.supervise.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_supervise.api.ApiService
import com.zx.module_supervise.module.supervise.bean.EntityInfoBean
import com.zx.module_supervise.module.supervise.bean.TaskInfoBean

import com.zx.module_supervise.module.supervise.mvp.contract.SuperviseDetailContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class SuperviseDetailModel : BaseModel(), SuperviseDetailContract.Model {

    override fun taskInfoData(map: Map<String, String>): Observable<TaskInfoBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getSuperviseDetailTaskInfo(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun entityInfoData(map: Map<String, String>): Observable<EntityInfoBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getSuperviseDetailEntityInfo(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}