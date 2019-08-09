package com.zx.module_entity.module.special.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.frame.zxmvp.http.upload.UploadRequestBody
import com.zx.module_entity.module.special.bean.DeptBean
import com.zx.module_entity.module.special.mvp.contract.SpecialAddContract
import com.zx.module_library.BuildConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class SpecialAddPresenter : SpecialAddContract.Presenter() {
    override fun getNormalInfo() {
        mModel.dicListData(hashMapOf("dicType" to "20"))
                .compose(RxHelper.bindToLifecycle(mView))
                .flatMap {
                    mView.onDicListResult(it)
                    mModel.dicListData(hashMapOf("dicType" to "16"))
                }
                .flatMap {
                    mView.onIdentifyListResult(it)
                    mModel.deptListData(hashMapOf("type" to "10", "parentId" to BuildConfig.AREA_ID))
                }
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

    override fun getAreaDeptList(map: Map<String, String>) {
        mModel.areaDeptListData(map)
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<List<DeptBean>>() {
                    override fun _onNext(deptBeans: List<DeptBean>) {
                        mView.onAreaDeptListResult(deptBeans)
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }

    override fun doSubmit(body: RequestBody) {
        mModel.submitData(body)
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

    override fun uploadFile(files: List<File>) {
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
                            mView.onFileUploadResult(s[0], s[1])
                        } else {
                            mView.showToast("文件上传失败，请重试")
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }
                })
    }


}