package com.zx.module_map.module.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_map.module.bean.DeptBean
import com.zx.module_map.module.bean.DicTypeBean
import com.zx.module_map.module.mvp.contract.MapBtnContract
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MapBtnPresenter : MapBtnContract.Presenter() {

    override fun changePos(body: RequestBody) {
        mModel.changePosData(body)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onChangePosResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun getStationList(map: Map<String, String>) {
        mModel.stationListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<DicTypeBean>>(){
                    override fun _onNext(t: List<DicTypeBean>?) {
                        if (t != null) {
                            mView.onStationListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

    override fun getGridList(map: Map<String, String>) {
        mModel.gridListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<DeptBean>>(){
                    override fun _onNext(t: List<DeptBean>?) {
                        if (t != null) {
                            mView.onGridListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}