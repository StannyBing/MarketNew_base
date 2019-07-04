package com.zx.module_other.module.law.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.*
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.UserManager
import com.zx.module_other.R
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.law.bean.*
import com.zx.module_other.module.law.func.adapter.LawQueryListAdapter
import com.zx.module_other.module.law.mvp.contract.LawQueryContract
import com.zx.module_other.module.law.mvp.model.LawQueryModel
import com.zx.module_other.module.law.mvp.presenter.LawQueryPresenter
import kotlinx.android.synthetic.main.activity_law_query.*

class LawQueryActivity : BaseActivity<LawQueryPresenter, LawQueryModel>(), LawQueryContract.View {


    private var lawMainBean: LawMainBean? = null
    private var sortDatas = arrayListOf<LawBean>()
    private var sortListAdapter = LawQueryListAdapter<LawBean>(sortDatas)
    private var keywordDatas = arrayListOf<LawSearchBean>()
    private var keywordListAdapter = LawQueryListAdapter<LawSearchBean>(keywordDatas)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, lawMainBean: LawMainBean) {
            val intent = Intent(activity, LawQueryActivity::class.java)
            intent.putExtra("p0", lawMainBean)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        if (intent != null) {
            lawMainBean = intent.getSerializableExtra("p0") as LawMainBean
        }
        queryPostMethod()
        groupLawQuery.visibility = if (lawMainBean!!.type == 2) View.VISIBLE else View.GONE
        rvLawQuery.visibility = if (lawMainBean!!.type != 2) View.VISIBLE else View.GONE
        initWebView()
        rvLawQuery.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        svLawSearch.setSearchListener {
            lawMainBean = LawMainBean(it, 4, 0)
            groupLawQuery.visibility = if (lawMainBean!!.type == 2) View.VISIBLE else View.GONE
            rvLawQuery.visibility = if (lawMainBean!!.type != 2) View.VISIBLE else View.GONE
            queryPostMethod()
        }
    }

    /**
     * 请求方法
     */
    private fun queryPostMethod() {
        if (lawMainBean!!.type == 0) {
            mPresenter.getSearchLaw(ApiParamUtil.lawSearchParam(lawMainBean!!.name))
            svLawSearch.setSearchText(lawMainBean!!.name)
        } else if (lawMainBean!!.type == 1) {
            val user = UserManager.getUser()
            if (user != null) {
                mPresenter.getLawList(ApiParamUtil.lawSelectParam(user.departmentCode, user.id))
            }
        } else if (lawMainBean!!.type == 2) {
            mPresenter.getLawDetail(ApiParamUtil.lawDetailParam(lawMainBean!!.id.toString()))
        }
    }

    private fun initWebView() {
        wvLawQuery.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            webViewClient =WebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
                    return true
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)

                }
            }
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        keywordListAdapter.setOnItemClickListener { adapter, view, position ->
            if (position != 0) {
                val keywordData = keywordDatas.get(position)
                lawMainBean = LawMainBean(keywordData.name,keywordData.id,2)
                startAction(this, false, lawMainBean!!)
            }
        }

        sortListAdapter.setOnItemClickListener { adapter, view, position ->
            if (position != 0) {
                val sortData = sortDatas.get(position)
                lawMainBean = LawMainBean(sortData.name,sortData.id,2)
                startAction(this, false, lawMainBean!!)
            }
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_law_query
    }

    /**
     * 设置需展示的Html片段
     */
    private fun setHtml(protocol: String?) {
        if (protocol != null) {
            wvLawQuery.getSettings().setDefaultTextEncodingName("utf-8")
            wvLawQuery.loadDataWithBaseURL("",protocol, "text/html; charset=UTF-8", "UTF-8", null)
        }
    }

    override fun onLawDetailResult(lawDetail: LawDetailBean) {
        setHtml(lawDetail.content)
    }

    override fun onLawListResult(complainList: List<LawBean>) {
        if (complainList != null) {
            val childBean = complainList.get(lawMainBean!!.id)
            val lawBean = childBean.children
            if (lawBean != null && lawBean.size > 0) {
                val bean = LawBean(itemTypeDef = 1, itemName = String.format("共%d条", lawBean.size))
                sortDatas.add(bean)
                lawBean.forEach {
                    it.itemTypeDef = 2
                    it.itemName = String.format("所属：%s", childBean.name)
                    sortDatas.add(it)
                }
            }
            sortListAdapter.setNewData(sortDatas)
            rvLawQuery.adapter = sortListAdapter
        }
    }

    override fun onSearchLawResult(lawSearchLawResult: LawSearchResultBean) {
        if (lawSearchLawResult != null) {
            val list = lawSearchLawResult.list
            removeKeywordData()
            if (list != null && list.size > 0) {
                val bean = LawSearchBean(itemTypeDef = 1, itemName = String.format("共%d条", lawSearchLawResult.size))
                keywordDatas.add(bean)
                list.forEach {
                    it.itemTypeDef = 2
                    keywordDatas.add(it)
                }
            }
            keywordListAdapter.setNewData(keywordDatas)
            rvLawQuery.adapter = keywordListAdapter
        }
    }

    /**
     * 删除搜索数据
     */
    fun removeKeywordData(){
        if (keywordDatas!=null&&keywordDatas.size>0){
            keywordDatas.clear()
        }
    }
}