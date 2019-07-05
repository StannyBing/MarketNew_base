package com.zx.module_other.module.law.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.UserManager
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.law.bean.LawBean
import com.zx.module_other.module.law.bean.LawMainBean
import com.zx.module_other.module.law.bean.LawSearchBean
import com.zx.module_other.module.law.bean.LawSearchResultBean
import com.zx.module_other.module.law.func.adapter.LawQueryListAdapter
import com.zx.module_other.module.law.mvp.contract.LawQueryContract
import com.zx.module_other.module.law.mvp.model.LawQueryModel
import com.zx.module_other.module.law.mvp.presenter.LawQueryPresenter
import kotlinx.android.synthetic.main.activity_law_query.*

class LawQueryActivity : BaseActivity<LawQueryPresenter, LawQueryModel>(), LawQueryContract.View {


    private var lawMainBean: LawMainBean? = null
    private var sortDatas = arrayListOf<LawBean>()
    private var sortListAdapter = LawQueryListAdapter(sortDatas)
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

        toobar_view.withXApp(XAppOther.get("法律法规"))
        sv_law_search.withXApp(XAppOther.get("法律法规"))

        if (intent != null) {
            lawMainBean = intent.getSerializableExtra("lawBean") as LawMainBean
        }
        queryPostMethod()
        sv_law_search.setSearchListener {
            lawMainBean = LawMainBean(it, 4, 0)
            queryPostMethod()
        }
    }

    /**
     * 请求方法
     */
    private fun queryPostMethod() {
        if (lawMainBean!!.type == 0) {
            rv_law_query.apply {
                layoutManager = LinearLayoutManager(this@LawQueryActivity)
                adapter = keywordListAdapter
            }
            mPresenter.getSearchLaw(ApiParamUtil.lawSearchParam(lawMainBean!!.name))
            sv_law_search.setSearchText(lawMainBean!!.name)
        } else if (lawMainBean!!.type == 1) {
            rv_law_query.apply {
                layoutManager = LinearLayoutManager(this@LawQueryActivity)
                adapter = sortListAdapter
            }
            mPresenter.getLawList(ApiParamUtil.lawSelectParam(UserManager.getUser().departmentCode, UserManager.getUser().id))
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        keywordListAdapter.setOnItemClickListener { adapter, view, position ->
            if (position != 0) {
                val keywordData = keywordDatas.get(position)
                lawMainBean = LawMainBean(keywordData.name, keywordData.id, 2)
                LawDetailActivity.startAction(this, false, lawMainBean!!.id.toString())
            }
        }

        sortListAdapter.setOnItemClickListener { adapter, view, position ->
            if (position != 0) {
                val sortData = sortDatas.get(position)
                lawMainBean = LawMainBean(sortData.name, sortData.id, 2)
                LawDetailActivity.startAction(this, false, lawMainBean!!.id.toString())
            }
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_law_query
    }


    override fun onLawListResult(complainList: List<LawBean>) {
        val childBean = complainList.get(lawMainBean!!.id)
        val lawBean = childBean.children
        if (lawBean != null && lawBean.isNotEmpty()) {
            val bean = LawBean(itemTypeDef = 1, itemName = String.format("共%d条", lawBean.size))
            sortDatas.add(bean)
            lawBean.forEach {
                it.itemTypeDef = 2
                it.itemName = String.format("所属：%s", childBean.name)
                sortDatas.add(it)
            }
        }
        sortListAdapter.setNewData(sortDatas)
    }

    override fun onSearchLawResult(lawSearchLawResult: LawSearchResultBean) {
        val list = lawSearchLawResult.list
        removeKeywordData()
        if (list != null && list.size > 0) {
            val bean = LawSearchBean(itemTypeDef = 1, itemName = String.format("共%d条", lawSearchLawResult.size))
            keywordDatas.add(bean)
            list.forEach {
                it.itemTypeDef = 2
                keywordDatas.add(it)
            }
        }
        keywordListAdapter.setNewData(keywordDatas)
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