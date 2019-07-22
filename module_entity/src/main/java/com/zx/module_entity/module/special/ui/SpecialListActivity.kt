package com.zx.module_entity.module.special.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_entity.R
import com.zx.module_entity.XAppEntity
import com.zx.module_entity.module.entity.bean.EntityBean
import com.zx.module_entity.module.entity.func.adapter.EntityListAdapter
import com.zx.module_entity.module.entity.ui.DetailActivity
import com.zx.module_entity.module.special.mvp.contract.SpecialListContract
import com.zx.module_entity.module.special.mvp.model.SpecialListModel
import com.zx.module_entity.module.special.mvp.presenter.SpecialListPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_library.func.tool.UserManager
import com.zx.module_library.func.tool.animateToTop
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import kotlinx.android.synthetic.main.activity_special_list.*


/**
 * Create By admin On 2017/7/11
 * 功能：特殊主体-记录
 */
@Route(path = RoutePath.ROUTE_ENTITY_SPECIALLIST)
class SpecialListActivity : BaseActivity<SpecialListPresenter, SpecialListModel>(), SpecialListContract.View {

    private var pageNo = 1
    private var dataBeans = arrayListOf<EntityBean>()
    private var mAdapter = EntityListAdapter(dataBeans)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, SpecialListActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_special_list
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolBar_view.withXApp(XAppEntity.get("特殊主体"))

        tv_entity_tips.setTextColor(ContextCompat.getColor(this, XAppEntity.get("特殊主体")!!.moduleColor))

        sr_special_list.setLayoutManager(LinearLayoutManager(this))
                .setAdapter(mAdapter)
                .autoLoadMore()
                .setPageSize(15)
                .setSRListener(object : ZXSRListener<EntityBean> {
                    override fun onItemLongClick(item: EntityBean?, position: Int) {
                    }

                    override fun onLoadMore() {
                        pageNo++
                        loadData()
                    }

                    override fun onRefresh() {
                        loadData(true)
                    }

                    override fun onItemClick(item: EntityBean?, position: Int) {
                        DetailActivity.startAction(this@SpecialListActivity, false, item!!.fEntityGuid!!, true)
                    }

                })
        loadData(true)
    }

    /**
     * 数据加载
     */
    private fun loadData(refresh: Boolean = false) {
        if (refresh) {
            pageNo = 1
            sr_special_list.clearStatus()
        }
        mPresenter.getEntityList(hashMapOf("pageNo" to pageNo.toString(), "pageSize" to 15.toString(), "fInsertPerson" to UserManager.getUser().id))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //顶部点击滚动到开头
        toolBar_view.setMidClickListener { sr_special_list.recyclerView.animateToTop(0) }
    }

    override fun onEntityListResult(entityList: NormalList<EntityBean>) {
        tv_entity_tips.text = "检索到特殊主体共${entityList.total}条"
        sr_special_list.refreshData(entityList.list, entityList.total)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01) {
            loadData(true)
        }
    }

}
