package com.zx.module_library.module.web.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zx.module_library.R
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.module.web.mvp.contract.WebNormalContract
import com.zx.module_library.module.web.mvp.model.WebNormalModel
import com.zx.module_library.module.web.mvp.presenter.WebNormalPresenter
import com.zx.zxutils.util.ZXFragmentUtil
import kotlinx.android.synthetic.main.activity_web_normal.*


/**
 * Create By admin On 2017/7/11
 * 功能：通用Web加载界面
 */
@Route(path = RoutePath.ROUTE_LIBRARY_WEB)
class WebNormalActivity : BaseActivity<WebNormalPresenter, WebNormalModel>(), WebNormalContract.View {

    @JvmField
    @Autowired
    var mTitle = ""
    @JvmField
    @Autowired
    var mUrl = ""

    private lateinit var webFragment: WebFragment

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, title: String, url: String) {
            val intent = Intent(activity, WebNormalActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_web_normal
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ARouter.getInstance().inject(this)
        toolbar_view.setMidText(mTitle)

        ZXFragmentUtil.addFragment(supportFragmentManager, WebFragment.newInstance(mUrl).apply { webFragment = this }, R.id.fm_web)
    }

    override fun onBackPressed() {
        if (!webFragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }


}
