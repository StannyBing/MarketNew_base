package com.zx.module_supervise.module.daily.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.api.ApiService
import com.zx.module_supervise.module.daily.bean.TemplateBean

import com.zx.module_supervise.module.daily.mvp.contract.TemplateContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class TemplateModel : BaseModel(), TemplateContract.Model {
    override fun modelListData(map: Map<String, String>): Observable<NormalList<TemplateBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getTempletList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}