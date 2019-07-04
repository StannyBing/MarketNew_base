package com.zx.module_other.module.filepreview.mvp.presenter

import com.frame.zxmvp.http.download.DownInfo
import com.frame.zxmvp.http.download.listener.DownloadOnNextListener
import com.frame.zxmvp.http.download.manager.HttpDownManager
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.app.ConstStrings
import com.zx.module_other.module.filepreview.mvp.contract.FilePreviewContract
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXFileUtil
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class FilePreviewPresenter : FilePreviewContract.Presenter() {
    override fun downloadFile(path: String) {
        val fileName = path.substring(path.lastIndexOf("/") + 1)
        val downInfo = DownInfo(path)
        downInfo.baseUrl = BaseConfigModule.BASE_IP
        downInfo.savePath = ConstStrings.getCachePath() + fileName
        downInfo.listener = object : DownloadOnNextListener<Any>() {
            override fun onNext(o: Any) {

            }

            override fun onStart() {
                ZXDialogUtil.showLoadingDialog(mContext, "正在下载中，请稍后...", 0)
            }

            override fun onComplete(file: File) {
                mView.onFileDownload(file)
                ZXDialogUtil.dismissLoadingDialog()
            }

            override fun updateProgress(progress: Int) {
                ZXDialogUtil.showLoadingDialog(mContext, "正在下载中，请稍后...", progress)
            }
        }
        if (ZXFileUtil.isFileExists(ConstStrings.getCachePath() + fileName)) {
            mView.onFileDownload(File(ConstStrings.getCachePath() + fileName))
        } else {
            HttpDownManager.getInstance().startDown(downInfo)
        }
    }


}