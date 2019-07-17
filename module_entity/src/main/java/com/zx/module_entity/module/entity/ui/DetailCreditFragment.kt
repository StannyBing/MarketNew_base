package com.zx.module_entity.module.entity.ui

import android.os.Bundle
import com.zx.module_entity.R
import com.zx.module_entity.module.entity.mvp.contract.DetailCreditContract
import com.zx.module_entity.module.entity.mvp.model.DetailCreditModel
import com.zx.module_entity.module.entity.mvp.presenter.DetailCreditPresenter
import com.zx.module_library.base.BaseFragment

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailCreditFragment : BaseFragment<DetailCreditPresenter, DetailCreditModel>(), DetailCreditContract.View {
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
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    fun resetInfo(fEntityGuid: String?) {

    }
}
