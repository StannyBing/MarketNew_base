package com.zx.module_entity.module.entity.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_entity.R
import com.zx.module_entity.XAppEntity
import com.zx.module_entity.api.ApiParamUtil
import com.zx.module_entity.module.entity.bean.DicTypeBean
import com.zx.module_entity.module.entity.bean.EntityLevelBean
import com.zx.module_entity.module.entity.bean.EntityStationBean
import com.zx.module_entity.module.entity.func.adapter.EntityBean
import com.zx.module_entity.module.entity.func.adapter.EntityListAdapter
import com.zx.module_entity.module.entity.mvp.contract.QueryContract
import com.zx.module_entity.module.entity.mvp.model.QueryModel
import com.zx.module_entity.module.entity.mvp.presenter.QueryPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_library.bean.SearchFilterBean
import com.zx.module_library.func.tool.animateToTop
import com.zx.module_library.func.tool.getItem
import com.zx.module_library.func.tool.getSelect
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import kotlinx.android.synthetic.main.activity_entity_query.*


/**
 * Create By admin On 2017/7/11
 * 功能：主体查询-主界面
 */
@Route(path = RoutePath.ROUTE_ENTITY_QUERY)
class QueryActivity : BaseActivity<QueryPresenter, QueryModel>(), QueryContract.View {

    private var pageNo = 1
    private var searchText = ""
    private var dataBeans = arrayListOf<EntityBean>()
    private var mAdapter = EntityListAdapter(dataBeans)

    private val filterList = arrayListOf<SearchFilterBean>()//过滤条件
    private var fStation = ""

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
        return R.layout.activity_entity_query
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolBar_view.withXApp(XAppEntity.get("主体查询"))
        search_view.withXApp(XAppEntity.get("主体查询"))
        tv_entity_tips.setTextColor(ContextCompat.getColor(this, XAppEntity.get("主体查询")!!.moduleColor))

        sr_entity_list.setLayoutManager(LinearLayoutManager(this))
                .setAdapter(mAdapter)
                .autoLoadMore()
                .setPageSize(15)
                .setSRListener(object : ZXSRListener<EntityBean> {
                    override fun onItemLongClick(item: EntityBean?, position: Int) {
                    }

                    override fun onLoadMore() {
                        pageNo++
                        loadData()
                    }

                    override fun onRefresh() {
                        loadData(true)
                    }

                    override fun onItemClick(item: EntityBean?, position: Int) {
//                        DetailActivity.startAction(this@QueryActivity, false, item!!.fGuid)
                    }

                })
        loadData(true)

        mPresenter.getFilterInfo()
    }

    /**
     * 数据加载
     */
    private fun loadData(refresh: Boolean = false) {
        if (refresh) {
            pageNo = 1
            sr_entity_list.clearStatus()
        }
        mPresenter.getEntityList(ApiParamUtil.searchParam(pageNo, 15, searchText))
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
                mPresenter.getAreaDeptList(ApiParamUtil.entityStationParam(filterList.getSelect(name = "监管局")))
            }
            fStation = filterList.getSelect(name = "监管局")
//            searchType = filterList.getSelect(0)
//            fType = filterList.getSelect(1)
//            fStatus = filterList.getSelect(2)
//            overdue = filterList.getSelect(3)
        }
        //搜索事件
        search_view.setSearchListener {
            searchText = it
            loadData(true)
        }
        //顶部点击滚动到开头
        toolBar_view.setMidClickListener { sr_entity_list.recyclerView.animateToTop(0) }
    }

    //主体标识
    override fun onTagListResult(dicTypeList: List<DicTypeBean>) {
        if (dicTypeList.isNotEmpty()) {
            filterList.add(SearchFilterBean("主体标识", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                dicTypeList.forEach {
                    add(SearchFilterBean.ValueBean(it.dicName, it.id))
                }
            }))
        }
    }

    //信用等级
    override fun onEntityLevelResult(entityLevelBeans: List<EntityLevelBean>) {
        if (entityLevelBeans.isNotEmpty()) {
            filterList.add(SearchFilterBean("信用等级", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                entityLevelBeans.forEach {
                    add(SearchFilterBean.ValueBean(it.value, it.key))
                }
            }))
        }
        filterList.add(SearchFilterBean("主体状态", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
            add(SearchFilterBean.ValueBean("存活", "存活"))
            add(SearchFilterBean.ValueBean("吊销", "吊销"))
        }))
    }

    //监管局
    override fun onDeptListResult(stationBeans: List<EntityStationBean>) {
        if (stationBeans.isNotEmpty()) {
            filterList.add(SearchFilterBean("监管局", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                stationBeans.forEach {
                    add(SearchFilterBean.ValueBean(it.value, it.id))
                }
            }))
            filterList.add(SearchFilterBean("监管片区", SearchFilterBean.FilterType.SELECT_TYPE))
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
        search_view.notifyDataSetChanged()
    }

    override fun onEntityListResult(entityList: NormalList<EntityBean>) {
        tv_entity_tips.text = "检索到主体共${entityList.total}条"
        sr_entity_list.refreshData(entityList.list, entityList.total)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01) {
            loadData(true)
        }
    }
}
