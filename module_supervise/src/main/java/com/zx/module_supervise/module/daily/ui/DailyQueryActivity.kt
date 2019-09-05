package com.zx.module_supervise.module.daily.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.BuildConfig
import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_library.bean.SearchFilterBean
import com.zx.module_library.func.tool.animateToTop
import com.zx.module_library.func.tool.getItem
import com.zx.module_library.func.tool.getPosition
import com.zx.module_library.func.tool.getSelect
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.daily.bean.DailyQueryBean
import com.zx.module_supervise.module.daily.bean.EntityStationBean
import com.zx.module_supervise.module.daily.func.adapter.DailyQueryAdapter
import com.zx.module_supervise.module.daily.mvp.contract.DailyQueryContract
import com.zx.module_supervise.module.daily.mvp.model.DailyQueryModel
import com.zx.module_supervise.module.daily.mvp.presenter.DailyQueryPresenter
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import kotlinx.android.synthetic.main.activity_daily_query.*


/**
 * Create By admin On 2017/7/11
 * 功能：现场检查
 */
@Route(path = RoutePath.ROUTE_SUPERVISE_DAILY)
class DailyQueryActivity : BaseActivity<DailyQueryPresenter, DailyQueryModel>(), DailyQueryContract.View {

    private var pageNo = 1
    private var searchText = ""
    private var dataBeans = arrayListOf<DailyQueryBean>()
    private var mAdapter = DailyQueryAdapter(dataBeans)

    private val filterList = arrayListOf<SearchFilterBean>()//过滤条件
    private var fStation = ""//分局
    private var fGrid = ""//片区

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, DailyQueryActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_daily_query
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolBar_view.withXApp(XAppSupervise.DAILY)
        search_view.withXApp(XAppSupervise.DAILY)
        tv_daily_tips.setTextColor(ContextCompat.getColor(this, XAppSupervise.DAILY.moduleColor))

        sr_daily_list.setLayoutManager(LinearLayoutManager(this))
                .setAdapter(mAdapter)
                .autoLoadMore()
                .setPageSize(15)
                .setSRListener(object : ZXSRListener<DailyQueryBean> {
                    override fun onItemLongClick(item: DailyQueryBean?, position: Int) {
                    }

                    override fun onLoadMore() {
                        pageNo++
                        loadData()
                    }

                    override fun onRefresh() {
                        loadData(true)
                    }

                    override fun onItemClick(item: DailyQueryBean?, position: Int) {
                        DailyListActivity.startAction(this@DailyQueryActivity, false, item!!.enterpriseId)
                    }
                })
        sr_daily_list.swipeRefreshLayout.setColorSchemeResources(R.color.daily_color)
        loadData(true)

        mPresenter.getDeptList(hashMapOf("parentId" to BuildConfig.AREA_ID))
    }

    private fun loadData(refresh: Boolean = false) {
        if (refresh) {
            pageNo = 1
            sr_daily_list.clearStatus()
        }
        mPresenter.getEntitys(hashMapOf("pageNo" to pageNo.toString(), "pageSize" to 15.toString(), "fStationCode" to fStation, "fGridCode" to fGrid, "enterpriseName" to searchText))
    }

    override fun onEntitysResult(entitys: NormalList<DailyQueryBean>) {
        tv_daily_tips.text = "检索到检查主体共${entitys.total}个"
        sr_daily_list.refreshData(entitys.list, entitys.total)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //过滤事件
        search_view.setFuncListener(filterList) {
            if (filterList.getSelect(name = "监管局").isEmpty() && fStation.isNotEmpty()) {
                onDeptListResult(arrayListOf())
            } else if (fStation != filterList.getSelect(name = "监管局")) {
                mPresenter.getAreaDeptList(hashMapOf("parentId" to filterList.getSelect(name = "监管局")))
            }
            fStation = filterList.getSelect(name = "监管局")
            fGrid = filterList.getSelect(name = "监管片区")
        }
        //搜索事件
        search_view.setSearchListener {
            searchText = it
            loadData(true)
        }
        //顶部点击滚动到开头
        toolBar_view.setMidClickListener { sr_daily_list.recyclerView.animateToTop(0) }
        //新增按钮
        fab_daily_add.setOnClickListener {
            DailyAddActivity.startAction(this, false)
        }
        toolBar_view.setRightClickListener {
            XApp.startXApp(RoutePath.ROUTE_STATISTICS_INFO) {
                it["xApp"] = XAppSupervise.DAILY
            }
        }

    }

    //监管片区
    override fun onAreaDeptListResult(deptBeans: List<EntityStationBean>) {
        filterList.getItem("监管片区")?.values?.apply {
            clear()
            if (deptBeans.isNotEmpty()) {
                deptBeans.forEach {
                    add(SearchFilterBean.ValueBean(it.value, it.id))
                }
            }
        }
        search_view.notifyItemChanged(filterList.getPosition("监管片区"))
    }

    //监管局
    override fun onDeptListResult(stationBeans: List<EntityStationBean>) {
        if (stationBeans.isNotEmpty()) {
            filterList.add(SearchFilterBean("监管局", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                stationBeans.forEach {
                    add(SearchFilterBean.ValueBean(it.value, it.id))
                }
            }, singleFunc = true))
            filterList.add(SearchFilterBean("监管片区", SearchFilterBean.FilterType.SELECT_TYPE))
        } else {
            filterList.getItem("监管片区")?.values?.clear()
        }
        search_view.notifyItemChanged(filterList.getPosition("监管片区"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01) {
            loadData(true)
        }
    }
}
