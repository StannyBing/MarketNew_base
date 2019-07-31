package com.zx.module_other.module.law.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.frame.zxmvp.base.BaseModel
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.module.law.bean.LawStandardQueryBean
import com.zx.module_other.module.law.func.util.util
import com.zx.module_other.module.law.mvp.presenter.LawStandardPresenter
import kotlinx.android.synthetic.main.activity_standard_detail.*

class StandardDetailActivity : BaseActivity<LawStandardPresenter, BaseModel>() {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, lawStandardQueryBean: LawStandardQueryBean) {
            val intent = Intent(activity, StandardDetailActivity::class.java)
            intent.putExtra("lawStandardQueryBean", lawStandardQueryBean)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_standard_detail
    }

    override fun onViewListener() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar_view.withXApp(XAppOther.LAW)
        val lawStandrdQueryBean: LawStandardQueryBean = intent.getSerializableExtra("lawStandardQueryBean") as LawStandardQueryBean;
        lawStandrdQueryBean.type?.let { toolbar_view.setMidText(it) }
        tv_tandard_Illegal.setText(lawStandrdQueryBean.illegalAct?.let { util.getHtmlText(it) })
        tv_tandard_qualitative.setText(lawStandrdQueryBean.violateLaw?.let { util.getHtmlText(it) })
        tv_tandard_punish.setText(lawStandrdQueryBean.punishLaw?.let { util.getHtmlText(it) })
        tv_tandard_discretion.setText(lawStandrdQueryBean.discretionStandard?.let { util.getHtmlText(it) })
    }

}