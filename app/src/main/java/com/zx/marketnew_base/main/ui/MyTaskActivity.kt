package com.zx.marketnew_base.main.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.R
import com.zx.marketnew_base.api.ApiParamUtil
import com.zx.marketnew_base.main.bean.TaskBean
import com.zx.marketnew_base.main.func.adapter.TaskAdapter
import com.zx.marketnew_base.main.mvp.contract.MyTaskContract
import com.zx.marketnew_base.main.mvp.model.MyTaskModel
import com.zx.marketnew_base.main.mvp.presenter.MyTaskPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_library.func.tool.UserManager
import kotlinx.android.synthetic.main.activity_my_task.*
import kotlinx.android.synthetic.main.fragment_message.*


/**
 * Create By admin On 2017/7/11
 * 功能：我的任务
 */
@Route(path = RoutePath.ROUTE_APP_MYTASK)
class MyTaskActivity : BaseActivity<MyTaskPresenter, MyTaskModel>(), MyTaskContract.View {

    var pageNo = 1
    var isRefresh = false
    var dataBeans = arrayListOf<TaskBean>()
    var listAdapter: TaskAdapter = TaskAdapter(dataBeans)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, MyTaskActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_my_task
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        rv_mytask_list.layoutManager = LinearLayoutManager(this)
        rv_mytask_list.adapter = listAdapter
        sr_mytask.setColorSchemeResources(R.color.colorPrimary)
        loadData()
    }

    /**
     * 数据加载
     */
    fun loadData(refresh: Boolean = false) {
        if (refresh) {
            isRefresh = true
            pageNo = 1
        } else {
            pageNo++
        }
        mPresenter.getTaskList(ApiParamUtil.taskListParam(UserManager.getUser().id, pageNo))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //搜索事件
        sv_mytask_search.setSearchListener {

        }
        //搜索功能键
        sv_mytask_search.setFuncListener {

        }
        //刷新事件
        sr_mytask.setOnRefreshListener {
            isRefresh = true
            loadData()
        }
        //点击事件
        listAdapter.setOnItemClickListener { adapter, view, position -> }
        //加载更多事件
        listAdapter.setOnLoadMoreListener({
            loadData()
        }, rv_message_list)
    }

    override fun onTaskListResult(taskList: NormalList<TaskBean>) {
        if (isRefresh) {
            isRefresh = false
            sr_mytask.isRefreshing = false
            dataBeans.clear()
            dataBeans.addAll(taskList.list)
            listAdapter.setNewData(dataBeans)
        } else {
            dataBeans.addAll(taskList.list)
            listAdapter.notifyDataSetChanged()
        }
        if (pageNo < taskList.pages && taskList.pages > 0) {
            listAdapter.loadMoreComplete()
        } else {
            listAdapter.loadMoreEnd()
        }
    }

}
