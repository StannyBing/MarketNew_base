package com.zx.module_other.module.workplan.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.necer.enumeration.CalendarState
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.module.law.ui.LawActivity
import com.zx.module_other.module.workplan.bean.WorkPlanBean
import com.zx.module_other.module.workplan.func.adapter.WorkPlanAdpater
import com.zx.module_other.module.workplan.mvp.contract.WorkPlanContract
import com.zx.module_other.module.workplan.mvp.model.WorkPlanModel
import com.zx.module_other.module.workplan.mvp.presenter.WorkPlanPresenter
import kotlinx.android.synthetic.main.activity_work_plan.*

@Route(path = RoutePath.ROUTE_OTHER_PLAN)
class WorkPlanActivity : BaseActivity<WorkPlanPresenter, WorkPlanModel>(), WorkPlanContract.View {

    private var workPlanDatas: ArrayList<WorkPlanBean> = arrayListOf()
    private var workPlanAdapter: WorkPlanAdpater<WorkPlanBean> = WorkPlanAdpater(workPlanDatas)

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
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_work_plan
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toobar_view.showRightImg()
//        toobar_view.setMidText(work_plan_calendar.getAllSelectDateList().get(0).toString())
        workPlanDatas.add(WorkPlanBean("重庆市大药科技涉嫌违法","截止日期：2018年8月8日"))
        workPlanDatas.add(WorkPlanBean("重庆市大药科技涉嫌违法","截止日期：2018年8月8日"))
        workPlanDatas.add(WorkPlanBean("重庆市大药科技涉嫌违法","截止日期：2018年8月8日"))
        rv_work_plan.apply {
            adapter = workPlanAdapter
            layoutManager = LinearLayoutManager(this@WorkPlanActivity)
        }
    }
}