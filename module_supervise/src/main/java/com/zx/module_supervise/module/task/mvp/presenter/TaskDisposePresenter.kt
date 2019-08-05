package com.zx.module_supervise.module.task.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.frame.zxmvp.http.upload.UploadRequestBody
import com.zx.module_supervise.module.task.mvp.contract.TaskDisposeContract
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class TaskDisposePresenter : TaskDisposeContract.Presenter() {

    override fun submitDispose(body: RequestBody) {
        mModel.submitDisposeData(body)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onSubmitResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun submitBack(body: RequestBody) {
        mModel.submitBackData(body)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(mView) {
                    override fun _onNext(s: String) {
                        mView.onSubmitResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun uploadFile(type: Int, vararg files: File) {
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

    override fun submitAudit(body: RequestBody) {
        mModel.submitAuditData(body)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>(mView){
                    override fun _onNext(t: String?) {
                        mView.onSubmitResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}