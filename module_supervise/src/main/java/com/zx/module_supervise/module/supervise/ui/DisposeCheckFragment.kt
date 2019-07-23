package com.zx.module_supervise.module.supervise.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseFragment
import com.zx.module_supervise.R
import com.zx.module_supervise.module.supervise.bean.SuperviseCheckBean
import com.zx.module_supervise.module.supervise.func.adapter.SuperviseCheckAdapter
import com.zx.module_supervise.module.supervise.mvp.contract.DisposeCheckContract
import com.zx.module_supervise.module.supervise.mvp.model.DisposeCheckModel
import com.zx.module_supervise.module.supervise.mvp.presenter.DisposeCheckPresenter
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

    private val checkList = arrayListOf<SuperviseCheckBean>()
    private val checkAdapter = SuperviseCheckAdapter(checkList)

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
        return hashMapOf("resultList" to arrayListOf<SuperviseCheckBean.ResultBean>().apply {
            if (checkList.isNotEmpty()) {
                checkList.forEach {
                    add(SuperviseCheckBean.ResultBean(it.fId, it.fCheckResult ?: ""))
                }
            }
        })
    }

    //检查列表
    override fun onCheckListResult(checkDetailBeans: List<SuperviseCheckBean>) {
        checkList.clear()
        checkList.addAll(checkDetailBeans)
        checkAdapter.notifyDataSetChanged()
    }
}
