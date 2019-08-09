package com.zx.marketnew_base.main.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.TaskBean
import com.zx.marketnew_base.main.func.adapter.TaskAdapter
import com.zx.marketnew_base.main.mvp.contract.MyTaskContract
import com.zx.marketnew_base.main.mvp.model.MyTaskModel
import com.zx.marketnew_base.main.mvp.presenter.MyTaskPresenter
import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import kotlinx.android.synthetic.main.activity_my_task.*


/**
 * Create By admin On 2017/7/11
 * 功能：我的任务
 */
@Route(path = RoutePath.ROUTE_APP_MYTASK)
class MyTaskActivity : BaseActivity<MyTaskPresenter, MyTaskModel>(), MyTaskContract.View {

    private val dataBeans = arrayListOf<TaskBean>()
    private val listAdapter = TaskAdapter(dataBeans)

    private var searchText = ""

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

        sr_my_task.setLayoutManager(LinearLayoutManager(this))
                .setAdapter(listAdapter)
                .setPageSize(999)
                .setSRListener(object : ZXSRListener<TaskBean> {
                    override fun onItemLongClick(item: TaskBean?, position: Int) {
                    }

                    override fun onLoadMore() {
//                        loadData()
                    }

                    override fun onRefresh() {
                        loadData(true)
                    }

                    override fun onItemClick(item: TaskBean?, position: Int) {
                        when (item!!.businessType) {
                            "case" -> {
                                XApp.startXApp(RoutePath.ROUTE_LEGALCASE_TASK_DETAIL) {
                                    it["id"] = item.id ?: ""
                                    it["taskId"] = item.taskId ?: ""
                                    it["processType"] = item.processType ?: ""
                                }
                            }
                            "complaint" -> {
                                XApp.startXApp(RoutePath.ROUTE_COMPLAIN_TASK_DETAIL) {
                                    it["fGuid"] = item.fGuid ?: ""
                                }
                            }
                            "entityTask" -> {
                                XApp.startXApp(RoutePath.ROUTE_SUPERVISE_TASK_DETAIL) {
                                    it["id"] = item.fId ?: ""
                                    it["taskId"] = item.fTaskId ?: ""
                                }
                            }
                        }
                    }
                })
    }

    /**
     * 数据加载
     */
    fun loadData(refresh: Boolean = false) {
        mPresenter.getTaskList(hashMapOf("queryType" to if (intent.getStringExtra("routeName") == "已经逾期") {
            "overdue"
        } else if (intent.getStringExtra("routeName") == "即将到期") {
            "soon"
        } else {
            "todo"
        }, "condition" to searchText))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //搜索事件
        sv_mytask_search.setSearchListener {
            searchText = it
            loadData()
        }
    }

    override fun onTaskListResult(taskList: List<TaskBean>) {
        sr_my_task.clearStatus()
        sr_my_task.refreshData(taskList, 999)
    }

    override fun onResume() {
        super.onResume()
        loadData(true)
    }
}
