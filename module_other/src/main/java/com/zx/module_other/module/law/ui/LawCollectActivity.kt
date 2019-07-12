package com.zx.module_other.module.law.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.law.bean.*
import com.zx.module_other.module.law.func.adapter.LawCollectAdapter
import com.zx.module_other.module.law.func.adapter.LawQueryListAdapter
import com.zx.module_other.module.law.mvp.contract.LawCollectContract
import com.zx.module_other.module.law.mvp.model.LawCollectModel
import com.zx.module_other.module.law.mvp.presenter.LawCollectPresenter
import kotlinx.android.synthetic.main.activity_law_collect.*

class LawCollectActivity : BaseActivity<LawCollectPresenter, LawCollectModel>(), LawCollectContract.View {

    private var collectDatas = arrayListOf<LawCollectBean>()
    private var lawCollectAdapter = LawCollectAdapter(collectDatas)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, LawCollectActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun onViewListener() {
        lawCollectAdapter.setOnItemClickListener { adapter, view, position ->
            LawDetailActivity.startAction(this, false, collectDatas.get(position)!!.lawMenuId.toString())
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_law_collect;
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toobar_view.withXApp(XAppOther.get("法律法规"))
        rv_law_collect.apply {
            layoutManager = GridLayoutManager(this@LawCollectActivity, 2)
            adapter = lawCollectAdapter
        }
    }

    override fun onLawCollectResult(lawCollectResultBean: LawCollectResultBean?) {
        val list = lawCollectResultBean!!.list
        collectDatas.clear()
        collectDatas.addAll(list!!)
        lawCollectAdapter.setNewData(collectDatas)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getCollectList(ApiParamUtil.lawMyCollectParam("oynkBwtUWJ2tFcS5s19RofvkfTs8"))
    }
}