package com.zx.marketnew_base.main.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.marketnew_base.api.ApiService

import com.zx.marketnew_base.main.mvp.contract.MyTaskContract
import com.zx.module_library.bean.NormalList
import com.zx.marketnew_base.main.bean.TaskBean
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MyTaskModel : BaseModel(), MyTaskContract.Model {
    override fun taskListData(map: Map<String, String>): Observable<NormalList<TaskBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getTaskLsit(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}