package com.zx.module_other.module.workresults.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.law.func.adapter.WorkResultAllAdapter
import com.zx.module_other.module.workresults.bean.WorkOverAllBean
import com.zx.module_other.module.workresults.bean.WorkStatisicsBean
import com.zx.module_other.module.workresults.mvp.contract.WorkResultsContract
import com.zx.module_other.module.workresults.mvp.model.WorkResultsModel
import com.zx.module_other.module.workresults.mvp.presenter.WorkResultsPresenter
import com.zx.zxutils.views.ZXStatusBarCompat
import kotlinx.android.synthetic.main.activity_work_results.*

@Route(path = RoutePath.ROUTE_OTHER_RESULTS)
class WorkResultsActivity : BaseActivity<WorkResultsPresenter, WorkResultsModel>(), WorkResultsContract.View {

    private var workAllData = listOf<WorkOverAllBean>()
    private var workResultAllAdapter = WorkResultAllAdapter(workAllData)


    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, WorkResultsActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun onViewListener() {
        workResultAllAdapter.setOnItemClickListener { adapter, view, position ->
            when (workAllData[position].business) {
                "投诉举报" -> XApp.startXApp(RoutePath.ROUTE_COMPLAIN_TASK) {
                    it["monthNum"] = "4"
                }
                "综合执法" -> XApp.startXApp(RoutePath.ROUTE_LEGALCASE_TASK) {
                    it["monthNum"] = "4"
                }
                "现场检查" -> XApp.startXApp(RoutePath.ROUTE_SUPERVISE_DAILY) {
                    it["monthNum"] = "4"
                }
                "监管任务" -> XApp.startXApp(RoutePath.ROUTE_SUPERVISE_TASK) {
                    it["monthNum"] = "4"
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_work_results
    }

    @SuppressLint("ResourceAsColor")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ZXStatusBarCompat.translucent(this, R.color.work_satisics_bg)
        ZXStatusBarCompat.setStatusBarDarkMode(this)

        toolbar_view.toolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.white))

        rv_workresult.apply {
            layoutManager = GridLayoutManager(this@WorkResultsActivity, 2)
            adapter = workResultAllAdapter
        }
        mPresenter.getWorkStatisicsList(ApiParamUtil.workStatisicsPlanParam("4"))
        mPresenter.getWorkOverallList(mapOf())
    }

    override fun getWorkStatisicsResult(workStatisicsDatas: List<WorkStatisicsBean>) {
        var nums = arrayListOf<Int>()
        var dates = arrayListOf<String>()
        for (workStatisics in workStatisicsDatas) {
            nums.add(workStatisics.num)
            dates.add(workStatisics.date)
        }
        sv_statistics.setDatas(nums, dates)
    }

    override fun getWorkOverallResult(workOverAlls: List<WorkOverAllBean>) {
        workAllData = workOverAlls
        workResultAllAdapter.setNewData(workAllData)
    }
}