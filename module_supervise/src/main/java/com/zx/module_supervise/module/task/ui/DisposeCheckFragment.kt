package com.zx.module_supervise.module.task.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseFragment
import com.zx.module_supervise.R
import com.zx.module_supervise.module.task.bean.TaskCheckBean
import com.zx.module_supervise.module.task.func.adapter.TaskCheckAdapter
import com.zx.module_supervise.module.task.mvp.contract.DisposeCheckContract
import com.zx.module_supervise.module.task.mvp.model.DisposeCheckModel
import com.zx.module_supervise.module.task.mvp.presenter.DisposeCheckPresenter
import kotlinx.android.synthetic.main.fragment_dispose_check.*

/**
 * Create By admin On 2017/7/11
 * 功能：专项检查-处置-检查项
 */
class DisposeCheckFragment : BaseFragment<DisposeCheckPresenter, DisposeCheckModel>(), DisposeCheckContract.View {

    private lateinit var fId: String
    private lateinit var fTaskId: String
    private lateinit var fEntityGuid: String
    private lateinit var status: String

    private val checkList = arrayListOf<TaskCheckBean>()
    private val checkAdapter = TaskCheckAdapter(checkList)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DisposeCheckFragment {
            val fragment = DisposeCheckFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_dispose_check
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        fId = activity!!.intent.getStringExtra("fId")
        fTaskId = activity!!.intent.getStringExtra("fTaskId")
        fEntityGuid = activity!!.intent.getStringExtra("fEntityGuid")
        status = activity!!.intent.getStringExtra("status")

        rv_dispose_check.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = checkAdapter
        }

        mPresenter.getCheckList(hashMapOf("fId" to fId, "fTaskId" to fTaskId))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    //检查指标项
    fun checkItem(): Boolean {
        if (checkList.isNotEmpty()) {
            checkList.forEach {
                if (it.fCheckResult.isNullOrEmpty()) {
                    return false
                }
            }
        }
        return true
    }

    //获取检查结果
    fun getDisposeInfo(): HashMap<String, Any> {
        return hashMapOf("resultList" to arrayListOf<TaskCheckBean.ResultBean>().apply {
            if (checkList.isNotEmpty()) {
                checkList.forEach {
                    add(TaskCheckBean.ResultBean(it.fId, it.fCheckResult ?: ""))
                }
            }
        })
    }

    //检查列表
    override fun onCheckListResult(checkDetailBeans: List<TaskCheckBean>) {
        checkList.clear()
        checkList.addAll(checkDetailBeans)
        checkAdapter.notifyDataSetChanged()
    }
}
