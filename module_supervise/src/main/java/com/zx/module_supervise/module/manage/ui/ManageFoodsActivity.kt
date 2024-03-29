package com.zx.module_supervise.module.manage.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.manage.mvp.contract.ManageFoodsContract
import com.zx.module_supervise.module.manage.mvp.model.ManageFoodsModel
import com.zx.module_supervise.module.manage.mvp.presenter.ManageFoodsPresenter
import kotlinx.android.synthetic.main.activity_manage_foods.*


/**
 * Create By admin On 2017/7/11
 * 功能：食品安全监管
 */
@Route(path = RoutePath.ROUTE_SUPERVISE_MANAGE_FOODS)
class ManageFoodsActivity : BaseActivity<ManageFoodsPresenter, ManageFoodsModel>(), ManageFoodsContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, ManageFoodsActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_manage_foods
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar_view.withXApp(XAppSupervise.FOODS)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
