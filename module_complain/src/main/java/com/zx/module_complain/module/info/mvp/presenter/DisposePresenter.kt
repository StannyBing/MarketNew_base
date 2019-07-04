package com.zx.module_complain.module.info.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.frame.zxmvp.http.upload.UploadRequestBody
import com.zx.module_complain.module.info.bean.DeptBean
import com.zx.module_complain.module.info.mvp.contract.DisposeContract
import com.zx.module_library.bean.UserBean
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DisposePresenter : DisposeContract.Presenter() {
    override fun uploadFile(fileName: String, files: List<File>) {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        for (file in files) {
            builder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
        }
        if (!fileName.isEmpty()) {
            builder.addFormDataPart("id", fileName)
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

    override fun submitDispose(info: RequestBody) {
        mModel.disposeData(info)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<String>() {
                    override fun _onNext(t: String?) {
                        mView.onDisposeResult()
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun getDeptList(map: Map<String, String>) {
        mModel.deptListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<DeptBean>>() {
                    override fun _onNext(t: List<DeptBean>?) {
                        if (t != null) {
                            mView.onDeptListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


    override fun getUserList(map: Map<String, String>) {
        mModel.userListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<UserBean>>() {
                    override fun _onNext(t: List<UserBean>?) {
                        if (t != null) {
                            mView.onUserListResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }


}