package com.zx.module_other.module.workplan.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.module.workplan.mvp.model.WorkStatisicsModel
import com.zx.module_other.module.workplan.mvp.presenter.WorkStatisicsPresenter
import com.zx.zxutils.views.ZXStatusBarCompat
import kotlinx.android.synthetic.main.activity_work_statisics.*

class WorkStatisicsActivity : BaseActivity<WorkStatisicsPresenter, WorkStatisicsModel>() {

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
        ZXStatusBarCompat.translucent(this,R.color.work_satisics_bg)
        ZXStatusBarCompat.setStatusBarDarkMode(this)
        var data= arrayListOf<Int>()
        data.add(20)
        data.add(32)
        data.add(37)
        data.add(54)
        sv_statistics.setDatas(data, arrayListOf("1月2日","1月2日","1月2日","1月2日"))
    }
}