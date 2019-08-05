package com.zx.module_supervise.module.daily.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zx.module_library.base.BaseFragment
import com.zx.module_supervise.R
import com.zx.module_supervise.module.daily.mvp.contract.DailyCheckContract
import com.zx.module_supervise.module.daily.mvp.model.DailyCheckModel
import com.zx.module_supervise.module.daily.mvp.presenter.DailyCheckPresenter
import com.zx.module_supervise.module.task.bean.TaskCheckBean
import com.zx.module_supervise.module.task.func.adapter.TaskCheckAdapter
import kotlinx.android.synthetic.main.fragment_daily_check.*

/**
 * Create By admin On 2017/7/11
 * 功能：现场检查-检查内容
 */
class DailyCheckFragment : BaseFragment<DailyCheckPresenter, DailyCheckModel>(), DailyCheckContract.View {

    val checkList = arrayListOf<TaskCheckBean>()
    private val checkAdapter = TaskCheckAdapter(checkList)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(dailyId: String): DailyCheckFragment {
            val fragment = DailyCheckFragment()
            val bundle = Bundle()
            bundle.putString("dailyId", dailyId)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_daily_check
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        rv_daily_check.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = checkAdapter
        }

        if (arguments!!.getString("dailyId").isNotEmpty()) {
            ll_daily_model.visibility = View.GONE
            checkAdapter.isEdit = false
            mPresenter.getDailyCheckList(hashMapOf("id" to arguments!!.getString("dailyId")))
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //设置检查模板
        ll_daily_model.setOnClickListener {
            TemplateActivity.startAction(this@DailyCheckFragment)
        }
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
    fun getDailyInfo(): HashMap<String, Any> {
        return hashMapOf("list" to arrayListOf<TaskCheckBean.ResultBean>().apply {
            if (checkList.isNotEmpty()) {
                checkList.forEach {
                    add(TaskCheckBean.ResultBean(it.fId, it.fCheckResult ?: ""))
                }
            }
        })
    }

    override fun onCheckListResult(checkList: List<TaskCheckBean>) {
        this.checkList.clear()
        addCheckList(checkList)
        checkAdapter.notifyDataSetChanged()
    }

    private fun addCheckList(checkList: List<TaskCheckBean>) {
        if (checkList.isNotEmpty()) {
            checkList.forEach {
                if (it.fIsLeaf == "0") {
                    this.checkList.add(it)
                } else {
                    if (it.children != null && it.children!!.isNotEmpty()) {
                        addCheckList(it.children!!)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01 && data != null) {
            tv_daily_model.text = "点击更换检查模板-${data.getStringExtra("templateName")}"
            val id = data.getStringExtra("templateId")
            mPresenter.getCheckList(hashMapOf("id" to id))
        }
    }
}
