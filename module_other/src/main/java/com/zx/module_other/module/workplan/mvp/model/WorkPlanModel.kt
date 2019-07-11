package com.zx.module_other.module.workplan.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.mvp.contract.WorkPlanContract
import rx.Observable

class WorkPlanModel : BaseModel(), WorkPlanContract.Model {
    override fun getWorkPlan(map: Map<String, String>): Observable<List<WorkPlanBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getWorkPlan(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}