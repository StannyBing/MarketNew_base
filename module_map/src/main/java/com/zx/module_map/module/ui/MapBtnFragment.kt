package com.zx.module_map.module.ui

import android.os.Bundle
import android.view.View
import com.zx.module_library.base.BaseFragment
import com.zx.module_map.R
import com.zx.module_map.module.mvp.contract.MapBtnContract
import com.zx.module_map.module.mvp.model.MapBtnModel
import com.zx.module_map.module.mvp.presenter.MapBtnPresenter

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MapBtnFragment : BaseFragment<MapBtnPresenter, MapBtnModel>(), MapBtnContract.View {
    companion object {
        /**
         * 启动器
         */
        fun newInstance(): MapBtnFragment {
            val fragment = MapBtnFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_map_btn
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
}
