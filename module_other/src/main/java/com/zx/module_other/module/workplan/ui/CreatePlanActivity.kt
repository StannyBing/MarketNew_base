package com.zx.module_other.module.workplan.ui

import android.app.Activity
import android.content.Intent
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.module.workplan.mvp.contract.CreatePlanContract
import com.zx.module_other.module.workplan.mvp.model.CreatePlanModel
import com.zx.module_other.module.workplan.mvp.presenter.CreatePlanPresenter

class CreatePlanActivity: BaseActivity<CreatePlanPresenter, CreatePlanModel>(),CreatePlanContract.View{

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}