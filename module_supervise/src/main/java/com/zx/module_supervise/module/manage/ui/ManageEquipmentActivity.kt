package com.zx.module_supervise.module.manage.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.manage.mvp.contract.ManageEquipmentContract
import com.zx.module_supervise.module.manage.mvp.model.ManageEquipmentModel
import com.zx.module_supervise.module.manage.mvp.presenter.ManageEquipmentPresenter
import kotlinx.android.synthetic.main.activity_manage_equipment.*


/**
 * Create By admin On 2017/7/11
 * 功能：特种设备监管
 */
@Route(path = RoutePath.ROUTE_SUPERVISE_MANAGE_EQUIPMENT)
class ManageEquipmentActivity : BaseActivity<ManageEquipmentPresenter, ManageEquipmentModel>(), ManageEquipmentContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, ManageEquipmentActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_manage_equipment
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar_view.withXApp(XAppSupervise.EQUIPMENT)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
