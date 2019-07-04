package com.zx.marketnew_base.main.mvp.model

import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSchedulers
import com.zx.marketnew_base.api.ApiService
import com.zx.marketnew_base.main.bean.MessageBean
import com.zx.marketnew_base.main.mvp.contract.MessageContract
import com.zx.module_library.bean.NormalList
import rx.Observable

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MessageModel : BaseModel(), MessageContract.Model {
    override fun messageListData(map: Map<String, String>): Observable<NormalList<MessageBean>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getMessageList(map)
                .compose(RxHelper.handleResult())
                .compose(RxSchedulers.io_main())
    }
}