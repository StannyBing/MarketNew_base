package com.zx.module_other.module.infomation.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.module.infomation.mvp.contract.InfomationContract
import com.zx.module_other.module.infomation.mvp.model.InfomationModel
import com.zx.module_other.module.infomation.mvp.presenter.InfomationPresenter
import kotlinx.android.synthetic.main.activity_infomation.*



/**
 * Create By admin On 2017/7/11
 * 功能：
 */
@Route(path = RoutePath.ROUTE_OTHER_INFOMATION)
class InfomationActivity : BaseActivity<InfomationPresenter, InfomationModel>(), InfomationContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, InfomationActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_infomation
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        wv_news.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
//            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            webViewClient = WebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
                    return true
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)

                }
            }
        }
        wv_news.loadUrl("https://portal.3g.qq.com/")
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
