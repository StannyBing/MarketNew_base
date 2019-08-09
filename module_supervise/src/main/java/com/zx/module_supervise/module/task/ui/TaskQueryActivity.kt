package com.zx.module_supervise.module.task.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_library.bean.SearchFilterBean
import com.zx.module_library.func.tool.UserManager
import com.zx.module_library.func.tool.animateToTop
import com.zx.module_library.func.tool.getSelect
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.task.bean.TaskListBean
import com.zx.module_supervise.module.task.func.adapter.TaskListAdapter
import com.zx.module_supervise.module.task.mvp.contract.TaskQueryContract
import com.zx.module_supervise.module.task.mvp.model.TaskQueryModel
import com.zx.module_supervise.module.task.mvp.presenter.TaskQueryPresenter
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import kotlinx.android.synthetic.main.activity_supervise_query.*


/**
 * Create By admin On 2017/7/11
 * 功能：专项检查
 */
@Route(path = RoutePath.ROUTE_SUPERVISE_TASK)
class TaskQueryActivity : BaseActivity<TaskQueryPresenter, TaskQueryModel>(), TaskQueryContract.View {

    private var pageNo = 1
    private var searchText = ""
    private var dataBeans = arrayListOf<TaskListBean>()
    private var mAdapter = TaskListAdapter(dataBeans)

    private var fStatus = ""//办理状态
    private var queryType = "todo"//搜索类型
    private var searchType = "0"//搜索类型 0我的任务  1全部任务

    private val filterList = arrayListOf<SearchFilterBean>()//过滤条件

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, TaskQueryActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_supervise_query
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolBar_view.withXApp(XAppSupervise.SUPERVISE)
        search_view.withXApp(XAppSupervise.SUPERVISE)
        tv_supervise_tips.setTextColor(ContextCompat.getColor(this, XAppSupervise.SUPERVISE.moduleColor))

        sr_supervise_list.setLayoutManager(LinearLayoutManager(this))
                .setAdapter(mAdapter)
                .autoLoadMore()
                .setPageSize(15)
                .setSRListener(object : ZXSRListener<TaskListBean> {
                    override fun onItemLongClick(item: TaskListBean?, position: Int) {
                    }

                    override fun onLoadMore() {
                        pageNo++
                        loadData()
                    }

                    override fun onRefresh() {
                        loadData(true)
                    }

                    override fun onItemClick(item: TaskListBean?, position: Int) {
                        TaskDetailActivity.startAction(this@TaskQueryActivity, false, item!!.fId, item.fTaskId)
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
            sr_supervise_list.clearStatus()
        }
        mPresenter.getSuperviseList(hashMapOf("pageNo" to pageNo.toString(), "pageSize" to 15.toString(), "fUserId" to UserManager.getUser().id,
                "fStatus" to fStatus, "queryType" to queryType, "condition" to searchText, "orderByClause" to "f_start_date desc"))
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
            add(SearchFilterBean("处置状态", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                add(SearchFilterBean.ValueBean("待处置", "101"))
                add(SearchFilterBean.ValueBean("待初审", "102"))
                add(SearchFilterBean.ValueBean("待核审", "103"))
                add(SearchFilterBean.ValueBean("待终审", "104"))
            }))
            add(SearchFilterBean("待办已办", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                add(SearchFilterBean.ValueBean("待办", "0", true))
                add(SearchFilterBean.ValueBean("已办", "1"))
            }, false, visibleBy = "查询对象" to "0"))
            add(SearchFilterBean("是否逾期", SearchFilterBean.FilterType.SELECT_TYPE, arrayListOf<SearchFilterBean.ValueBean>().apply {
                add(SearchFilterBean.ValueBean("即将到期", "1"))
                add(SearchFilterBean.ValueBean("逾期", "0"))
            }, visibleBy = "查询对象" to "0"))
        }
        search_view.setFuncListener(filterList) {
            searchType = filterList.getSelect(0)
            fStatus = filterList.getSelect(1)
            queryType = if (searchType == "1") {//全部
                "all"
            } else {
                if (filterList.getSelect(2) == "1") {//已办
                    "finish"
                } else if (filterList.getSelect(3) == "1") {//即将到期
                    "soon"
                } else if (filterList.getSelect(3) == "0") {//逾期
                    "overdue"
                } else {
                    "todo"
                }
            }
        }
        //搜索事件
        search_view.setSearchListener {
            searchText = it
            loadData(true)
        }
        //顶部点击滚动到开头
        toolBar_view.setMidClickListener { sr_supervise_list.recyclerView.animateToTop(0) }
    }

    override fun onSuperviseListResult(taskList: NormalList<TaskListBean>) {
        val type = if (searchType == "0") {
            "我的任务"
        } else {
            "全部任务"
        }
        tv_supervise_tips.text = "检索到${type}共${taskList.total}条"
        sr_supervise_list.refreshData(taskList.list, taskList.total)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01) {
            loadData(true)
        }
    }

}
