package com.zx.module_other.module.law.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_library.func.tool.UserManager
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.law.bean.*
import com.zx.module_other.module.law.func.adapter.LawQueryListAdapter
import com.zx.module_other.module.law.func.adapter.LawQuerySortAdapter
import com.zx.module_other.module.law.mvp.contract.LawQueryContract
import com.zx.module_other.module.law.mvp.model.LawQueryModel
import com.zx.module_other.module.law.mvp.presenter.LawQueryPresenter
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import kotlinx.android.synthetic.main.activity_law_collect.*
import kotlinx.android.synthetic.main.activity_law_query.*
import kotlinx.android.synthetic.main.activity_law_query.toobar_view

class LawQueryActivity : BaseActivity<LawQueryPresenter, LawQueryModel>(), LawQueryContract.View {

    private var pageNo = 1
    private var lawMainBean: LawMainBean? = null
    private var sortDatas = arrayListOf<LawBean>()
    private var sortListAdapter = LawQuerySortAdapter(sortDatas)
    private var keywordDatas = arrayListOf<LawSearchBean>()
    private var keywordListAdapter = LawQueryListAdapter(keywordDatas)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, lawMainBean: LawMainBean) {
            val intent = Intent(activity, LawQueryActivity::class.java)
            intent.putExtra("lawBean", lawMainBean)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toobar_view.withXApp(XAppOther.LAW)
        sv_law_search.withXApp(XAppOther.LAW)


        if (intent != null) {
            lawMainBean = intent.getSerializableExtra("lawBean") as LawMainBean
        }
        queryPostMethod()
        sv_law_search.setSearchListener {
            pageNo = 1
            lawMainBean = LawMainBean(it, 4, 0)
            queryPostMethod()
        }
    }

    /**
     * 请求方法
     */
    private fun queryPostMethod() {
        if (lawMainBean!!.type == 0) {
            rv_law_query.setLayoutManager(LinearLayoutManager(this@LawQueryActivity))
                    .setAdapter(keywordListAdapter)
                    .autoLoadMore()
                    .setPageSize(15)
                    .setSRListener(object : ZXSRListener<LawSearchBean> {
                        override fun onItemLongClick(item: LawSearchBean?, position: Int) {
                        }

                        override fun onLoadMore() {
                            pageNo++
                            loadData(false, 0)
                        }

                        override fun onRefresh() {
                            loadData(true, 0)
                        }

                        override fun onItemClick(item: LawSearchBean?, position: Int) {
                            LawDetailActivity.startAction(this@LawQueryActivity, false, item!!.id.toString())
                        }

                    })
            loadData(true, 0)
        } else if (lawMainBean!!.type == 1) {
            rv_law_query.setLayoutManager(LinearLayoutManager(this@LawQueryActivity))
                    .setAdapter(sortListAdapter)
                    .autoLoadMore()
                    .setPageSize(25)
                    .setSRListener(object : ZXSRListener<LawCollectBean> {
                        override fun onItemLongClick(item: LawCollectBean?, position: Int) {
                        }

                        override fun onLoadMore() {
                            pageNo++
                            loadData(false, 1)
                        }

                        override fun onRefresh() {
                            loadData(true, 1)
                        }

                        override fun onItemClick(item: LawCollectBean?, position: Int) {
                            LawDetailActivity.startAction(this@LawQueryActivity, false, item!!.id.toString())
                        }

                    })
            loadData(true, 1)
        }
    }

    fun loadData(refresh: Boolean = false, type: Int) {
        if (refresh) {
            pageNo = 1
            rv_law_query.clearStatus()
        }
        when (type) {
            0 -> mPresenter.getSearchLaw(ApiParamUtil.lawSearchParam(lawMainBean!!.name))
            1 -> mPresenter.getLawList(ApiParamUtil.lawSelectParam(UserManager.getUser().departmentCode, UserManager.getUser().id))
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {


        sortListAdapter.setOnItemClickListener { adapter, view, position ->
            if (position != 0) {
                val sortData = sortDatas.get(position)
                lawMainBean = LawMainBean(sortData.name, sortData.id, 2)
            }
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_law_query
    }


    override fun onLawListResult(complainList: NormalList<LawBean>) {
        rv_law_query.refreshData(complainList!!.list, complainList.total)
    }

    override fun onSearchLawResult(lawSearchLawResult: NormalList<LawSearchBean>) {
        rv_law_query.refreshData(lawSearchLawResult!!.list, lawSearchLawResult.total)
    }

    /**
     * 删除搜索数据
     */
    fun removeKeywordData() {
        if (keywordDatas != null && keywordDatas.size > 0) {
            keywordDatas.clear()
        }
    }
}