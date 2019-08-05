package com.zx.module_other.module.workplan.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.func.adapter.WorkPlanAdpater
import com.zx.module_other.module.workplan.func.util.DateUtil

import com.zx.module_other.module.workplan.mvp.contract.WorkListContract
import com.zx.module_other.module.workplan.mvp.model.WorkListModel
import com.zx.module_other.module.workplan.mvp.presenter.WorkListPresenter
import kotlinx.android.synthetic.main.activity_work_list.*


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class WorkListActivity : BaseActivity<WorkListPresenter, WorkListModel>(), WorkListContract.View {

    var dates = arrayListOf<String>()
    var datesAdpter: ArrayAdapter<String>? = null
    var workPlanDatas: ArrayList<WorkPlanBean> = arrayListOf()
    var workPlanAdapter: WorkPlanAdpater<WorkPlanBean> = WorkPlanAdpater(workPlanDatas)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, WorkListActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_work_list
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toobar_view.withXApp(XAppOther.get("个人工作计划")!!)
        mPresenter.getWorkDatesList()
        datesAdpter = ArrayAdapter(this, com.zx.module_other.R.layout.item_start_print, R.id.text, dates)
        sp_work_list.adapter = datesAdpter
        sp_work_list.setSelection(10, true);
        rv_work_list.apply {
            adapter = workPlanAdapter
            layoutManager = LinearLayoutManager(this@WorkListActivity)
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        sp_work_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //切换选择
                val year = dates[position].substring(0, 4).toInt()
                val month = dates[position].substring(5, 7).toInt()
                var startDate = ""
                var endDate = ""
                if (month < 10) {
                    startDate = year.toString() + "-0" + month + "-01 00:00:00"
                    if (month == 9) {
                        endDate = year.toString() + "-10" + "-01 00:00:00"
                    } else {
                        endDate = year.toString() + "-0" + (month + 1) + "-01 00:00:00"
                    }
                } else {
                    startDate = year.toString() + "-" + month + "-01 00:00:00"
                    if (month == 12) {
                        endDate = (year + 1).toString() + "-01" + "-01 00:00:00"
                    } else {
                        endDate = year.toString() + "-" + (month + 1) + "-01 00:00:00"
                    }
                }
                getWorkPlan(DateUtil.timeStringToStamp(startDate).toString(), DateUtil.timeStringToStamp(endDate).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun getWorkPlan(startDateMin: String, startDateMax: String) {
        mPresenter.getWorkPlanList(ApiParamUtil.workPlanParam(startDateMin, startDateMax));
    }

    override fun getWorkPlanResult(workPlanBeans: List<WorkPlanBean>) {
        workPlanDatas.clear();
        workPlanDatas.addAll(workPlanBeans)
        workPlanAdapter.setNewData(workPlanBeans)
    }

    override fun getWorkDatesResult(dates: ArrayList<String>) {
        this.dates = dates
    }
}
