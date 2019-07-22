package com.zx.module_entity.module.entity.ui

import android.Manifest
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
import com.zx.module_entity.module.entity.bean.EntityBean
import com.zx.module_entity.module.entity.bean.EntityLevelBean
import com.zx.module_entity.module.entity.bean.EntityStationBean
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
import com.zx.module_library.func.tool.getPosition
import com.zx.module_library.func.tool.getSelect
import com.zx.zxutils.util.ZXLocationUtil
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
    private var fTags = ""//主体标识
    private var fCreditLevel = ""//主体等级
    private var fStatus = ""//主体状态
    private var areaCode = ""//片区
    private var radius = ""//范围
    private var positionList = ""//坐标

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
                        DetailActivity.startAction(this@QueryActivity, false, item!!.fEntityGuid!!, item.fTags?.contains("特殊主体")
                                ?: false)
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
        mPresenter.getEntityList(ApiParamUtil.searchParam(pageNo, 15, searchText, radius = radius, positionList = positionList, fTags = fTags, fCreditLevel = fCreditLevel, fStatus = fStatus, areaCode = areaCode))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //过滤事件
        search_view.setFuncListener(filterList) {
            if (filterList.getSelect(name = "监管局").isEmpty() && areaCode.isNotEmpty()) {
                onDeptListResult(arrayListOf())
            } else if (areaCode != filterList.getSelect(name = "监管局")) {
                mPresenter.getAreaDeptList(ApiParamUtil.entityStationParam(filterList.getSelect(name = "监管局")))
            }
            fTags = filterList.getSelect(name = "主体标识")
            fCreditLevel = filterList.getSelect(name = "信用等级")
            fStatus = filterList.getSelect(name = "主体状态")
            areaCode = filterList.getSelect(name = "监管所")
            if (filterList.getSelect(name = "周边查询") == "1"&&filterList.getSelect(name = "查询范围").isNotEmpty()) {
                getPermission(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    val location = ZXLocationUtil.getLocation(this)
                    positionList = "[[${location.longitude},${location.latitude}]]"
                }
                radius = filterList.getSelect(name = "查询范围")
            }else{
                positionList = ""
                radius = ""
            }
            if (filterList.getSelect(name = "监管片区").isNotEmpty()) areaCode = filterList.getSelect(name = "监管片区")
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
                add(SearchFilterBean.ValueBean("特殊主体", "特殊主体"))
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
            }, singleFunc = true))
            filterList.add(SearchFilterBean("监管片区", SearchFilterBean.FilterType.SELECT_TYPE))
        } else {
            filterList.getItem("监管片区")?.values?.clear()
        }

        filterList.add(SearchFilterBean("周边查询", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
            add(SearchFilterBean.ValueBean("否", "0", true))
            add(SearchFilterBean.ValueBean("是", "1"))
        }, addDefalut = false))
        filterList.add(SearchFilterBean("查询范围", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
            add(SearchFilterBean.ValueBean("0.5公里", "0.5"))
            add(SearchFilterBean.ValueBean("1公里", "1"))
            add(SearchFilterBean.ValueBean("2公里", "2"))
            add(SearchFilterBean.ValueBean("3公里", "3"))
            add(SearchFilterBean.ValueBean("5公里", "5"))
            add(SearchFilterBean.ValueBean("10公里", "10"))
        }, visibleBy = "周边查询" to "1", isEnable = false))
        search_view.notifyItemChanged(filterList.getPosition("监管片区"))
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
