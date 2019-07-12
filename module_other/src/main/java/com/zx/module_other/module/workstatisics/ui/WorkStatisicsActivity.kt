package com.zx.module_other.module.workplan.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.workplan.mvp.contract.WorkStatisicsContract
import com.zx.module_other.module.workplan.mvp.model.WorkStatisicsModel
import com.zx.module_other.module.workplan.mvp.presenter.WorkStatisicsPresenter
import com.zx.module_other.module.workstatisics.bean.WorkStatisicsBean
import com.zx.zxutils.views.ZXStatusBarCompat
import kotlinx.android.synthetic.main.activity_work_statisics.*

@Route(path = RoutePath.ROUTE_OTHER_STATISICSACTIVITY)
class WorkStatisicsActivity : BaseActivity<WorkStatisicsPresenter, WorkStatisicsModel>(), WorkStatisicsContract.View {


    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, WorkStatisicsActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun onViewListener() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_work_statisics
    }

    @SuppressLint("ResourceAsColor")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ZXStatusBarCompat.translucent(this, R.color.work_satisics_bg)
        ZXStatusBarCompat.setStatusBarDarkMode(this)
        mPresenter.getWorkStatisicsList(ApiParamUtil.workStatisicsPlanParam("4"))
    }

    override fun getWorkStatisicsResult(workStatisicsDatas: List<WorkStatisicsBean>) {
        var nums = arrayListOf<Int>()
        var dates = arrayListOf<String>()
        for (workStatisics in workStatisicsDatas) {
            nums.add(workStatisics.num)
            dates.add(workStatisics.date)
        }
        sv_statistics.setDatas(nums, dates)
    }
}