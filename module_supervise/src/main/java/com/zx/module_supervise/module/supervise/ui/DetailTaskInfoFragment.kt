package com.zx.module_supervise.module.supervise.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.KeyValueBean
import com.zx.module_supervise.R
import com.zx.module_supervise.module.supervise.bean.TaskInfoBean
import com.zx.module_supervise.module.supervise.func.adapter.DetailInfoAdapter
import com.zx.module_supervise.module.supervise.mvp.contract.DetailTaskInfoContract
import com.zx.module_supervise.module.supervise.mvp.model.DetailTaskInfoModel
import com.zx.module_supervise.module.supervise.mvp.presenter.DetailTaskInfoPresenter
import com.zx.zxutils.util.ZXTimeUtil
import kotlinx.android.synthetic.main.fragment_detail_task_info.*

/**
 * Create By admin On 2017/7/11
 * 功能：专项检查-详情-任务信息
 */
class DetailTaskInfoFragment : BaseFragment<DetailTaskInfoPresenter, DetailTaskInfoModel>(), DetailTaskInfoContract.View {

    private val dataList = arrayListOf<KeyValueBean>()
    private val mAdapter = DetailInfoAdapter(dataList)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DetailTaskInfoFragment {
            val fragment = DetailTaskInfoFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_detail_task_info
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)


        rv_supervise_taskinfo.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = mAdapter
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    fun resetInfo(taskInfoBean: TaskInfoBean) {
        dataList.clear()
        dataList.add(KeyValueBean("任务编号", taskInfoBean.fNum))
        dataList.add(KeyValueBean("任务名称", taskInfoBean.fName))
        dataList.add(KeyValueBean("任务来源", taskInfoBean.fSource))
        dataList.add(KeyValueBean("任务时间", if (taskInfoBean.fStartDate == 0L || taskInfoBean.fStartDate == null) "" else ZXTimeUtil.getTime(taskInfoBean.fStartDate!!)))
        dataList.add(KeyValueBean("办理状态", getStatus(taskInfoBean.fStatus)))
        dataList.add(KeyValueBean("核审人员", taskInfoBean.fReviewUser))
        dataList.add(KeyValueBean("办理部门", taskInfoBean.fHandleDepartment))
        dataList.add(KeyValueBean("任务说明", taskInfoBean.fReamrks))
        mAdapter.notifyDataSetChanged()
    }

    private fun getStatus(status: String?): String {
        return when (status) {
            "001" -> "待下发"
            "002" -> "已下发"
            "003" -> "待指派"
            "004" -> "已指派"
            "005" -> "已办结"
            else -> "未知状态"
        }
    }
}
