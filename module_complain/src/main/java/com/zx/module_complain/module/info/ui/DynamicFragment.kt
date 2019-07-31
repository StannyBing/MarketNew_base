package com.zx.module_complain.module.info.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_complain.R
import com.zx.module_complain.XAppComplain
import com.zx.module_complain.module.info.bean.DetailBean
import com.zx.module_complain.module.info.func.adapter.DynamicAdapter
import com.zx.module_complain.module.info.mvp.contract.DynamicContract
import com.zx.module_complain.module.info.mvp.model.DynamicModel
import com.zx.module_complain.module.info.mvp.presenter.DynamicPresenter
import com.zx.module_library.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_complain_dynamic.*

/**
 * Create By admin On 2017/7/11
 * 功能：投诉举报动态
 */
class DynamicFragment : BaseFragment<DynamicPresenter, DynamicModel>(), DynamicContract.View {

    private val dataList = arrayListOf<DetailBean.StatusInfo>()
    private val dynamicAdapter = DynamicAdapter(dataList)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DynamicFragment {
            val fragment = DynamicFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_complain_dynamic
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        dataList.add(DetailBean.StatusInfo())
        rv_complain_dynamic.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = dynamicAdapter.apply {
                setModuleColor(ContextCompat.getColor(activity!!, XAppComplain.LIST.moduleColor))
            }
        }
    }


    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    fun reSetInfo(dynamicList: List<DetailBean.StatusInfo>?) {
        if (dynamicList != null) {
            dataList.clear()
            dynamicAdapter.addData(DetailBean.StatusInfo())
            dynamicAdapter.addData(dynamicList)
        }
    }
}
