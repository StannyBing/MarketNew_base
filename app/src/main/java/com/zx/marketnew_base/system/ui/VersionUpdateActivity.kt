package com.zx.marketnew_base.system.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.BuildConfig
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.VersionBean
import com.zx.marketnew_base.system.mvp.contract.VersionUpdateContract
import com.zx.marketnew_base.system.mvp.model.VersionUpdateModel
import com.zx.marketnew_base.system.mvp.presenter.VersionUpdatePresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.zxutils.util.ZXAppUtil
import kotlinx.android.synthetic.main.activity_version_update.*
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：版本更新
 */
@Route(path = RoutePath.ROUTE_APP_VERSIONUPDATE)
class VersionUpdateActivity : BaseActivity<VersionUpdatePresenter, VersionUpdateModel>(), VersionUpdateContract.View {

    private var versionBean: VersionBean? = null

    override var canSwipeBack = false

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, VersionUpdateActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_version_update
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mPresenter.getVerson()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        btn_doUpdate.setOnClickListener {
            getPermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                mPresenter.downloadApk(versionBean!!.url)
            }
        }
    }

    /**
     * 版本检测
     */
    override fun onVersionResult(versionBean: VersionBean) {
        this.versionBean = versionBean
        tv_update_content.text = versionBean.content
        if (BuildConfig.VERSION_CODE < versionBean.versionCode) {
            toolbar_view.setLeftClickListener {
                showToast("请先更新后再使用")
            }
            tv_update_info.text = "检查到新版本-${versionBean.versionName}，请更新后使用"
            btn_doUpdate.visibility = View.VISIBLE
        } else {
            tv_update_info.text = "当前已是最新版本-${versionBean.versionName}"
            btn_doUpdate.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if (BuildConfig.VERSION_CODE < versionBean?.versionCode ?: 0) {
            showToast("请先更新后再使用")
        } else {
            super.onBackPressed()
        }
    }

    /**
     * apk下载回调
     */
    override fun onApkDownloadResult(file: File) {
        ZXAppUtil.installApp(this, file.path)
    }

    override fun onDownLoadProgress(progress: Int) {
        btn_doUpdate.visibility = View.GONE
        pb_update.visibility = View.VISIBLE
        pb_update.progress = progress
    }

}
