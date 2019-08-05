package com.zx.module_other.module.workplan.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.necer.enumeration.CalendarState
import com.necer.painter.InnerPainter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.func.adapter.WorkPlanAdpater
import com.zx.module_other.module.workplan.func.util.DateUtil
import com.zx.module_other.module.workplan.func.util.SpacesItemDecoration
import com.zx.module_other.module.workplan.mvp.contract.WorkPlanContract
import com.zx.module_other.module.workplan.mvp.model.WorkPlanModel
import com.zx.module_other.module.workplan.mvp.presenter.WorkPlanPresenter
import kotlinx.android.synthetic.main.activity_work_plan.*
import org.joda.time.LocalDate

@Route(path = RoutePath.ROUTE_OTHER_PLAN)
class WorkPlanActivity : BaseActivity<WorkPlanPresenter, WorkPlanModel>(), WorkPlanContract.View {

    private var workPlanDatas: ArrayList<WorkPlanBean> = arrayListOf()
    private var workPlanAdapter: WorkPlanAdpater<WorkPlanBean> = WorkPlanAdpater(workPlanDatas)
    private var innerPainter: InnerPainter? = null
    private var localDate: LocalDate? = null

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, WorkPlanActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun onViewListener() {
        work_plan_calendar.setOnCalendarStateChangedListener {
            when (it) {
                CalendarState.MONTH -> iv_calendar_open.setImageResource(R.drawable.work_plan_up)
                CalendarState.WEEK -> iv_calendar_open.setImageResource(R.drawable.work_plan_down)
            }
        }
        work_plan_calendar.setOnCalendarChangedListener { baseCalendar, year, month, localDate ->
            tv_plan_date.setText(month.toString() + "月份")
            tv_plan_week.setText("")
            var startDate = ""
            var endDate = ""
            if (month < 10) {
                startDate = year.toString() + "-0" + month+"-01 00:00:00"
                if (month == 9){
                    endDate = year.toString()+"-10"+ "-01 00:00:00"
                }else{
                    endDate = year.toString()+"-0"+(month+1)+"-01 00:00:00"
                }
            }else{
                startDate = year.toString() + "-" + month+"-01 00:00:00"
                if (month == 12){
                    endDate = (year+1).toString()+"-01"+"-01 00:00:00"
                }else{
                    endDate = year.toString()+"-"+(month+1)+"-01 00:00:00"
                }
            }
            if (this.localDate == null) {
                getWorkPlan(DateUtil.timeStringToStamp(startDate).toString(), DateUtil.timeStringToStamp(endDate).toString())
            } else if (this.localDate!!.monthOfYear != localDate.monthOfYear) {
                getWorkPlan(DateUtil.timeStringToStamp(startDate).toString(), DateUtil.timeStringToStamp(endDate).toString())
            } else if (this.localDate!!.dayOfMonth != localDate.dayOfMonth) {
                getWorkPlan(DateUtil.timeStringToStamp(localDate.toString() + " 00:00:00").toString(), DateUtil.timeStringToStamp(localDate.toString() + " 24:00:00").toString())
                tv_plan_date.setText(localDate.toString().substring(5))
                tv_plan_week.setText(DateUtil.dateToWeek(localDate.toString()))
            }
            this.localDate = localDate
        }
        iv_create_plan.setOnClickListener {
            CreatePlanActivity.startAction(this, false)
        }
        toobar_view.setRightClickListener {
            WorkListActivity.startAction(this,false)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_work_plan
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toobar_view.withXApp(XAppOther.get("个人工作计划")!!)
        innerPainter = work_plan_calendar.calendarPainter as InnerPainter?
        rv_work_plan.apply {
            adapter = workPlanAdapter
            layoutManager = LinearLayoutManager(this@WorkPlanActivity)
        }

        rv_work_plan.addItemDecoration(SpacesItemDecoration(10));
    }

    fun getWorkPlan(startDateMin: String, startDateMax: String) {
        mPresenter.getWorkPlanList(ApiParamUtil.workPlanParam(startDateMin, startDateMax));
    }

    override fun getWorkPlanResult(workPlanBeans: List<WorkPlanBean>) {
        workPlanDatas.clear();
        workPlanDatas.addAll(workPlanBeans)
        workPlanAdapter.setNewData(workPlanBeans)
        var timeList = arrayListOf<String>()
        for (workPlan in workPlanBeans) {
            timeList.add(DateUtil.stampToTime(workPlan.endDate))
        }
        innerPainter!!.setPointList(timeList)
    }
}