package com.zx.module_entity.module.entity.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_entity.api.ApiParamUtil
import com.zx.module_entity.module.entity.bean.EntityStationBean
import com.zx.module_entity.module.entity.bean.EntityBean
import com.zx.module_entity.module.entity.mvp.contract.QueryContract
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.bean.NormalList


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class QueryPresenter : QueryContract.Presenter() {
    override fun getEntityList(map: Map<String, String>) {
        mModel.entityListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<NormalList<EntityBean>>() {
                    override fun _onNext(t: NormalList<EntityBean>?) {
                        if (t != null) {
                            mView.onEntityListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

    override fun getFilterInfo() {
        mModel.tagListData(ApiParamUtil.tagListParam("16"))
                .compose(RxHelper.bindToLifecycle(mView))
                .flatMap {
                    mView.onTagListResult(it)
                    mModel.entityLevelData()
                }
                .flatMap {
                    mView.onEntityLevelResult(it)
                    mModel.deptListData(ApiParamUtil.entityStationParam(BaseConfigModule.appInfo.areaParentId))
                }
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