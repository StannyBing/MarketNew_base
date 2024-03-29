package com.zx.module_supervise.module.task.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_supervise.api.ApiService
import com.zx.module_supervise.module.task.bean.TaskCheckBean

import com.zx.module_supervise.module.task.mvp.contract.DisposeCheckContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DisposeCheckModel : BaseModel(), DisposeCheckContract.Model {
    override fun checkListData(map: Map<String, String>): Observable<List<TaskCheckBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getSuperviseCheckList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}