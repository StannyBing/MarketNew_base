package com.zx.module_supervise.module.supervise.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseFragment
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.supervise.bean.DetailDynamicBean
import com.zx.module_supervise.module.supervise.func.adapter.DynamicAdapter
import com.zx.module_supervise.module.supervise.mvp.contract.DetailDynamicContract
import com.zx.module_supervise.module.supervise.mvp.model.DetailDynamicModel
import com.zx.module_supervise.module.supervise.mvp.presenter.DetailDynamicPresenter
import kotlinx.android.synthetic.main.fragment_detail_dynamic.*

/**
 * Create By admin On 2017/7/11
 * 功能：监管任务-详情-处置动态
 */
class DetailDynamicFragment : BaseFragment<DetailDynamicPresenter, DetailDynamicModel>(), DetailDynamicContract.View {

    private val dataList = arrayListOf<DetailDynamicBean>()
    private val dynamicAdapter = DynamicAdapter(dataList)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DetailDynamicFragment {
            val fragment = DetailDynamicFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_detail_dynamic
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        dataList.add(DetailDynamicBean())

        rv_supervise_dynamic.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = dynamicAdapter.apply {
                setModuleColor(ContextCompat.getColor(activity!!, XAppSupervise.get("监管任务")!!.moduleColor))
            }
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    override fun onDynamicListResult(dynamicDetailBeans: List<DetailDynamicBean>) {
        dataList.clear()
        dataList.add(DetailDynamicBean())
        dataList.addAll(dynamicDetailBeans)
        dynamicAdapter.notifyDataSetChanged()
    }

    fun resetInfo(taskId: String, fEntityGuid: String) {
        mPresenter.getDynamicList(hashMapOf("pageNo" to "1", "pageSize" to "999", "fTaskId" to taskId, "fEntityGuid" to fEntityGuid))
    }
}
