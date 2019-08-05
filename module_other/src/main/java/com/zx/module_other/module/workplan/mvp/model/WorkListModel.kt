package com.zx.module_other.module.workplan.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.workplan.bean.WorkPlanBean

import com.zx.module_other.module.workplan.mvp.contract.WorkListContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class WorkListModel : BaseModel(), WorkListContract.Model {
    override fun getWorkPlan(map: Map<String, String>): Observable<List<WorkPlanBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getWorkPlan(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }
}