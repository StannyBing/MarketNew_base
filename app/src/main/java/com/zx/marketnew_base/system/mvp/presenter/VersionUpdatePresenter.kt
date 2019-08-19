package com.zx.marketnew_base.system.mvp.presenter

import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.frame.zxmvp.http.download.DownInfo
import com.frame.zxmvp.http.download.listener.DownloadOnNextListener
import com.frame.zxmvp.http.download.manager.HttpDownManager
import com.zx.marketnew_base.main.bean.VersionBean
import com.zx.marketnew_base.system.mvp.contract.VersionUpdateContract
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.app.ConstStrings
import com.zx.zxutils.util.ZXFileUtil
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class VersionUpdatePresenter : VersionUpdateContract.Presenter() {

    override fun downloadApk(downUrl: String) {
        val fileName = downUrl.substring(downUrl.lastIndexOf("/") + 1)
        val downInfo = DownInfo(downUrl)
        downInfo.baseUrl = BaseConfigModule.BASE_IP
        downInfo.savePath = ConstStrings.getApkPath() + fileName
        downInfo.listener = object : DownloadOnNextListener<Any>() {
            override fun onNext(o: Any) {

            }

            override fun onStart() {
                mView.onDownLoadProgress(0)
            }

            override fun onComplete(file: File) {
                mView.onApkDownloadResult(file)
                mView.onDownLoadProgress(100)
            }

            override fun updateProgress(progress: Int) {
                mView.onDownLoadProgress(progress)
            }
        }
        if (ZXFileUtil.isFileExists(ConstStrings.getApkPath() + fileName)) {
            mView.onApkDownloadResult(File(ConstStrings.getApkPath() + fileName))
        } else {
            HttpDownManager.getInstance().startDown(downInfo)
        }
    }

    override fun getVerson() {
        mModel.versionData()
                .compose(RxHelper.bindToLifecycle(mView))
                .subscribe(object : RxSubscriber<VersionBean>() {
                    override fun _onNext(t: VersionBean?) {
                        if (t != null) {
                            mView.onVersionResult(t)
                        }
                    }

                    override fun _onError(code: String?, message: String?) {
                        mView.handleError(code, message)
                    }

                })
    }

}