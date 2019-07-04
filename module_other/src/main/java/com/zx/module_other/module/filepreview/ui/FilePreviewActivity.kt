package com.zx.module_other.module.filepreview.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.module.filepreview.mvp.contract.FilePreviewContract
import com.zx.module_other.module.filepreview.mvp.model.FilePreviewModel
import com.zx.module_other.module.filepreview.mvp.presenter.FilePreviewPresenter
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXFileUtil
import com.zx.zxutils.util.ZXLogUtil
import kotlinx.android.synthetic.main.activity_file_preview.*
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：文件查看
 */
@Route(path = RoutePath.ROUTE_OTHER_PREVIEW)
class FilePreviewActivity : BaseActivity<FilePreviewPresenter, FilePreviewModel>(), FilePreviewContract.View {

    private var name: String = "文件预览"
    private var path: String = ""

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, FilePreviewActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_file_preview
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        name = intent.getStringExtra("name")
        path = intent.getStringExtra("path")

        toolbar_view.setMidText(name)
        if (name.endsWith("png") || name.endsWith("jpg")) {
            pv_preview_image.visibility = View.VISIBLE
            vv_preview_video.visibility = View.GONE
            Glide.with(mContext)
                    .load(path)
                    .into(pv_preview_image)
        } else if (name.endsWith("mp4") || name.endsWith("rmvb") || name.endsWith("3gp") || name.endsWith("avi")) {
            pv_preview_image.visibility = View.GONE
            vv_preview_video.visibility = View.VISIBLE
            vv_preview_video.setMediaController(MediaController(this))
//            vv_preview_video.setOnErrorListener { mp, what, extra -> vv_preview_video. }
            vv_preview_video.setOnCompletionListener { vv_preview_video.start() }
            val uri: Uri
            if (path.startsWith("http")) {
//                mPresenter.downloadFile(hazardname, path)
                uri = Uri.parse(path)
                vv_preview_video.setVideoURI(uri)
            } else {
                uri = Uri.fromFile(File(path))
                vv_preview_video.setVideoURI(uri)
            }
            vv_preview_video.start()
            vv_preview_video.setOnPreparedListener {
                ZXLogUtil.loge(it.isPlaying.toString())
            }
        } else {
            pv_preview_image.visibility = View.GONE
            vv_preview_video.visibility = View.GONE
            if (path.startsWith("http")) {
                ZXDialogUtil.showYesNoDialog(this, "提示", "是否下载该文件？") { dialog, which ->
                    getPermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        mPresenter.downloadFile(path)
                    }
                }.setOnDismissListener {
                    finish()
                }
            } else {
                ZXFileUtil.openFile(this, File(path))
            }
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (vv_preview_video != null) {
            vv_preview_video.stopPlayback()
            vv_preview_video.suspend()
        }
    }

    /**
     * 下载回调
     */
    override fun onFileDownload(file: File) {
        ZXFileUtil.openFile(this, File(path))
    }

}
