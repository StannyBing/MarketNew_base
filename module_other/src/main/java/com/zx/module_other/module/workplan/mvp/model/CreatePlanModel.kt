package com.zx.module_other.module.workplan.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.mvp.contract.CreatePlanContract
import okhttp3.RequestBody
import rx.Observable

class CreatePlanModel : BaseModel(), CreatePlanContract.Model {
    override fun createWorkPlan(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .createWorkPlan(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}