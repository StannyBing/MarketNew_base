package com.zx.module_supervise.module.daily.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.frame.zxmvp.http.upload.UploadRequestBody
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.module.daily.bean.DailyDetailBean
import com.zx.module_supervise.module.daily.bean.EntityBean
import com.zx.module_supervise.module.daily.mvp.contract.DailyAddContract
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DailyAddPresenter : DailyAddContract.Presenter() {

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

    override fun uploadFile(type: Int, files: List<File>) {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        for (file in files) {
            builder.addFormDataPart("files", file.name, RequestBody.create(MediaType.parse("multipart/form-data"), file))
        }
        val uploadRequestBody = UploadRequestBody(builder.build()) { progress, done -> mView.showLoading("正在上传中...", progress) }
        mModel.fileUploadData(uploadRequestBody)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<String>>() {
                    override fun _onNext(s: List<String>) {
                        if (s.size > 1) {
                            mView.onFileUploadResult(s[0], s[1], type)
                        } else {
                            mView.showToast("文件上传失败，请重试")
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                        mView.dismissLoading()
                    }
                })
    }

    override fun saveDailyInfo(body: RequestBody) {
        mModel.saveDailyData(body)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onDailySaveResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }


}