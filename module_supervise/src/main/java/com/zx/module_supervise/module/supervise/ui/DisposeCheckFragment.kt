package com.zx.module_supervise.module.supervise.ui

import android.os.Bundle
import com.zx.module_library.base.BaseFragment
import com.zx.module_supervise.R
import com.zx.module_supervise.module.supervise.mvp.contract.DisposeCheckContract
import com.zx.module_supervise.module.supervise.mvp.model.DisposeCheckModel
import com.zx.module_supervise.module.supervise.mvp.presenter.DisposeCheckPresenter

/**
 * Create By admin On 2017/7/11
 * 功能：监管任务-处置-检查项
 */
class DisposeCheckFragment : BaseFragment<DisposeCheckPresenter, DisposeCheckModel>(), DisposeCheckContract.View {

    private lateinit var fId: String
    private lateinit var fTaskId: String
    private lateinit var fEntityGuid: String
    private lateinit var status: String

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
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }
}
