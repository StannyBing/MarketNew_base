package com.zx.module_other.module.workplan.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.workplan.mvp.contract.CreatePlanContract
import com.zx.module_other.module.workplan.mvp.model.CreatePlanModel
import com.zx.module_other.module.workplan.mvp.presenter.CreatePlanPresenter
import kotlinx.android.synthetic.main.activity_create_plan.*

class CreatePlanActivity : BaseActivity<CreatePlanPresenter, CreatePlanModel>(), CreatePlanContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, CreatePlanActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun onViewListener() {
        toobar_view.setRightClickListener {
            //            mPresenter.createWorkPlan(ApiParamUtil.createWorkPlanParam("", ""))
            WorkStatisicsActivity.startAction(this, false)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_create_plan
    }

    override fun getCreateWorkResult(result: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

}