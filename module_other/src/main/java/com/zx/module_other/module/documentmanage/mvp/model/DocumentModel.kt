package com.zx.module_other.module.workplan.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.documentmanage.bean.DocumentBean
import com.zx.module_other.module.workplan.mvp.contract.DocumentContract
import rx.Observable

class DocumentModel : BaseModel(), DocumentContract.Model {
    override fun getDocument(map: Map<String, String>): Observable<List<DocumentBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDocument(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}