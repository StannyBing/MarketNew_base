package com.zx.module_other.module.law.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.module.law.bean.LawMainBean
import com.zx.module_other.module.law.func.adapter.LawMainAdapter
import com.zx.module_other.module.law.func.adapter.LawRecentlyNotifyAdapter
import com.zx.module_other.module.law.mvp.contract.LawContract
import com.zx.module_other.module.law.mvp.model.LawModel
import com.zx.module_other.module.law.mvp.presenter.LawPresenter
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import kotlinx.android.synthetic.main.activity_law.*


/**
 * Create By admin On 2017/7/11
 * 功能：法律法规
 */
@Route(path = RoutePath.ROUTE_OTHER_LAW)
class LawActivity : BaseActivity<LawPresenter, LawModel>(), LawContract.View {
    var keywordData = arrayListOf<LawMainBean>()
    var keywordAdapter = LawMainAdapter(keywordData);

    var sortData = arrayListOf<LawMainBean>()
    var typeAdapter = LawMainAdapter(sortData)

    var notifyData = arrayListOf<LawMainBean>()
    var notifyAdapter = LawRecentlyNotifyAdapter(notifyData)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, LawActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_law
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar_view.withXApp(XAppOther.get("法律法规"))
        searchView.withXApp(XAppOther.get("法律法规"))

        rv_law_keyword.apply {
            layoutManager = GridLayoutManager(this@LawActivity, 2)
            adapter = keywordAdapter
        }
        rv_law_type.apply {
            layoutManager = GridLayoutManager(this@LawActivity, 2)
            adapter = typeAdapter
        }
        rv_law_list.apply {
            layoutManager = LinearLayoutManager(this@LawActivity)
            adapter = notifyAdapter
        }
        searchView.setSearchListener {
            val lawMainBean = LawMainBean(it, 4, 0)
            LawQueryActivity.startAction(this, false, lawMainBean)
        }
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        addData(this.resources.getStringArray(R.array.keywordName), this.resources.getIntArray(R.array.keywordId), 0, keywordData, keywordAdapter)
        addData(this.resources.getStringArray(R.array.sortName), this.resources.getIntArray(R.array.sortId), 1, sortData, typeAdapter)
        addData(this.resources.getStringArray(R.array.notifyName), this.resources.getIntArray(R.array.notifyId), 2, notifyData, notifyAdapter)
    }

    /**
     * 添加数据源
     */
    private fun addData(dataSource: Array<String>, id: IntArray, type: Int, data: ArrayList<LawMainBean>, adapter: ZXQuickAdapter<LawMainBean, ZXBaseHolder>) {
        if (dataSource.isNotEmpty()) {
            for ((index, e) in dataSource.withIndex()) {
                data.add(LawMainBean(e, id[index], type))
            }
            adapter.setNewData(data)
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        keywordAdapter.setOnItemClickListener { adapter, view, position ->
            LawQueryActivity.startAction(this, false, keywordData.get(position))
        }

        typeAdapter.setOnItemClickListener { adapter, view, position ->
            LawQueryActivity.startAction(this, false, sortData.get(position))
        }

        notifyAdapter.setOnItemClickListener { adapter, view, position ->
            if (position != 0) {
                LawDetailActivity.startAction(this, false, notifyData.get(position).id.toString())
            }
        }

    }

}
