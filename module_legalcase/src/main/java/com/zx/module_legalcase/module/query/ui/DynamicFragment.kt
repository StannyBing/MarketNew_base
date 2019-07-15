package com.zx.module_legalcase.module.query.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_legalcase.R
import com.zx.module_legalcase.XAppLegalcase
import com.zx.module_legalcase.api.ApiParamUtil
import com.zx.module_legalcase.module.query.bean.DynamicBean
import com.zx.module_legalcase.module.query.func.adapter.DynamicAdapter
import com.zx.module_legalcase.module.query.mvp.contract.DynamicContract
import com.zx.module_legalcase.module.query.mvp.model.DynamicModel
import com.zx.module_legalcase.module.query.mvp.presenter.DynamicPresenter
import com.zx.module_library.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_dynamic.*

/**
 * Create By admin On 2017/7/11
 * 功能：综合执法-详情-流程轨迹
 */
class DynamicFragment : BaseFragment<DynamicPresenter, DynamicModel>(), DynamicContract.View {

    private val dataList = arrayListOf<DynamicBean>()
    private val dynamicAdapter = DynamicAdapter(dataList)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(id : String): DynamicFragment {
            val fragment = DynamicFragment()
            val bundle = Bundle()
            bundle.putString("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_dynamic
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        dataList.add(DynamicBean())
        rv_legalcase_dynamic.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = dynamicAdapter.apply {
                setModuleColor(ContextCompat.getColor(activity!!, XAppLegalcase.get("综合执法")!!.moduleColor))
            }
        }

        mPresenter.getDynamic(ApiParamUtil.dynamicParam(arguments!!.getString("id")))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    override fun onDynamicResult(dynamicList: List<DynamicBean>) {
        dynamicAdapter.addData(dynamicList)
    }
}
