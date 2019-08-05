package com.zx.module_supervise.module.daily.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_supervise.module.daily.mvp.contract.CheckListContract
import com.zx.module_supervise.module.task.bean.TaskCheckBean
import okhttp3.RequestBody


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class CheckListPresenter : CheckListContract.Presenter() {

    override fun getCheckList(map: Map<String, String>, pid: String) {
        mModel.checkListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<TaskCheckBean>>() {
                    override fun _onNext(t: List<TaskCheckBean>) {
                        mView.onCheckListResult(t, pid)
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun queryItemList(map: Map<String, String>) {
        mModel.queryItemData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<TaskCheckBean>>() {
                    override fun _onNext(t: List<TaskCheckBean>) {
                        mView.onCheckListResult(t, "")
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun getTempletCheckList(map: Map<String, String>) {
        mModel.templetCheckListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<TaskCheckBean>>() {
                    override fun _onNext(checkDetailBeans: List<TaskCheckBean>) {
                        mView.onTempletCheckListResult(checkDetailBeans)
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun saveTemplet(info: RequestBody, templateName: String) {
        mModel.saveTempletData(info)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onSaveTempletResult(s, templateName)
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }
}