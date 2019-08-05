package com.zx.module_other.module.checkin.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther

import com.zx.module_other.module.checkin.mvp.contract.CheckinContract
import com.zx.module_other.module.checkin.mvp.model.CheckinModel
import com.zx.module_other.module.checkin.mvp.presenter.CheckinPresenter
import kotlinx.android.synthetic.main.activity_checkin.*


/**
 * Create By admin On 2017/7/11
 * 功能：考勤打卡
 */
@Route(path = RoutePath.ROUTE_OTHER_CHECKIN)
class CheckinActivity : BaseActivity<CheckinPresenter, CheckinModel>(), CheckinContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, CheckinActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_checkin
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar_view.withXApp(XAppOther.CHECKIN)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
