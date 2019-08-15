package com.zx.module_other.module.workplan.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.frame.zxmvp.baserx.RxManager
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.workplan.func.util.DateUtil
import com.zx.module_other.module.workplan.mvp.contract.CreatePlanContract
import com.zx.module_other.module.workplan.mvp.model.CreatePlanModel
import com.zx.module_other.module.workplan.mvp.presenter.CreatePlanPresenter
import kotlinx.android.synthetic.main.activity_create_plan.*

@Route(path = RoutePath.ROUTE_OTHER_PLAN_CREATE)
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
            if (!TextUtils.isEmpty(et_work_content.text.toString())) {
                mPresenter.createWorkPlan(ApiParamUtil.createWorkPlanParam(et_work_content.text.toString(), DateUtil.timeStringToStamp2(tv_create_date.text.toString()).toString()))
            } else {
                showToast("内容不能为空！！")
            }
//            WorkStatisicsActivity.startAction(this, false)
        }
        mc_create_plan.setOnCalendarChangedListener { baseCalendar, year, month, localDate ->
            tv_create_date.setText(localDate.toString())
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_create_plan
    }

    override fun getCreateWorkResult() {
        RxManager().post("createplan", "")
        showToast("创建成功")
        finish()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toobar_view.withXApp(XAppOther.PLAN)
    }

}