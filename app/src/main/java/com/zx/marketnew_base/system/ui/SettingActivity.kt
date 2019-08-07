package com.zx.marketnew_base.system.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.FuncBean
import com.zx.marketnew_base.main.bean.VersionBean
import com.zx.marketnew_base.main.func.adapter.FuncAdapter
import com.zx.marketnew_base.system.mvp.contract.SettingContract
import com.zx.marketnew_base.system.mvp.model.SettingModel
import com.zx.marketnew_base.system.mvp.presenter.SettingPresenter
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.app.ConstStrings
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.UserManager
import com.zx.zxutils.util.ZXAppUtil
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXFileUtil
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：设置
 */
@Route(path = RoutePath.ROUTE_APP_SETTING)
class SettingActivity : BaseActivity<SettingPresenter, SettingModel>(), SettingContract.View {

    var dataBeans = arrayListOf<FuncBean>()
    var listAdapter = FuncAdapter(dataBeans)

    private var clearFileList: ArrayList<String> = arrayListOf(ConstStrings.getCachePath(),
            ConstStrings.getApkPath(),
            ConstStrings.getOnlinePath(),
            ConstStrings.getZipPath(),
            ConstStrings.getLocalPath(),
            ConstStrings.getCachePath())

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, checkVerson: Boolean = false) {
            val intent = Intent(activity, SettingActivity::class.java)
            intent.putExtra("checkVerson", checkVerson)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        rv_setting.apply {
            layoutManager = LinearLayoutManager(this@SettingActivity)
            adapter = listAdapter
        }

        dataBeans.add(FuncBean("录像设置", R.drawable.app_func_video))
        dataBeans.add(FuncBean("清理缓存", R.drawable.app_func_clear, true))
        dataBeans.add(FuncBean("检查更新", R.drawable.app_func_version))
        dataBeans.add(FuncBean("意见反馈", R.drawable.app_func_feedback))
        dataBeans.add(FuncBean("修改密码", R.drawable.app_func_law, true))
        dataBeans.add(FuncBean("退出登录", R.drawable.app_func_logout))

        if (intent.hasExtra("checkVerson") && intent.getBooleanExtra("checkVerson", false)) {
            mPresenter.getVerson()
        }

        listAdapter.notifyValue("清理缓存", getFileSize().toString() + "M")
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //菜单点击事件
        listAdapter.setOnItemClickListener { adapter, view, position ->
            when (dataBeans[position].title) {
                "录像设置" -> {
                    VideoSettingActivity.startAction(this, false)
                }
                "清理缓存" -> {
                    showLoading("正在清理中...")
                    clearFile()
                    Thread {
                        Glide.get(this).clearDiskCache()
                    }.start()
                    Glide.get(this).clearMemory()
                    handler.postDelayed({
                        listAdapter.notifyValue("清理缓存", getFileSize().toString() + "M")
                        dismissLoading()
                        showToast("清理完成")
                    }, 1000)
                }
                "检查更新" -> {
                    mPresenter.getVerson()
                }
                "意见反馈" -> {
                    FeedBackActivity.startAction(this,false)
                }
                "修改密码" -> {
                    ForgetPwdActivity.startAction(this, false, UserManager.getUser().telephone)
                }
                "退出登录" -> {
                    ZXDialogUtil.showYesNoDialog(this, "提示", "是否退出登录？") { dialog, which ->
                        UserManager.loginOut()
                        JPushInterface.stopPush(this)
                        LoginActivity.startAction(this, false)
                    }
                }
                else -> {
                    showToast("正在开发中")
                }
            }
        }
    }

    /**
     * 版本检测
     */
    override fun onVersionResult(versionBean: VersionBean) {
        val versionCode = BaseConfigModule.appInfo.versionCode
        if (versionCode < versionBean.versionCode) {
            ZXDialogUtil.showYesNoDialog(mContext, "提示", "当前应用需要下载更新\n版本号:${versionBean.versionName}\n内容:${versionBean.content}") { dialog, which ->
                getPermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    mPresenter.downloadApk(versionBean.url)
                }
            }
        }
    }

    /**
     * apk下载回调
     */
    override fun onApkDownloadResult(file: File) {
        ZXAppUtil.installApp(this, file.path)
    }

    private fun getFileSize(): Double {
        var size = 0.0
        clearFileList.forEach {
            size += ZXFileUtil.getFileOrFilesSize(it, ZXFileUtil.SIZETYPE_MB)
        }
        return size
    }

    private fun clearFile() {
        clearFileList.forEach {
            ZXFileUtil.deleteFiles(it)
        }
    }


    private fun FuncAdapter.notifyValue(name: String, value: String) {
        if (dataBeans.isNotEmpty()) {
            dataBeans.forEachIndexed { index, funcBean ->
                if (funcBean.title == name) {
                    funcBean.value = value
                    notifyItemChanged(index)
                    return@forEachIndexed
                }
            }
        }
    }
}
