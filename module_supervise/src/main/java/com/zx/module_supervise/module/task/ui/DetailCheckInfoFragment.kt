package com.zx.module_supervise.module.task.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseFragment
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.task.bean.TaskCheckBean
import com.zx.module_supervise.module.task.func.adapter.TaskCheckAdapter
import com.zx.module_supervise.module.task.mvp.contract.DetailCheckInfoContract
import com.zx.module_supervise.module.task.mvp.model.DetailCheckInfoModel
import com.zx.module_supervise.module.task.mvp.presenter.DetailCheckInfoPresenter
import kotlinx.android.synthetic.main.fragment_detail_check_info.*

/**
 * Create By admin On 2017/7/11
 * 功能：专项检查-详情-检查指标
 */
class DetailCheckInfoFragment : BaseFragment<DetailCheckInfoPresenter, DetailCheckInfoModel>(), DetailCheckInfoContract.View {

    private val checkList = arrayListOf<TaskCheckBean>()
    private val checkAdapter = TaskCheckAdapter(checkList, XAppSupervise.SUPERVISE.moduleColor)

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
            checkAdapter.isEdit = false
            adapter = checkAdapter
        }

        mPresenter.getCheckList(hashMapOf("fId" to id, "fTaskId" to taskId))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    fun setfStatus(status: String?) {
        checkAdapter.isShow = status != "101"
        checkAdapter.notifyDataSetChanged()
    }

    //检查结果
    override fun onCheckListResult(checkDetailBeans: List<TaskCheckBean>) {
        checkList.clear()
        checkList.addAll(checkDetailBeans)
        checkAdapter.notifyDataSetChanged()
    }
}
