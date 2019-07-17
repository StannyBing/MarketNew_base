package com.zx.module_entity.module.entity.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_entity.R
import com.zx.module_entity.module.entity.bean.BusinessBean
import com.zx.module_entity.module.entity.func.adapter.BusinessAdapter
import com.zx.module_entity.module.entity.mvp.contract.DetailBusinessContract
import com.zx.module_entity.module.entity.mvp.model.DetailBusinessModel
import com.zx.module_entity.module.entity.mvp.presenter.DetailBusinessPresenter
import com.zx.module_library.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_entity_detail_business.*

/**
 * Create By admin On 2017/7/11
 * 功能：主体查询-业务信息
 */
class DetailBusinessFragment : BaseFragment<DetailBusinessPresenter, DetailBusinessModel>(), DetailBusinessContract.View {

    private val businessList = arrayListOf<BusinessBean>()
    private val businessAdapter = BusinessAdapter(businessList)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DetailBusinessFragment {
            val fragment = DetailBusinessFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_entity_detail_business
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        rv_detail_business.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = businessAdapter
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        businessAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.tv_business_detail -> {

                }
            }
        }
    }

    fun resetInfo(fEntityGuid: String?) {
        if (fEntityGuid != null) {
            mPresenter.getBusinessInfo(hashMapOf("id" to fEntityGuid))
        }
    }

    override fun onBusinessResult(businessBeans: List<BusinessBean>) {
        this.businessList.clear()
        this.businessList.addAll(businessBeans)
        businessAdapter.notifyDataSetChanged()
    }
}
