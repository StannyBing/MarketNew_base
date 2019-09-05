package com.zx.module_supervise.module.daily.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_library.func.tool.animateToTop
import com.zx.module_library.func.tool.toJson
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.daily.bean.DailyListBean
import com.zx.module_supervise.module.daily.func.adapter.DailyListAdapter
import com.zx.module_supervise.module.daily.mvp.contract.DailyListContract
import com.zx.module_supervise.module.daily.mvp.model.DailyListModel
import com.zx.module_supervise.module.daily.mvp.presenter.DailyListPresenter
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import kotlinx.android.synthetic.main.activity_daily_list.*


/**
 * Create By admin On 2017/7/11
 * 功能：主体相关检查列表
 */
@Route(path = RoutePath.ROUTE_SUPERVISE_DAILY_LIST)
class DailyListActivity : BaseActivity<DailyListPresenter, DailyListModel>(), DailyListContract.View {

    private var pageNo = 1
    private var searchText = ""
    private var dataBeans = arrayListOf<DailyListBean>()
    private var mAdapter = DailyListAdapter(dataBeans)

    private var monthNum = ""//工作成果
    private var enterpriseId = ""

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, enterpriseId: String = "") {
            val intent = Intent(activity, DailyListActivity::class.java)
            intent.putExtra("enterpriseId", enterpriseId)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_daily_list
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolBar_view.withXApp(XAppSupervise.DAILY)
        tv_daily_tips.setTextColor(ContextCompat.getColor(this, XAppSupervise.DAILY.moduleColor))

        monthNum = if (intent.hasExtra("monthNum")) intent.getStringExtra("monthNum") else ""
        enterpriseId = if (intent.hasExtra("enterpriseId")) intent.getStringExtra("enterpriseId") else ""

        sr_daily_list.setLayoutManager(LinearLayoutManager(this))
                .setAdapter(mAdapter)
                .autoLoadMore()
                .setPageSize(15)
                .setSRListener(object : ZXSRListener<DailyListBean> {
                    override fun onItemLongClick(item: DailyListBean?, position: Int) {
                    }

                    override fun onLoadMore() {
                        pageNo++
                        loadData()
                    }

                    override fun onRefresh() {
                        loadData(true)
                    }

                    override fun onItemClick(item: DailyListBean?, position: Int) {
                        DailyAddActivity.startAction(this@DailyListActivity, false, item!!.id)
                    }
                })
        sr_daily_list.swipeRefreshLayout.setColorSchemeResources(R.color.daily_color)
        loadData(true)
    }

    private fun loadData(refresh: Boolean = false) {
        if (refresh) {
            pageNo = 1
            sr_daily_list.clearStatus()
        }
        if (monthNum.isNotEmpty()) {
            mPresenter.getDailyList(hashMapOf("pageNo" to pageNo.toString(), "pageSize" to 15.toString(), "queryType" to "workResult", "monthNum" to monthNum))
        } else {
            mPresenter.getDailyList(hashMapOf("pageNo" to pageNo.toString(), "pageSize" to 15.toString(), "enterpriseId" to enterpriseId))
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //顶部点击滚动到开头
        toolBar_view.setMidClickListener { sr_daily_list.recyclerView.animateToTop(0) }

        //结论补录
        mAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.tv_daily_status) {
                if (dataBeans[position].result == null) {
                    ZXDialogUtil.showListDialog(this, "结论补录", "关闭", arrayOf("符合要求", "当场整改", "当场行政处罚", "限期整改", "停业整顿", "移交稽查")
                    ) { dialog, which -> mPresenter.updateDaily(hashMapOf("id" to dataBeans[position].id, "result" to (which + 3)).toJson()) }
                }
            }
        }
    }


    override fun onDailyListResult(dailyList: NormalList<DailyListBean>) {
        tv_daily_tips.text = "检索到该主体任务共${dailyList.total}条"
        sr_daily_list.refreshData(dailyList.list, dailyList.total)
    }

    override fun onDailyUpdateResult() {
        showToast("更新成功")
        loadData(true)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01) {
            loadData(true)
        }
    }

}
