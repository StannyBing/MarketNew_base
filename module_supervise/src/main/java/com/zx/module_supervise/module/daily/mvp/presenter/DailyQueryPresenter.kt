package com.zx.module_supervise.module.daily.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.module.daily.bean.DailyQueryBean
import com.zx.module_supervise.module.daily.bean.EntityStationBean
import com.zx.module_supervise.module.daily.mvp.contract.DailyQueryContract


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DailyQueryPresenter : DailyQueryContract.Presenter() {
    override fun getEntitys(map: Map<String, String>) {
        mModel.entitysData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<DailyQueryBean>>(){
                    override fun _onNext(t: NormalList<DailyQueryBean>?) {
                        if (t != null) {
                            mView.onEntitysResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

    override fun getDeptList(map: Map<String, String>) {
        mModel.areaDeptListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<EntityStationBean>>() {
                    override fun _onNext(stationBeans: List<EntityStationBean>) {
                        mView.onDeptListResult(stationBeans)
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun getAreaDeptList(map: Map<String, String>) {
        mModel.areaDeptListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<EntityStationBean>>() {
                    override fun _onNext(stationBeans: List<EntityStationBean>) {
                        mView.onAreaDeptListResult(stationBeans)
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }


}