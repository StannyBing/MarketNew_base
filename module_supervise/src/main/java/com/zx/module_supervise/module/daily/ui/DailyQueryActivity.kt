package com.zx.module_supervise.module.daily.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.daily.bean.DailyListBean
import com.zx.module_supervise.module.daily.mvp.contract.DailyQueryContract
import com.zx.module_supervise.module.daily.mvp.model.DailyQueryModel
import com.zx.module_supervise.module.daily.mvp.presenter.DailyQueryPresenter
import kotlinx.android.synthetic.main.activity_daily_query.*


/**
 * Create By admin On 2017/7/11
 * 功能：现场检查
 */
@Route(path = RoutePath.ROUTE_SUPERVISE_DAILY)
class DailyQueryActivity : BaseActivity<DailyQueryPresenter, DailyQueryModel>(), DailyQueryContract.View {

    private var pageNo = 1

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

//        sr_daily_list.setLayoutManager(LinearLayoutManager(this))
//                .setAdapter(mAdapter)
//                .autoLoadMore()
//                .setPageSize(15)
//                .setSRListener(object : ZXSRListener<SuperviseListBean> {
//                    override fun onItemLongClick(item: SuperviseListBean?, position: Int) {
//                    }
//
//                    override fun onLoadMore() {
//                        pageNo++
//                        loadData()
//                    }
//
//                    override fun onRefresh() {
//                        loadData(true)
//                    }
//
//                    override fun onItemClick(item: SuperviseListBean?, position: Int) {
//                        SuperviseDetailActivity.startAction(this@DailyQueryActivity, false, item!!.fId, item.fTaskId, searchType == "0")
//                    }
//
//                })
        loadData(true)
    }

    private fun loadData(refresh : Boolean){

    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    override fun onDailyListResult(dailyList: NormalList<DailyListBean>) {

    }
}
