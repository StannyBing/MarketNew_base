package com.zx.module_supervise.module.daily.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.module.daily.bean.TemplateBean
import com.zx.module_supervise.module.daily.mvp.contract.TemplateContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class TemplatePresenter : TemplateContract.Presenter() {
    override fun getModelList(map: Map<String, String>) {
        mModel.modelListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<TemplateBean>>() {
                    override fun _onNext(t: NormalList<TemplateBean>?) {
                        if (t != null) {
                            mView.onModelListResult(t.list)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }


}