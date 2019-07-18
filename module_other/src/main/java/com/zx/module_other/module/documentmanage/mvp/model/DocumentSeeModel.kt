package com.zx.module_other.module.workplan.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.workplan.mvp.contract.DocumentSeeContract
import rx.Observable

class DocumentSeeModel : BaseModel(), DocumentSeeContract.Model {
    override fun getDocumentWeb(map: Map<String, String>): Observable<String> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDocumentSee(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}