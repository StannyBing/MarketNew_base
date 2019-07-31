package com.zx.marketnew_base.system.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.FuncBean
import com.zx.marketnew_base.main.func.adapter.FuncAdapter
import com.zx.marketnew_base.system.mvp.contract.SettingContract
import com.zx.marketnew_base.system.mvp.model.SettingModel
import com.zx.marketnew_base.system.mvp.presenter.SettingPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.UserManager
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.activity_setting.*


/**
 * Create By admin On 2017/7/11
 * 功能：设置
 */
@Route(path = RoutePath.ROUTE_APP_SETTING)
class SettingActivity : BaseActivity<SettingPresenter, SettingModel>(), SettingContract.View {

    var dataBeans = arrayListOf<FuncBean>()
    var listAdapter = FuncAdapter(dataBeans)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, SettingActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        rv_setting.apply {
            layoutManager = LinearLayoutManager(this@SettingActivity)
            adapter = listAdapter
        }
        dataBeans.add(FuncBean("录像设置", R.drawable.app_func_video, true))
        dataBeans.add(FuncBean("修改密码", R.drawable.app_func_law, true))
        dataBeans.add(FuncBean("退出登录", R.drawable.app_func_setting))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //菜单点击事件
        listAdapter.setOnItemClickListener { adapter, view, position ->
            when (dataBeans[position].title) {
                "录像设置"->{
                    VideoSettingActivity.startAction(this, false)
                }
                "修改密码" -> {
                    ForgetPwdActivity.startAction(this, false, UserManager.getUser().telephone!!)
                }
                "退出登录" -> {
                    ZXDialogUtil.showYesNoDialog(this, "提示", "是否退出登录？") { dialog, which ->
                        UserManager.loginOut()
                        JPushInterface.stopPush(this)
                        LoginActivity.startAction(this, false)
                    }
                }
                else->{
                    showToast("正在开发中")
                }
            }
        }
    }

}
