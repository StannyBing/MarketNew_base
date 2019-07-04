package com.zx.module_other.module.law.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_library.bean.NormalList
import com.zx.module_other.module.law.bean.LawBean
import com.zx.module_other.module.law.mvp.contract.LawContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class LawPresenter : LawContract.Presenter() {
   /* override fun getLawList(map: Map<String, String>) {
        mModel.lawListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<LawBean>>(){
                    override fun _onNext(t: List<LawBean>?) {
                        if (t!=null){
                            mView.onLawListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code,message)
                    }
                })
    }*/
}