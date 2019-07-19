package com.zx.module_entity.module.entity.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.frame.zxmvp.http.upload.UploadRequestBody
import com.zx.module_entity.module.entity.mvp.contract.DetailImageContract
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailImagePresenter : DetailImageContract.Presenter() {

    override fun uploadFile(oldIds: String, files: List<File>) {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        for (file in files) {
            builder.addFormDataPart("files", file.name, RequestBody.create(MediaType.parse("multipart/form-data"), file))
        }
        if (!oldIds.isEmpty()) {
            builder.addFormDataPart("id", oldIds)
        }
        val uploadRequestBody = UploadRequestBody(builder.build()) { progress, done -> mView.showLoading("正在上传中...", progress) }
        mModel.fileUploadData(uploadRequestBody)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<String>>() {
                    override fun _onNext(s: List<String>) {
                        if (s.size > 1) {
                            mView.onFileUploadResult(s[0], s[1])
                        } else {
                            mView.showToast("文件上传失败，请重试")
                        }
                    }

                    override fun _onError(code: String, message: String) {
                        mView.handleError(code, message)
                    }
                })
    }


    override fun doModify(info: RequestBody) {
        mModel.modityData(info)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<Any>(mView) {
                    override fun _onNext(s: Any) {
                        mView.onInfoModifyResult()
                    }

                    override fun _onError(code: String, message: String) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun deleteFile(posiiton : Int, info: RequestBody) {
        mModel.deleteFileData(info)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<Any?>(mView) {
                    override fun _onNext(s: Any?) {
                        mView.onFileDeleteResult(posiiton)
                    }

                    override fun _onError(code: String, message: String) {
                        mView.handleError(code, message)
                    }
                })
    }

}