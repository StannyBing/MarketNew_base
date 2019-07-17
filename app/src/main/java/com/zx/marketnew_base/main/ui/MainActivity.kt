package com.zx.marketnew_base.main.ui;

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.tencent.bugly.crashreport.CrashReport
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.VersionBean
import com.zx.marketnew_base.main.mvp.contract.MainContract
import com.zx.marketnew_base.main.mvp.model.MainModel
import com.zx.marketnew_base.main.mvp.presenter.MainPresenter
import com.zx.module_library.BuildConfig
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.UserManager
import com.zx.zxutils.util.ZXAppUtil
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.views.TabViewPager.ZXTabViewPager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：主界面
 */
@Route(path = RoutePath.ROUTE_APP_MAIN)
class MainActivity : BaseActivity<MainPresenter, MainModel>(), MainContract.View {

    override var canSwipeBack: Boolean = false

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        tvp_main.setManager(supportFragmentManager)
                .setTabScrollable(false)
                .setViewpagerCanScroll(false)
                .setTabLayoutGravity(ZXTabViewPager.TabGravity.GRAVITY_BOTTOM)
                .addTab(MessageFragment.newInstance(), "消息", R.drawable.selector_tab_message)
                .addTab(WorkFragment.newInstance(), "办公", R.drawable.selector_tab_work)
                .addTab(MailListFragment.newInstance(), "通讯录", R.drawable.selector_tab_maillist)
                .addTab(UserFragment.newInstance(), "我的", R.drawable.selector_tab_user)
                .setTitleColor(ContextCompat.getColor(this, R.color.text_color_noraml), ContextCompat.getColor(this, R.color.colorPrimary))
                .setIndicatorHeight(0)
                .setTablayoutHeight(60)
                .setTabTextSize(12, 12)
                .setTablayoutBackgroundColor(ContextCompat.getColor(this, R.color.white))
                .showDivider(ContextCompat.getColor(this, R.color.text_color_light))
                .setTabImageSize(26)
                .build()

        if (BuildConfig.isRelease) {
            CrashReport.setUserId(UserManager.getUser().id)
        }

        JPushInterface.resumePush(this)
        JPushInterface.setAlias(this, 0, UserManager.getUser().id)
        JPushInterface.setTags(this, 0, setOf(if (BaseConfigModule.ISRELEASE) "regular" else "test"))

        mPresenter.getVerson()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

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
     * 未读消息锁链
     */
    override fun onCountUnreadResult(num: Int) {
        tvp_main.setTabTitleNum(0, num)
    }

    /**
     * apk下载回调
     */
    override fun onApkDownloadResult(file: File) {
        ZXAppUtil.installApp(this, file.path)
    }

    override fun onBackPressed() {
        startActivity(Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_HOME)
        })
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getUnread()
    }

}
