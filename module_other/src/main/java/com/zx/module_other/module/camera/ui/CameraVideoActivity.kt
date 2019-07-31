package com.zx.module_other.module.camera.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.ConstStrings
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.module.camera.mvp.contract.CameraVideoContract
import com.zx.module_other.module.camera.mvp.model.CameraVideoModel
import com.zx.module_other.module.camera.mvp.presenter.CameraVideoPresenter
import com.zx.zxutils.util.ZXBitmapUtil
import com.zx.zxutils.util.ZXTimeUtil
import com.zx.zxutils.views.CameraView.ZXCameraView
import com.zx.zxutils.views.CameraView.listener.CameraListener
import com.zx.zxutils.views.ZXStatusBarCompat
import kotlinx.android.synthetic.main.activity_camera_video.*
import java.io.File
import java.text.SimpleDateFormat


/**
 * Create By admin On 2017/7/11
 * 功能：图片拍照-录像
 */
@Route(path = RoutePath.ROUTE_OTHER_CAMERA)
class CameraVideoActivity : BaseActivity<CameraVideoPresenter, CameraVideoModel>(), CameraVideoContract.View {

    private var cameraType = 0//0全部  1拍照  2视频

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, CameraVideoActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_camera_video
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ZXStatusBarCompat.translucent(this)
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        cameraType = intent.getIntExtra("cameraType", 0)

        cv_camera_view.setSaveVideoPath(ConstStrings.getCachePath())
                .setCameraMode(if (cameraType == 1) ZXCameraView.BUTTON_STATE_ONLY_CAPTURE else if (cameraType == 2) ZXCameraView.BUTTON_STATE_ONLY_RECORDER else ZXCameraView.BUTTON_STATE_BOTH)
                .setMediaQuality(if (mSharedPrefUtil.contains("video_quality")) mSharedPrefUtil.getInt("video_quality") else ZXCameraView.MEDIA_QUALITY_MIDDLE)
                .setMaxVedioDuration(if (mSharedPrefUtil.contains("video_duration")) mSharedPrefUtil.getInt("video_duration") else 30)
                .showAlbumView(cameraType == 1)
                .setCameraLisenter(object : CameraListener {
                    override fun onCaptureCommit(bitmap: Bitmap?) {
                        val time = ZXTimeUtil.getTime(System.currentTimeMillis(), SimpleDateFormat("yyyyMMdd_HHmmss"))
                        val name = time + ".jpg"
                        val path = ConstStrings.getCachePath() + time + ".jpg"
                        ZXBitmapUtil.bitmapToFile(bitmap, File(path))
                        val intent = Intent()
                        intent.putExtra("path", path)
                        intent.putExtra("name", name)
                        setResult(0x02, intent)
                        finish()
                    }

                    override fun onRecordCommit(url: String?, firstFrame: Bitmap?) {
                        val time = ZXTimeUtil.getTime(System.currentTimeMillis(), SimpleDateFormat("yyyyMMdd_HHmmss"))
                        val name = time + ".mp4"
                        val intent = Intent()
                        intent.putExtra("path", url)
                        intent.putExtra("name", name)
                        setResult(0x02, intent)
                        finish()
                    }

                    override fun onActionSuccess(type: CameraListener.CameraType?) {
                    }


                    override fun onError(errorType: CameraListener.ErrorType?) {
                        showToast("相机打开失败，请稍后再试")
                        finish()
                    }

                })
        if (cameraType == 1) {
            cv_camera_view.setTip("轻触拍照")
        } else if (cameraType == 2) {
            cv_camera_view.setTip("长按摄像")
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }


    override fun onPause() {
        super.onPause()
        cv_camera_view.onPause()
    }

    override fun onResume() {
        super.onResume()
        cv_camera_view.onResume()
    }

}
