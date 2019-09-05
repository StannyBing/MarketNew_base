package com.zx.module_statistics.module.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.func.tool.DatePickerTool
import com.zx.module_statistics.R
import com.zx.module_statistics.module.bean.ChartBean
import com.zx.module_statistics.module.bean.StatisticsBean
import com.zx.module_statistics.module.mvp.contract.ChartContract
import com.zx.module_statistics.module.mvp.model.ChartModel
import com.zx.module_statistics.module.mvp.presenter.ChartPresenter
import com.zx.zxutils.entity.KeyValueEntity
import com.zx.zxutils.views.MPChart.ChartKeyValue
import kotlinx.android.synthetic.main.fragment_chart.*
import java.util.*

/**
 * Create By admin On 2017/7/11
 * 功能：统计图表
 */
class ChartFragment : BaseFragment<ChartPresenter, ChartModel>(), ChartContract.View {
    companion object {
        /**
         * 启动器
         */
        fun newInstance(statisticsBean: StatisticsBean): ChartFragment {
            val fragment = ChartFragment()
            val bundle = Bundle()
            bundle.putSerializable("statisticsBean", statisticsBean)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var statisticsBean: StatisticsBean

    private val chartList = arrayListOf<ChartBean>()

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_chart
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        statisticsBean = arguments!!.getSerializable("statisticsBean") as StatisticsBean

        sp_chart_year.apply {
            showUnderineColor(false)
            val year = Calendar.getInstance().get(Calendar.YEAR)
            for (i in (year - 5)..year) {
                addData(KeyValueEntity(i.toString(), i.toString()))
            }
            setItemHeightDp(40)
            setItemTextSizeSp(15)
            setDefaultItem(5)
            showSelectedTextColor(true, R.color.colorPrimary)
            build()
        }

        sp_chart_dept.apply {
            showUnderineColor(false)
            setItemHeightDp(40)
            setItemTextSizeSp(15)
            showSelectedTextColor(true, R.color.colorPrimary)
            build()
        }

        loadData()
    }

    /**
     * 加载数据
     */
    private fun loadData() {
        when (statisticsBean.type) {
            "daily" -> {
                when (statisticsBean.name) {
                    "分局分布" -> {
                        ll_filter_time.visibility = View.VISIBLE
                        mp_barchart.visibility = View.VISIBLE
                        mPresenter.getDailyByArea(hashMapOf("checkDateStart" to tv_chart_startTime.text.toString(), "checkDateEnd" to tv_chart_endTime.text.toString()))
                    }
                    "趋势分析" -> {
                        ll_filter_year.visibility = View.VISIBLE
                        ll_chart_year.visibility = View.VISIBLE
                        mp_linechart.visibility = View.VISIBLE
                        mPresenter.getDailyByTrend(hashMapOf("queryType" to if (rb_chart_yeartype0.isChecked) "year" else "month", "year" to if (rb_chart_yeartype0.isChecked) "" else sp_chart_year.selectedKey))
                    }
                    "检查结果" -> {
                        ll_filter_time.visibility = View.VISIBLE
                        ll_chart_year.visibility = View.VISIBLE
                        mp_piechart.visibility = View.VISIBLE
                        mPresenter.getDailyByResult(hashMapOf("checkDateStart" to tv_chart_startTime.text.toString(), "checkDateEnd" to tv_chart_endTime.text.toString()))
                    }
                }
            }
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //起始时间
        tv_chart_startTime.setOnClickListener {
            DatePickerTool.showDatePicker(activity!!, tv_chart_startTime) {
                loadData()
            }
        }
        //截止时间
        tv_chart_endTime.setOnClickListener {
            DatePickerTool.showDatePicker(activity!!, tv_chart_endTime) {
                loadData()
            }
        }
        //年份选择
        rg_chart_yeartype.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == rb_chart_yeartype0.id) {
                ll_chart_year.visibility = View.GONE
            } else {
                ll_chart_year.visibility = View.VISIBLE
            }
            loadData()
        }
        //年份选择
        sp_chart_year.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (ll_chart_year.visibility == View.VISIBLE) {
                    loadData()
                }
            }
        }
    }

    override fun onChartListResult(chartList: List<ChartBean>) {
        if (chartList.isEmpty()) {
            tv_chart_nodata.visibility = View.VISIBLE
        } else {
            tv_chart_nodata.visibility = View.GONE
        }
        this.chartList.clear()
        this.chartList.addAll(chartList)
        if (mp_linechart.visibility == View.VISIBLE) {//折线图
            initLineChart()
        } else if (mp_barchart.visibility == View.VISIBLE) {//柱状图
            initBarChart()
        } else if (mp_piechart.visibility == View.VISIBLE) {//饼状图
            initPieChart()
        }
    }

    //折线图
    private fun initLineChart() {
        mp_linechart.apply {
            clear()
            setUnit(statisticsBean.unit)
            if (chartList.hasSecendList()) {
                chartList.forEach {
                    val keyValues = arrayListOf<ChartKeyValue>()
                    it.value!!.forEach {
                        keyValues.add(ChartKeyValue(it.name ?: "", it.num ?: 0))
                    }
                    addData(keyValues, it.name)
                }
            } else {
                val keyValues = arrayListOf<ChartKeyValue>()
                chartList.forEach {
                    keyValues.add(ChartKeyValue(it.name ?: "", it.num ?: 0))
                }
                addData(keyValues, statisticsBean.name)
            }
            addComplete(2000)
        }
    }

    //柱状图
    private fun initBarChart() {
        mp_barchart.apply {
            clear()
            setUnit(statisticsBean.unit)
            if (chartList.hasSecendList()) {
                chartList.forEach {
                    val keyValues = arrayListOf<ChartKeyValue>()
                    it.value!!.forEach {
                        keyValues.add(ChartKeyValue(it.name ?: "", it.num ?: 0))
                    }
                    addData(keyValues, it.name)
                }
            } else {
                val keyValues = arrayListOf<ChartKeyValue>()
                chartList.forEach {
                    keyValues.add(ChartKeyValue(it.name ?: "", it.num ?: 0))
                }
                addData(keyValues, statisticsBean.name)
            }
            addComplete(2000)
        }
    }

    //饼状图
    private fun initPieChart() {
        mp_piechart.apply {
            clear()
            if (chartList.hasSecendList()) {
                chartList.forEach {
                    val keyValues = arrayListOf<ChartKeyValue>()
                    it.value!!.forEach {
                        keyValues.add(ChartKeyValue(it.name ?: "", it.num ?: 0))
                    }
                    addData(keyValues, it.name)
                }
            } else {
                val keyValues = arrayListOf<ChartKeyValue>()
                chartList.forEach {
                    keyValues.add(ChartKeyValue(it.name ?: "", it.num ?: 0))
                }
                addData(keyValues, statisticsBean.name)
            }
            addComplete(2000)
        }
    }

    private fun List<ChartBean>.hasSecendList(): Boolean {
        forEach {
            if (it.value != null) {
                return true
            }
        }
        return false
    }
}
