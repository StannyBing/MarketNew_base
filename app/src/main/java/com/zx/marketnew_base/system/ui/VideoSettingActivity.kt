package com.zx.marketnew_base.system.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.FuncBean
import com.zx.marketnew_base.main.func.adapter.FuncAdapter
import com.zx.marketnew_base.system.mvp.contract.VideoSettingContract
import com.zx.marketnew_base.system.mvp.model.VideoSettingModel
import com.zx.marketnew_base.system.mvp.presenter.VideoSettingPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.views.CameraView.ZXCameraView
import kotlinx.android.synthetic.main.activity_video_setting.*


/**
 * Create By admin On 2017/7/11
 * 功能：设置-；录像设置
 */
@Route(path = RoutePath.ROUTE_APP_SETTING_VIDEO)
class VideoSettingActivity : BaseActivity<VideoSettingPresenter, VideoSettingModel>(), VideoSettingContract.View {


    var dataBeans = arrayListOf<FuncBean>()
    var listAdapter = FuncAdapter(dataBeans)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, VideoSettingActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_video_setting
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        rv_setting.apply {
            layoutManager = LinearLayoutManager(this@VideoSettingActivity)
            adapter = listAdapter
        }

        val nowQuality = arrayListOf("超高清", "高清", "普清", "低清", "超低清")[when (mSharedPrefUtil.getInt("video_quality", ZXCameraView.MEDIA_QUALITY_MIDDLE)) {
            ZXCameraView.MEDIA_QUALITY_HIGH -> 0
            ZXCameraView.MEDIA_QUALITY_MIDDLE -> 1
            ZXCameraView.MEDIA_QUALITY_LOW -> 2
            ZXCameraView.MEDIA_QUALITY_POOR -> 3
            ZXCameraView.MEDIA_QUALITY_FUNNY -> 4
            else -> 1
        }]

        val nowDuation = arrayListOf("10s", "30s", "1min", "2min")[when (mSharedPrefUtil.getInt("video_duration", 30)) {
            10 -> 0
            30 -> 1
            60 -> 2
            120 -> 3
            else -> 1
        }]


        dataBeans.add(FuncBean("录像分辨率", R.drawable.app_func_videoquality, false, nowQuality))
        dataBeans.add(FuncBean("时长限制", R.drawable.app_func_videodruation, false, nowDuation))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //菜单点击事件
        listAdapter.setOnItemClickListener { adapter, view, position ->
            when (dataBeans[position].title) {
                "录像分辨率" -> {
                    val list = arrayListOf("超高清", "高清", "普清", "低清", "超低清")
                    val quality = mSharedPrefUtil.getInt("video_quality", ZXCameraView.MEDIA_QUALITY_MIDDLE)
                    list[when (quality) {
                        ZXCameraView.MEDIA_QUALITY_HIGH -> 0
                        ZXCameraView.MEDIA_QUALITY_MIDDLE -> 1
                        ZXCameraView.MEDIA_QUALITY_LOW -> 2
                        ZXCameraView.MEDIA_QUALITY_POOR -> 3
                        ZXCameraView.MEDIA_QUALITY_FUNNY -> 4
                        else -> 1
                    }] += "（当前）"
                    mSharedPrefUtil.getInt("video_quality")
                    ZXDialogUtil.showListDialog(this, "", "关闭", list) { _, which ->
                        mSharedPrefUtil.putInt("video_quality", when (which) {
                            0 -> ZXCameraView.MEDIA_QUALITY_HIGH
                            1 -> ZXCameraView.MEDIA_QUALITY_MIDDLE
                            2 -> ZXCameraView.MEDIA_QUALITY_LOW
                            3 -> ZXCameraView.MEDIA_QUALITY_POOR
                            4 -> ZXCameraView.MEDIA_QUALITY_FUNNY
                            else -> ZXCameraView.MEDIA_QUALITY_MIDDLE
                        })
                    }
                }
                "时长限制" -> {
                    val list = arrayListOf("10s", "30s", "1min", "2min")
                    val quality = mSharedPrefUtil.getInt("video_duration", ZXCameraView.MEDIA_QUALITY_MIDDLE)
                    list[when (quality) {
                        10 -> 0
                        30 -> 1
                        60 -> 2
                        120 -> 3
                        else -> 1
                    }] += "（当前）"
                    mSharedPrefUtil.getInt("video_duration")
                    ZXDialogUtil.showListDialog(this, "", "关闭", list) { _, which ->
                        mSharedPrefUtil.putInt("video_duration", when (which) {
                            0 -> 10
                            1 -> 30
                            2 -> 60
                            3 -> 120
                            else -> 30
                        })
                    }
                }
            }
        }
    }

}
