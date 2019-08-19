package com.zx.marketnew_base.system.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.FuncBean
import com.zx.marketnew_base.main.func.adapter.FuncAdapter
import com.zx.marketnew_base.system.mvp.contract.SettingContract
import com.zx.marketnew_base.system.mvp.model.SettingModel
import com.zx.marketnew_base.system.mvp.presenter.SettingPresenter
import com.zx.module_library.app.ConstStrings
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.UserManager
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

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, SettingActivity::class.java)
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
            layoutManager = LinearLayoutManager(this@SettingActivity) as RecyclerView.LayoutManager?
            adapter = listAdapter
        }

        if (mSharedPrefUtil.getBool("openDevelop", false)) {
            dataBeans.add(FuncBean("开发者模式", R.drawable.app_func_develop, true))
        }
        dataBeans.add(FuncBean("录像设置", R.drawable.app_func_video))
        dataBeans.add(FuncBean("字体设置", R.drawable.app_func_font))
        dataBeans.add(FuncBean("清理缓存", R.drawable.app_func_clear, true))
        dataBeans.add(FuncBean("意见反馈", R.drawable.app_func_feedback))
        dataBeans.add(FuncBean("关于我们", R.drawable.app_func_about))
        dataBeans.add(FuncBean("检查更新", R.drawable.app_func_version))
        dataBeans.add(FuncBean("修改密码", R.drawable.app_func_law, true))
        dataBeans.add(FuncBean("退出登录", R.drawable.app_func_logout))

        getPermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            listAdapter.notifyValue("清理缓存", if (getFileSize().toString().isNotEmpty() && getFileSize().toString().length > 4) {
                getFileSize().toString().substring(0, 4)
            } else {
                getFileSize().toString()
            } + "M")
        }
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
                "字体设置" -> {
                    FontSettingActivity.startAction(this, false)
                }
                "清理缓存" -> {
                    showLoading("正在清理中...")
                    clearFile()
                    Thread {
                        Glide.get(this).clearDiskCache()
                    }.start()
                    Glide.get(this).clearMemory()
                    handler.postDelayed({
                        listAdapter.notifyValue("清理缓存", if (getFileSize().toString().isNotEmpty() && getFileSize().toString().length > 4) {
                            getFileSize().toString().substring(0, 4)
                        } else {
                            getFileSize().toString()
                        } + "M")
                        dismissLoading()
                        showToast("清理完成")
                    }, 1000)
                }
                "检查更新" -> {
                    VersionUpdateActivity.startAction(this, false)
                }
                "意见反馈" -> {
                    FeedBackActivity.startAction(this, false)
                }
                "关于我们" -> {
                    AboutActivity.startAction(this, false)
                }
                "修改密码" -> {
                    ChangePwdActivity.startAction(this, false, UserManager.getUser().id)
                }
                "退出登录" -> {
                    ZXDialogUtil.showYesNoDialog(this, "提示", "是否退出登录？") { dialog, which ->
                        UserManager.loginOut()
                        JPushInterface.stopPush(this)
                        LoginActivity.startAction(this, false)
                    }
                }
                "开发者模式" -> {
                    DevelopActivity.startAction(this, false)
                }
                else -> {
                    showToast("正在开发中")
                }
            }
        }
        //开发者模式
        toolbar_view.setMidClickListener {
            if (!mSharedPrefUtil.getBool("openDevelop", false)) {
                if (System.currentTimeMillis() - developTime < 1000 || developTime == 0L) {
                    developCount++
                    developTime = System.currentTimeMillis()
                    if (developCount == 5) {
                        dataBeans.add(0, FuncBean("开发者模式", R.drawable.app_func_develop, true))
                        listAdapter.notifyDataSetChanged()
                        mSharedPrefUtil.putBool("openDevelop", true)
                        showToast("已开启开发者模式！")
                        developCount = 0
                        developTime = 0
                    }
                } else {
                    developCount = 0
                    developTime = 0
                }
            }
        }
    }

    var developCount = 0
    var developTime = 0L

    private fun getFileSize(): Double {
        var size = 0.0
        size += ZXFileUtil.getFileOrFilesSize(ConstStrings.getLocalPath(), ZXFileUtil.SIZETYPE_MB)
        return size
    }

    private fun clearFile() {
        val localFile = File(ConstStrings.getLocalPath())
        if (localFile.isDirectory) {
            localFile.listFiles().forEach {
                ZXFileUtil.deleteFiles(it)
            }
        }else{
            ZXFileUtil.deleteFiles(localFile)
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

    override fun onResume() {
        super.onResume()
        if (!mSharedPrefUtil.getBool("openDevelop", false) && dataBeans[0].title == "开发者模式") {
            dataBeans.removeAt(0)
            listAdapter.notifyDataSetChanged()
        }
    }
}
