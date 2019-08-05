package com.zx.module_legalcase.module.query.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_legalcase.R
import com.zx.module_legalcase.XAppLegalcase
import com.zx.module_legalcase.api.ApiParamUtil
import com.zx.module_legalcase.module.query.func.adapter.LegalcaseListAdapter
import com.zx.module_legalcase.module.query.mvp.bean.LegalcaseListBean
import com.zx.module_legalcase.module.query.mvp.contract.QueryContract
import com.zx.module_legalcase.module.query.mvp.model.QueryModel
import com.zx.module_legalcase.module.query.mvp.presenter.QueryPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_library.bean.SearchFilterBean
import com.zx.module_library.func.tool.animateToTop
import com.zx.module_library.func.tool.getSelect
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import kotlinx.android.synthetic.main.activity_legalcase_query.*


/**
 * Create By admin On 2017/7/11
 * 功能：综合执法-主页
 */
@Route(path = RoutePath.ROUTE_LEGALCASE_TASK)
class QueryActivity : BaseActivity<QueryPresenter, QueryModel>(), QueryContract.View {

    private var pageNo = 1
    private var searchText = ""
    private var dataBeans = arrayListOf<LegalcaseListBean>()
    private var mAdapter = LegalcaseListAdapter(dataBeans)

    private val filterList = arrayListOf<SearchFilterBean>()//过滤条件
    private var domainCode = ""//案件类别
    private var process = ""//案件状态
    private var todo = "0"//待办已办
    private var searchType = "0"//搜索类型 0我的案件  1全部案件

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, QueryActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_legalcase_query
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolBar_view.withXApp(XAppLegalcase.HANDLE)
        search_view.withXApp(XAppLegalcase.HANDLE)
        tv_legalcase_tips.setTextColor(ContextCompat.getColor(this, XAppLegalcase.HANDLE.moduleColor))

        sr_legalcase_list.setLayoutManager(LinearLayoutManager(this))
                .setAdapter(mAdapter)
                .autoLoadMore()
                .setPageSize(15)
                .setSRListener(object : ZXSRListener<LegalcaseListBean> {
                    override fun onItemLongClick(item: LegalcaseListBean?, position: Int) {
                    }

                    override fun onLoadMore() {
                        pageNo++
                        loadData()
                    }

                    override fun onRefresh() {
                        loadData(true)
                    }

                    override fun onItemClick(item: LegalcaseListBean?, position: Int) {
                        DetailActivity.startAction(this@QueryActivity, false, item!!.id, item.taskId, searchType != "1" || todo != "1", item.processType)
                    }

                })
        loadData(true)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
//过滤事件
        filterList.apply {
            add(SearchFilterBean("查询对象", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                add(SearchFilterBean.ValueBean("我的案件", "0", true))
                add(SearchFilterBean.ValueBean("全部案件", "1"))
            }, false))
            add(SearchFilterBean("案件类别", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                add(SearchFilterBean.ValueBean("市场经营", "01"))
                add(SearchFilterBean.ValueBean("产品质量", "02"))
                add(SearchFilterBean.ValueBean("食品药品", "03"))
            }))
            add(SearchFilterBean("案件状态", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                add(SearchFilterBean.ValueBean("案件登记", "register"))
                add(SearchFilterBean.ValueBean("案件审核", "auditing"))
                add(SearchFilterBean.ValueBean("案件处罚", "punish"))
                add(SearchFilterBean.ValueBean("案件结案", "end"))
                add(SearchFilterBean.ValueBean("强制措施", "compel"))
                add(SearchFilterBean.ValueBean("简易流程", "simple"))
            }, visibleBy = "查询对象" to "0"))
            add(SearchFilterBean("待办已办", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                add(SearchFilterBean.ValueBean("待办", "0", true))
                add(SearchFilterBean.ValueBean("已办", "1"))
            }, false, visibleBy = "查询对象" to "0"))
        }
        search_view.setFuncListener(filterList) {
            searchType = filterList.getSelect(0)
            domainCode = filterList.getSelect(1)
            process = filterList.getSelect(2)
            todo = filterList.getSelect(3)
        }
        //搜索事件
        search_view.setSearchListener {
            searchText = it
            loadData(true)
        }
        //顶部点击滚动到开头
        toolBar_view.setMidClickListener { sr_legalcase_list.recyclerView.animateToTop(0) }
    }

    /**
     * 数据加载
     */
    private fun loadData(refresh: Boolean = false) {
        if (refresh) {
            pageNo = 1
            sr_legalcase_list.clearStatus()
        }
        if (searchType == "0") {//我的案件
            if (todo == "0") {//待办
                mPresenter.getMyCaseTodoList(ApiParamUtil.caseListParam(pageNo, 15, process, searchText, domainCode))
            } else {//已办
                mPresenter.getMyCaseAlreadyList(ApiParamUtil.caseListParam(pageNo, 15, process, searchText, domainCode))
            }
        } else {//全部案件
            mPresenter.getAllCaseList(ApiParamUtil.caseListParam(pageNo, 15, process, searchText, domainCode))
        }
    }

    override fun onCaseListResult(caseList: NormalList<LegalcaseListBean>) {
        val type = if (searchType == "1") {
            "全部案件"
        } else if (todo == "0") {
            "我的待办案件"
        } else {
            "我的已办案件"
        }
        tv_legalcase_tips.text = "检索到${type}共${caseList.total}条"
        sr_legalcase_list.refreshData(caseList.list, caseList.total)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01) {
            loadData(true)
        }
    }

}
