package com.zx.module_supervise.module.daily.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_supervise.api.ApiService
import com.zx.module_supervise.module.daily.mvp.contract.DailyCheckContract
import com.zx.module_supervise.module.task.bean.TaskCheckBean
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DailyCheckModel : BaseModel(), DailyCheckContract.Model {
    override fun checkListData(map: Map<String, String>): Observable<List<TaskCheckBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getTempletCheckList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun dailyCheckData(map: Map<String, String>): Observable<List<TaskCheckBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .dailyCheckResult(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}