package com.zx.module_other.module.law.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_library.func.tool.UserManager
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.law.bean.LawCollectBean
import com.zx.module_other.module.law.func.adapter.LawCollectAdapter
import com.zx.module_other.module.law.mvp.contract.LawCollectContract
import com.zx.module_other.module.law.mvp.model.LawCollectModel
import com.zx.module_other.module.law.mvp.presenter.LawCollectPresenter
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import kotlinx.android.synthetic.main.activity_law_collect.*

@Route(path = RoutePath.ROUTE_OTHER_LAW_COLLECT)
class LawCollectActivity : BaseActivity<LawCollectPresenter, LawCollectModel>(), LawCollectContract.View {
    private var pageNo = 1
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
        toobar_view.withXApp(XAppOther.LAW)
        sr_law_collect.setLayoutManager(GridLayoutManager(this@LawCollectActivity, 2))
                .setAdapter(lawCollectAdapter)
                .autoLoadMore()
                .setPageSize(15)
                .setSRListener(object : ZXSRListener<LawCollectBean> {
                    override fun onItemLongClick(item: LawCollectBean?, position: Int) {
                    }

                    override fun onLoadMore() {
                        pageNo++
                        loadData()
                    }

                    override fun onRefresh() {
                        loadData(true)
                    }

                    override fun onItemClick(item: LawCollectBean?, position: Int) {
                        LawDetailActivity.startAction(this@LawCollectActivity, false, item!!.lawMenuId.toString())
                    }

                })
    }


    fun loadData(refresh: Boolean = false) {
        if (refresh) {
            pageNo = 1
            sr_law_collect.clearStatus()
        }
        mPresenter.getCollectList(ApiParamUtil.lawMyCollectParam(UserManager.getUser().id, pageNo, 15))
    }

    override fun onLawCollectResult(lawCollectResultBean: NormalList<LawCollectBean>?) {
        sr_law_collect.refreshData(lawCollectResultBean!!.list, lawCollectResultBean.total)
    }

    override fun onResume() {
        super.onResume()
        loadData(true)
    }
}