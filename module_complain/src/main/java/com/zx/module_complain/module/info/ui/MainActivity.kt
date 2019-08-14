package com.zx.module_complain.module.info.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_complain.R
import com.zx.module_complain.XAppComplain
import com.zx.module_complain.api.ApiParamUtil
import com.zx.module_complain.module.info.bean.ComplainListBean
import com.zx.module_complain.module.info.func.adapter.ComplainListAdapter
import com.zx.module_complain.module.info.mvp.contract.MainContract
import com.zx.module_complain.module.info.mvp.model.MainModel
import com.zx.module_complain.module.info.mvp.presenter.MainPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_library.bean.SearchFilterBean
import com.zx.module_library.func.tool.animateToTop
import com.zx.module_library.func.tool.getSelect
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import kotlinx.android.synthetic.main.activity_complain_list.*


/**
 * Create By admin On 2017/7/11
 * 功能：投诉举报列表
 */
@Route(path = RoutePath.ROUTE_COMPLAIN_TASK)
class MainActivity : BaseActivity<MainPresenter, MainModel>(), MainContract.View {

    private var pageNo = 1
    private var searchText = ""
    private var dataBeans = arrayListOf<ComplainListBean>()
    private var mAdapter = ComplainListAdapter(dataBeans)

    private val filterList = arrayListOf<SearchFilterBean>()//过滤条件
    private var fType = ""//投诉类别
    private var fStatus = ""//办理状态
    private var overdue = ""//逾期状态
    private var searchType = "0"//搜索类型 0我的任务  1全部任务

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
        return R.layout.activity_complain_list
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolBar_view.withXApp(XAppComplain.LIST)
        search_view.withXApp(XAppComplain.LIST)
        tv_complain_tips.setTextColor(ContextCompat.getColor(this, XAppComplain.LIST.moduleColor))

        sr_complain_list.setLayoutManager(LinearLayoutManager(this))
                .setAdapter(mAdapter)
                .autoLoadMore()
                .setPageSize(15)
                .setSRListener(object : ZXSRListener<ComplainListBean> {
                    override fun onItemLongClick(item: ComplainListBean?, position: Int) {
                    }

                    override fun onLoadMore() {
                        pageNo++
                        loadData()
                    }

                    override fun onRefresh() {
                        loadData(true)
                    }

                    override fun onItemClick(item: ComplainListBean?, position: Int) {
                        DetailActivity.startAction(this@MainActivity, false, item!!.fGuid)
                    }

                })
        loadData(true)

    }

    /**
     * 数据加载
     */
    private fun loadData(refresh: Boolean = false) {
        if (refresh) {
            pageNo = 1
            sr_complain_list.clearStatus()
        }
        if (searchType == "0") {
            mPresenter.getMyComplainList(ApiParamUtil.complainListParam(pageNo, 15, searchText, fType, fStatus, overdue))
        } else if (searchType == "1") {
            mPresenter.getAllComplainList(ApiParamUtil.complainListParam(pageNo, 15, searchText, fType, fStatus, overdue))
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //过滤事件
        filterList.apply {
            add(SearchFilterBean("查询对象", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                add(SearchFilterBean.ValueBean("我的任务", "0", true))
                add(SearchFilterBean.ValueBean("全部任务", "1"))
            }, false))
            add(SearchFilterBean("投诉类别", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                add(SearchFilterBean.ValueBean("投诉", "投诉"))
                add(SearchFilterBean.ValueBean("举报", "举报"))
                add(SearchFilterBean.ValueBean("咨询", "咨询"))
            }))
            add(SearchFilterBean("处置状态", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                add(SearchFilterBean.ValueBean("待受理", "10"))
                add(SearchFilterBean.ValueBean("待分流", "20"))
                add(SearchFilterBean.ValueBean("待指派", "30"))
                add(SearchFilterBean.ValueBean("待联系", "40"))
                add(SearchFilterBean.ValueBean("待处置", "50"))
                add(SearchFilterBean.ValueBean("待初审", "60"))
                add(SearchFilterBean.ValueBean("待终审", "70"))
                add(SearchFilterBean.ValueBean("待办结", "80"))
                add(SearchFilterBean.ValueBean("已办结", "90"))
            }))
            add(SearchFilterBean("是否逾期", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                add(SearchFilterBean.ValueBean("逾期", "逾期"))
                add(SearchFilterBean.ValueBean("即将到期", "即将到期"))
            }))
        }
        search_view.setFuncListener(filterList) {
            searchType = filterList.getSelect(0)
            fType = filterList.getSelect(1)
            fStatus = filterList.getSelect(2)
            overdue = filterList.getSelect(3)
        }
        //搜索事件
        search_view.setSearchListener {
            searchText = it
            loadData(true)
        }
        //顶部点击滚动到开头
        toolBar_view.setMidClickListener { sr_complain_list.recyclerView.animateToTop(0)}
    }

    override fun onComplainListResult(complainList: NormalList<ComplainListBean>) {
        val type = if (searchType == "0") {
            "我的任务"
        } else {
            "全部任务"
        }
        tv_complain_tips.text = "检索到${type}共${complainList.total}条"
        sr_complain_list.refreshData(complainList.list, complainList.total)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01) {
            loadData(true)
        }
    }

}
