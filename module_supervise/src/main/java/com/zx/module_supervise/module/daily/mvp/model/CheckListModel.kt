package com.zx.module_supervise.module.daily.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_supervise.api.ApiService

import com.zx.module_supervise.module.daily.mvp.contract.CheckListContract
import com.zx.module_supervise.module.task.bean.TaskCheckBean
import okhttp3.RequestBody
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class CheckListModel : BaseModel(), CheckListContract.Model {
    override fun queryItemData(map: Map<String, String>): Observable<List<TaskCheckBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .queryCheckList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun checkListData(map: Map<String, String>): Observable<List<TaskCheckBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getCheckItemList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun templetCheckListData(map: Map<String, String>): Observable<List<TaskCheckBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getTempletCheckList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

    override fun saveTempletData(info: RequestBody): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .saveTemplet(info)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}