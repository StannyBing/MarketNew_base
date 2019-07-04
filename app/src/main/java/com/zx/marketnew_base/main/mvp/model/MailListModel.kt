package com.zx.marketnew_base.main.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.marketnew_base.api.ApiService
import com.zx.marketnew_base.main.bean.MailListBean

import com.zx.marketnew_base.main.mvp.contract.MailListContract
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MailListModel : BaseModel(), MailListContract.Model {
    override fun mailListData(map: Map<String, String>): Observable<List<MailListBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getMailList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }


}