package com.zx.module_other.module.law.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.webkit.*
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.law.bean.LawCollectBean
import com.zx.module_other.module.law.bean.LawCollectResultBean
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

    private var lawDetail: LawDetailBean? = null
    private var isCollect: Boolean = false
    private var lawCollectId: String? = null

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

        toolbar_view.withXApp(XAppOther.LAW)
        mPresenter.getCollectList(ApiParamUtil.lawMyCollectAllParam("oynkBwtUWJ2tFcS5s19RofvkfTs8"))
        wv_law_query.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
//            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            //settings.loadWithOverviewMode = true
            //settings.useWideViewPort = true
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
        tv_law_collect.setOnClickListener {
            if (isCollect) {
                mPresenter.DeleteWeixinCollectLaw(ApiParamUtil.lawDeleteCollectParam(lawCollectId!!))
            } else {
                mPresenter.AddWeixinCollectLaw(ApiParamUtil.lawAddCollectParam(lawDetail!!.id.toString(), "oynkBwtUWJ2tFcS5s19RofvkfTs8", lawDetail!!.name, lawDetail!!.type))
            }
        }
    }

    override fun onLawDetailResult(lawDetail: LawDetailBean) {
        this.lawDetail = lawDetail;
        setHtml(lawDetail.content)
    }

    override fun onLawAddCollect(collect: String) {
        lawCollectId = collect;
        setCollectPic(R.drawable.collect_select);
    }

    override fun onLawDeleteCollect(data: Int) {
        lawCollectId = null
        setCollectPic(R.drawable.collect_normal_gray)
    }

    override fun onLawCollectResult(lawCollectResultBean: LawCollectResultBean?) {
        val list = lawCollectResultBean!!.list
        for (lawCollect in list!!) {
            if (intent.getStringExtra("id").equals(lawCollect.lawMenuId)) {
                setCollectPic(R.drawable.collect_select);
                lawCollectId = lawCollect.id;
                isCollect = true
                break
            }
        }
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

    private fun setCollectPic(resourceId: Int) {
        tv_law_collect.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, resourceId), null, null, null);
    }

}
