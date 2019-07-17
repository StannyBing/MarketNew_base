package com.zx.module_entity.module.entity.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_entity.R
import com.zx.module_entity.module.entity.bean.CreditBean
import com.zx.module_entity.module.entity.func.adapter.CreditAdapter
import com.zx.module_entity.module.entity.mvp.contract.DetailCreditContract
import com.zx.module_entity.module.entity.mvp.model.DetailCreditModel
import com.zx.module_entity.module.entity.mvp.presenter.DetailCreditPresenter
import com.zx.module_library.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_entity_detail_credit.*

/**
 * Create By admin On 2017/7/11
 * 功能：主体查询-信用信息
 */
class DetailCreditFragment : BaseFragment<DetailCreditPresenter, DetailCreditModel>(), DetailCreditContract.View {

    private val creditList = arrayListOf<CreditBean>()
    private val creditAdapter = CreditAdapter(creditList)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DetailCreditFragment {
            val fragment = DetailCreditFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_entity_detail_credit
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        rv_detail_credit.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = creditAdapter
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        creditAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.tv_credit_year -> {
                    //TODO
                }
                R.id.tv_credit_all -> {
                    //TODO
                }
            }
        }
    }

    fun resetInfo(fEntityGuid: String?) {
        if (fEntityGuid != null) {
            mPresenter.getCreditInfo(hashMapOf("id" to fEntityGuid))
        }
    }

    override fun onCreditListResult(creditList: List<CreditBean>) {
        this.creditList.clear()
        this.creditList.addAll(creditList)
        creditAdapter.notifyDataSetChanged()
    }
}
