package com.zx.module_other.module.papers.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther

import com.zx.module_other.module.papers.mvp.contract.PapersContract
import com.zx.module_other.module.papers.mvp.model.PapersModel
import com.zx.module_other.module.papers.mvp.presenter.PapersPresenter
import kotlinx.android.synthetic.main.activity_papers.*


/**
 * Create By admin On 2017/7/11
 * 功能：公文流转
 */
@Route(path = RoutePath.ROUTE_OTHER_PAPERS)
class PapersActivity : BaseActivity<PapersPresenter, PapersModel>(), PapersContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, PapersActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_papers
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar_view.withXApp(XAppOther.PAPERS)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
