package com.zx.module_supervise.module.supervise.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseFragment
import com.zx.module_supervise.R
import com.zx.module_supervise.module.supervise.bean.SuperviseCheckBean
import com.zx.module_supervise.module.supervise.func.adapter.SuperviseCheckAdapter
import com.zx.module_supervise.module.supervise.mvp.contract.DetailCheckInfoContract
import com.zx.module_supervise.module.supervise.mvp.model.DetailCheckInfoModel
import com.zx.module_supervise.module.supervise.mvp.presenter.DetailCheckInfoPresenter
import kotlinx.android.synthetic.main.fragment_detail_check_info.*

/**
 * Create By admin On 2017/7/11
 * 功能：监管任务-详情-检查指标
 */
class DetailCheckInfoFragment : BaseFragment<DetailCheckInfoPresenter, DetailCheckInfoModel>(), DetailCheckInfoContract.View {

    private val checkList = arrayListOf<SuperviseCheckBean>()
    private val checkAdapter = SuperviseCheckAdapter(checkList)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(id: String, taskId: String): DetailCheckInfoFragment {
            val fragment = DetailCheckInfoFragment()
            val bundle = Bundle()
            bundle.putString("id", id)
            bundle.putString("taskId", taskId)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_detail_check_info
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        val id = arguments!!.getString("id")
        val taskId = arguments!!.getString("taskId")

        rv_supervise_check.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = checkAdapter
        }

        mPresenter.getCheckList(hashMapOf("fId" to id, "fTaskId" to taskId))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    //检查结果
    override fun onCheckListResult(checkDetailBeans: List<SuperviseCheckBean>) {
        checkList.clear()
        checkList.addAll(checkDetailBeans)
        checkAdapter.notifyDataSetChanged()
    }
}
