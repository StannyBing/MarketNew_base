package com.zx.module_other.module.workplan.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.module_other.api.ApiService
import com.zx.module_other.module.documentmanage.bean.DocumentBean
import com.zx.module_other.module.documentmanage.bean.TemplateFieldBean
import com.zx.module_other.module.workplan.mvp.contract.DocumentContract
import com.zx.module_other.module.workplan.mvp.contract.DocumentFillContract
import okhttp3.ResponseBody
import rx.Observable

class DocumentFillModel : BaseModel(), DocumentFillContract.Model {
    override fun getDocumentPrint(map: Map<String, String>): Observable<ResponseBody> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDocumentPrintHtml(map)
    }

    override fun getDocumentField(map: Map<String, String>): Observable<List<TemplateFieldBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getDocumentField(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }

}