package com.zx.module_other.module.workresults.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.workresults.mvp.contract.WorkResultsContract
import com.zx.module_other.module.workresults.bean.WorkOverAllBean
import com.zx.module_other.module.workresults.bean.WorkStatisicsBean
import rx.Observable

class WorkResultsModel : BaseModel(), WorkResultsContract.Model {
    override fun getWorkOverall(map: Map<String, String>): Observable<List<WorkOverAllBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getWorkOverAlls(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun getWorkStatisics(map: Map<String, String>): Observable<List<WorkStatisicsBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getWorkSatistics(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}