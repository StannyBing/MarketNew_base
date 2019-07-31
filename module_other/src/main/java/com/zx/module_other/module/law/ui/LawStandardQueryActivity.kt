package com.zx.module_other.module.law.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.law.bean.LawBean
import com.zx.module_other.module.law.bean.LawMainBean
import com.zx.module_other.module.law.bean.LawStandardQueryBean
import com.zx.module_other.module.law.bean.LawStandardQueryResultBean
import com.zx.module_other.module.law.func.adapter.LawQueryListAdapter
import com.zx.module_other.module.law.func.adapter.LawStandardQueryAdapter
import com.zx.module_other.module.law.mvp.contract.LawStandardQueryContract
import com.zx.module_other.module.law.mvp.model.LawStandardModel
import com.zx.module_other.module.law.mvp.presenter.LawStandardPresenter
import kotlinx.android.synthetic.main.activity_law_standard_query.*

class LawStandardQueryActivity : BaseActivity<LawStandardPresenter, LawStandardModel>(), LawStandardQueryContract.View {

    private var standardDatas = arrayListOf<LawStandardQueryBean>()
    private var standardAdapter = LawStandardQueryAdapter(standardDatas)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, LawStandardQueryActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun onViewListener() {
        sv_law_standard_search.setSearchListener {
            mPresenter.getStandardData(ApiParamUtil.lawStandardParam(it))
        }
        standardAdapter.setOnItemClickListener { adapter, view, position ->
            StandardDetailActivity.startAction(this, false, standardDatas.get(position))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_law_standard_query
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toobar_view.withXApp(XAppOther.LAW)
        rv_law_standard_query.apply {
            layoutManager = LinearLayoutManager(this@LawStandardQueryActivity)
            adapter = standardAdapter
        }
        mPresenter.getStandardData(ApiParamUtil.lawStandardParam(""))
    }

    override fun onLawStandardResult(lawStandardQueryResultBean: LawStandardQueryResultBean?) {
        var list = lawStandardQueryResultBean!!.list;
        standardDatas.clear()
        val bean = LawStandardQueryBean(itemTypeDef = 1, illegalAct = String.format("共%d条", lawStandardQueryResultBean.size))
        standardDatas.add(bean)
        if (list != null && list.size > 0) {
            list.forEach {
                it.itemTypeDef = 2
                standardDatas.add(it)
            }
        }
        standardAdapter.setNewData(standardDatas)
    }

}