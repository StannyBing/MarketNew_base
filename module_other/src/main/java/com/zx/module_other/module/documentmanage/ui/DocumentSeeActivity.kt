package com.zx.module_other.module.workplan.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.webkit.*
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiConfigModule
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.documentmanage.bean.Children
import com.zx.module_other.module.workplan.mvp.contract.DocumentSeeContract
import com.zx.module_other.module.workplan.mvp.model.DocumentSeeModel
import com.zx.module_other.module.workplan.mvp.presenter.DocumentSeePresenter
import kotlinx.android.synthetic.main.activity_document_see.*

class DocumentSeeActivity : BaseActivity<DocumentSeePresenter, DocumentSeeModel>(), DocumentSeeContract.View {

    companion object {
        val TYPE_FILL = 0
        val TYPE_CHANGE = 1
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, children: Children, type: Int, printUrl: String) {
            val intent = Intent(activity, DocumentSeeActivity::class.java)
            intent.putExtra("children", children)
            intent.putExtra("type", type)
            intent.putExtra("printUrl", printUrl)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewListener() {
        tv_fill_document.setOnClickListener {
            DocumentFillActivity.startAction(this, false, intent.getSerializableExtra("children") as Children)
        }
        tv_print_document.setOnClickListener {
            //            getSystemService(Context.PRINT_SERVICE).apply {
//                print("",wv_documentsee.createPrintDocumentAdapter(""),null)
//            }

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_document_see
    }

    @SuppressLint("ResourceAsColor")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toobar_view.withXApp(XAppOther.get("文书"))
        wv_documentsee.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
           // settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
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
        when (intent.getIntExtra("type", TYPE_FILL)) {
            TYPE_FILL -> {
                toobar_view.setMidText(resources.getString(R.string.fill_document))
                mPresenter.getDocumentWeb(ApiParamUtil.getDocumentMoldeParam((intent.getSerializableExtra("children") as Children).id))
            }
            TYPE_CHANGE -> {
                toobar_view.setMidText(resources.getString(R.string.goon_fill))
            }
        }
    }

    override fun getDocumentWebSeeResult(weburl: String) {
        setHtml(weburl)
    }

    /**
     * 设置需展示的Html片段
     */
    private fun setHtml(protocol: String?) {
        if (protocol != null) {
            wv_documentsee.getSettings().setDefaultTextEncodingName("utf-8")
            wv_documentsee.loadDataWithBaseURL("", protocol, "text/html; charset=UTF-8", "UTF-8", null)
        }
    }
}