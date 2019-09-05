package com.zx.module_statistics.module.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_complain.XAppComplain
import com.zx.module_legalcase.XAppLegalcase
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.XAppBean
import com.zx.module_statistics.R
import com.zx.module_statistics.XAppStatistics
import com.zx.module_statistics.module.func.adapter.StatisticsItemAdapter
import com.zx.module_statistics.module.mvp.contract.StatisticsMainContract
import com.zx.module_statistics.module.mvp.model.StatisticsMainModel
import com.zx.module_statistics.module.mvp.presenter.StatisticsMainPresenter
import com.zx.module_supervise.XAppSupervise
import kotlinx.android.synthetic.main.activity_statistics_main.*


/**
 * Create By admin On 2017/7/11
 * 功能：统计分析主页
 */
@Route(path = RoutePath.ROUTE_STATISTICS_MAIN)
class StatisticsMainActivity : BaseActivity<StatisticsMainPresenter, StatisticsMainModel>(), StatisticsMainContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, StatisticsMainActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    private val statisticsList = arrayListOf<XAppBean>()
    private val statisticsAdapter = StatisticsItemAdapter(statisticsList)

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_statistics_main
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolbar_view.withXApp(XAppStatistics.MAIN)

        statisticsList.add(XAppSupervise.DAILY)
        statisticsList.add(XAppSupervise.SUPERVISE)
        statisticsList.add(XAppComplain.LIST)
        statisticsList.add(XAppLegalcase.HANDLE)

        rv_statistics.apply {
            layoutManager = GridLayoutManager(this@StatisticsMainActivity, 2) as RecyclerView.LayoutManager?
            adapter = statisticsAdapter
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        statisticsAdapter.setOnItemClickListener { _, _, position ->
            when (statisticsList[position]) {
                XAppSupervise.DAILY -> {
                    StatisticsInfoActivity.startAction(this, false, statisticsList[position])
                }
                else -> {
                    showToast("正在建设中")
                }
            }
        }
    }

}
