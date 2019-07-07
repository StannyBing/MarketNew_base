package com.zx.module_legalcase.module.query.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_legalcase.R
import com.zx.module_legalcase.XAppLegalcase
import com.zx.module_legalcase.module.query.mvp.bean.LegalcaseListBean
import com.zx.module_legalcase.module.query.mvp.contract.QueryContract
import com.zx.module_legalcase.module.query.mvp.model.QueryModel
import com.zx.module_legalcase.module.query.mvp.presenter.QueryPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.SearchFilterBean
import kotlinx.android.synthetic.main.activity_query.*


/**
 * Create By admin On 2017/7/11
 * 功能：案件执法-主页
 */
@Route(path = RoutePath.ROUTE_LEGALASE_QUERY)
class QueryActivity : BaseActivity<QueryPresenter, QueryModel>(), QueryContract.View {

    private var pageNo = 1
    private var searchText = ""
    private var dataBeans = arrayListOf<LegalcaseListBean>()
//    private var mAdapter = ComplainListAdapter(dataBeans)

    private val filterList = arrayListOf<SearchFilterBean>()//过滤条件
    private var fType = ""//投诉类别
    private var fStatus = ""//办理状态
    private var overdue = ""//逾期状态
    private var searchType = "0"//搜索类型 0我的任务  1全部任务

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, QueryActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_query
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolBar_view.withXApp(XAppLegalcase.get("案件查询"))
        search_view.withXApp(XAppLegalcase.get("案件查询"))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
