package com.zx.module_statistics.module.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.XAppBean
import com.zx.module_statistics.R
import com.zx.module_statistics.XAppStatistics
import com.zx.module_statistics.module.bean.StatisticsBean
import com.zx.module_statistics.module.func.adapter.StatisticsInfoAdapter
import com.zx.module_statistics.module.func.adapter.StatistisGuideAdapter
import com.zx.module_statistics.module.mvp.contract.StatisticsInfoContract
import com.zx.module_statistics.module.mvp.model.StatisticsInfoModel
import com.zx.module_statistics.module.mvp.presenter.StatisticsInfoPresenter
import com.zx.module_supervise.XAppSupervise
import com.zx.zxutils.util.ZXSystemUtil
import kotlinx.android.synthetic.main.activity_statistics_info.*


/**
 * Create By admin On 2017/7/11
 * 功能：统计信息
 */
@Route(path = RoutePath.ROUTE_STATISTICS_INFO)
class StatisticsInfoActivity : BaseActivity<StatisticsInfoPresenter, StatisticsInfoModel>(), StatisticsInfoContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, xApp: XAppBean) {
            val intent = Intent(activity, StatisticsInfoActivity::class.java)
            intent.putExtra("xApp", xApp)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    private lateinit var xApp: XAppBean

    private val dataList = arrayListOf<StatisticsBean>()
    private val guideAdapter = StatistisGuideAdapter(dataList)
    private val infoAdapter = StatisticsInfoAdapter(dataList)

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_statistics_info
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        xApp = intent.getSerializableExtra("xApp") as XAppBean
        toolbar_view.withXApp(XAppStatistics.MAIN)
        toolbar_view.setMidText(xApp.name)

        initItems()

        rv_statistics_guide.apply {
            layoutManager = LinearLayoutManager(this@StatisticsInfoActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = guideAdapter
        }
        rv_statistics_list.apply {
            layoutManager = LinearLayoutManager(this@StatisticsInfoActivity)
            adapter = infoAdapter
        }
    }

    /**
     * 初始化统计项
     */
    private fun initItems() {
        when (xApp) {
            XAppSupervise.DAILY -> {
                dataList.add(StatisticsBean("daily", "分局分布","件"))
                dataList.add(StatisticsBean("daily", "趋势分析","件"))
                dataList.add(StatisticsBean("daily", "检查结果","件"))
            }
        }
    }

    /**
     * View事件设置
     */
    @SuppressLint("NewApi")
    override fun onViewListener() {
        guideAdapter.setOnItemClickListener { _, _, position ->
            rv_statistics_list.smoothScrollToPosition(position)
            guideAdapter.selectItem = position
            guideAdapter.notifyDataSetChanged()
        }
        rv_statistics_list.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val offset = rv_statistics_list.computeVerticalScrollOffset()
            val position = if (!rv_statistics_list.canScrollVertically(-1)) {
                0
            } else if (!rv_statistics_list.canScrollVertically(1)) {
                dataList.lastIndex
            } else {
                (offset + ZXSystemUtil.dp2px(10f)) / ZXSystemUtil.dp2px(300f) + 1
            }
            guideAdapter.selectItem = position
            rv_statistics_guide.smoothScrollToPosition(position)
            guideAdapter.notifyDataSetChanged()
        }
    }

}
