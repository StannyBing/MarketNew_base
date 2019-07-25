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
import com.zx.module_supervise.module.daily.mvp.contract.DailyQueryContract
import com.zx.module_supervise.module.daily.mvp.model.DailyQueryModel
import com.zx.module_supervise.module.daily.mvp.presenter.DailyQueryPresenter
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import kotlinx.android.synthetic.main.activity_daily_query.*


/**
 * Create By admin On 2017/7/11
 * 功能：现场检查
 */
@Route(path = RoutePath.ROUTE_DAILY_QUERY)
class DailyQueryActivity : BaseActivity<DailyQueryPresenter, DailyQueryModel>(), DailyQueryContract.View {

    private var pageNo = 1
    private var searchText = ""
    private var dataBeans = arrayListOf<DailyListBean>()
    private var mAdapter = DailyListAdapter(dataBeans)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, DailyQueryActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_daily_query
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolBar_view.withXApp(XAppSupervise.get("现场检查"))
        search_view.withXApp(XAppSupervise.get("现场检查"))
        tv_daily_tips.setTextColor(ContextCompat.getColor(this, XAppSupervise.get("现场检查")!!.moduleColor))

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
                        DailyAddActivity.startAction(this@DailyQueryActivity, false, item!!.id)
                    }
                })
        loadData(true)
    }

    private fun loadData(refresh: Boolean = false) {
        if (refresh) {
            pageNo = 1
            sr_daily_list.clearStatus()
        }
        mPresenter.getDailyList(hashMapOf("pageNo" to pageNo.toString(), "pageSize" to 15.toString(), "name" to searchText))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //搜索事件
        search_view.setSearchListener {
            searchText = it
            loadData(true)
        }
        //顶部点击滚动到开头
        toolBar_view.setMidClickListener { sr_daily_list.recyclerView.animateToTop(0) }
        //新增按钮
        toolBar_view.setRightClickListener {
            DailyAddActivity.startAction(this, false)
        }
        //结论补录
        mAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.tv_daily_status) {
                if (dataBeans[position].result == null) {
                    ZXDialogUtil.showListDialog(this, "结论补录", "关闭", arrayOf("符合", "不符合", "基本符合")
                    ) { dialog, which -> mPresenter.updateDaily(hashMapOf("id" to dataBeans[position].id, "result" to which).toJson()) }
                }
            }
        }
    }

    override fun onDailyListResult(dailyList: NormalList<DailyListBean>) {
        tv_daily_tips.text = "检索到检查任务共${dailyList.total}条"
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
