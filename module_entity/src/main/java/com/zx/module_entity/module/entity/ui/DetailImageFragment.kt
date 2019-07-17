package com.zx.module_entity.module.entity.ui

import android.os.Bundle
import com.zx.module_entity.R
import com.zx.module_entity.module.entity.bean.EntityDetailBean
import com.zx.module_entity.module.entity.mvp.contract.DetailImageContract
import com.zx.module_entity.module.entity.mvp.model.DetailImageModel
import com.zx.module_entity.module.entity.mvp.presenter.DetailImagePresenter
import com.zx.module_library.base.BaseFragment

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailImageFragment : BaseFragment<DetailImagePresenter, DetailImageModel>(), DetailImageContract.View {
    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DetailImageFragment {
            val fragment = DetailImageFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_entity_detail_image
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

    fun resetInfo(entityDetail: EntityDetailBean) {

    }
}
