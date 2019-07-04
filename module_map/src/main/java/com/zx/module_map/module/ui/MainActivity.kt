package com.zx.module_map.module.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_map.R
import com.zx.module_map.XAppMap
import com.zx.module_map.module.mvp.contract.MainContract
import com.zx.module_map.module.mvp.model.MainModel
import com.zx.module_map.module.mvp.presenter.MainPresenter
import com.zx.zxutils.util.ZXFragmentUtil
import kotlinx.android.synthetic.main.activity_map.*


/**
 * Create By admin On 2017/7/11
 * 功能：地图主页
 */
@Route(path = RoutePath.ROUTE_MAP_MAP)
class MainActivity : BaseActivity<MainPresenter, MainModel>(), MainContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_map
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar_view.withXApp(XAppMap.get("地图"))
        ZXFragmentUtil.addFragment(supportFragmentManager, MapFragment.newInstance(), R.id.fl_map)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
