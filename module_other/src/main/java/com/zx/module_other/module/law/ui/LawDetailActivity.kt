package com.zx.module_other.module.law.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.law.bean.LawDetailBean
import com.zx.module_other.module.law.mvp.contract.LawDetailContract
import com.zx.module_other.module.law.mvp.model.LawDetailModel
import com.zx.module_other.module.law.mvp.presenter.LawDetailPresenter
import kotlinx.android.synthetic.main.activity_law_detail.*


/**
 * Create By admin On 2017/7/11
 * 功能：详情
 */
class LawDetailActivity : BaseActivity<LawDetailPresenter, LawDetailModel>(), LawDetailContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, id: String) {
            val intent = Intent(activity, LawDetailActivity::class.java)
            intent.putExtra("id", id)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_law_detail
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolbar_view.withXApp(XAppOther.get("法律法规"))

        wv_law_query.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
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
        mPresenter.getLawDetail(ApiParamUtil.lawDetailParam(intent.getStringExtra("id")))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    override fun onLawDetailResult(lawDetail: LawDetailBean) {
        setHtml(lawDetail.content)
    }

    /**
     * 设置需展示的Html片段
     */
    private fun setHtml(protocol: String?) {
        if (protocol != null) {
            wv_law_query.getSettings().setDefaultTextEncodingName("utf-8")
            wv_law_query.loadDataWithBaseURL("", protocol, "text/html; charset=UTF-8", "UTF-8", null)
        }
    }
}
