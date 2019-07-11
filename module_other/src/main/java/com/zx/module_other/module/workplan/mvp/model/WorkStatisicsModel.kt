package com.zx.module_other.module.workplan.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.mvp.contract.WorkPlanContract
import com.zx.module_other.module.workplan.mvp.contract.WorkStatisicsContract
import rx.Observable

class WorkStatisicsModel : BaseModel(), WorkStatisicsContract.Model {
    override fun getWorkStatisics(map: Map<String, String>): Observable<List<Int>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getWorkSatistics(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}