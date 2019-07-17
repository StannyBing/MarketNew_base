package com.zx.module_entity.module.entity.ui

import android.os.Bundle
import com.zx.module_entity.R
import com.zx.module_entity.module.entity.mvp.contract.DetailBusinessContract
import com.zx.module_entity.module.entity.mvp.model.DetailBusinessModel
import com.zx.module_entity.module.entity.mvp.presenter.DetailBusinessPresenter
import com.zx.module_library.base.BaseFragment

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailBusinessFragment : BaseFragment<DetailBusinessPresenter, DetailBusinessModel>(), DetailBusinessContract.View {
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
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    fun resetInfo(fEntityGuid: String?) {

    }
}
