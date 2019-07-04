package com.zx.module_other.module.web.ui

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.module.web.mvp.contract.WebNormalContract
import com.zx.module_other.module.web.mvp.model.WebNormalModel
import com.zx.module_other.module.web.mvp.presenter.WebNormalPresenter
import kotlinx.android.synthetic.main.activity_web_normal.*


/**
 * Create By admin On 2017/7/11
 * 功能：通用Web加载界面
 */
@Route(path = RoutePath.ROUTE_OTHER_WEB)
class WebNormalActivity : BaseActivity<WebNormalPresenter, WebNormalModel>(), WebNormalContract.View {

    @JvmField
    @Autowired var mTitle = ""
    @JvmField
    @Autowired var mUrl = ""

    private var fileCallback: ValueCallback<Array<Uri>>? = null

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
        web_app_normal.apply {
            settings.javaScriptEnabled = true
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            webViewClient = WebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
                    fileCallback = filePathCallback
                    val i = Intent(Intent.ACTION_GET_CONTENT)
                    i.addCategory(Intent.CATEGORY_OPENABLE)
                    i.type = "image/*"
                    startActivityForResult(Intent.createChooser(i, "Image Chooser"), 0x01)
                    return true
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    pb_app_loading.progress = newProgress
                    pb_app_loading.solidColor
                    if (pb_app_loading.progress == 0 || pb_app_loading.progress == 100) {
                        pb_app_loading.visibility = View.GONE
                    } else {
                        pb_app_loading.visibility = View.VISIBLE
                    }
                }
            }
            loadUrl(mUrl)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0x01) {
            if (null == fileCallback) return
            val result = if (data == null || resultCode != RESULT_OK) null else data.data
            onActivityResultAboveL(requestCode, resultCode, data)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onActivityResultAboveL(requestCode: Int, resultCode: Int, intent: Intent?) {
        var results = arrayListOf<Uri>()
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                val dataString = intent.dataString
                val clipData = intent.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val item = clipData.getItemAt(i)
                        results.add(item.uri)
                    }
                }
                if (dataString != null)
                    results = arrayListOf(Uri.parse(dataString))
            }
        }
        fileCallback!!.onReceiveValue(results.toArray() as Array<Uri>?)
        fileCallback = null
    }

    override fun onBackPressed() {
        if (web_app_normal.canGoBack()) {
            web_app_normal.goBack()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    override fun onPause() {
        super.onPause()
        web_app_normal.onPause()
    }

    override fun onResume() {
        super.onResume()
        web_app_normal.onResume()
    }

}
