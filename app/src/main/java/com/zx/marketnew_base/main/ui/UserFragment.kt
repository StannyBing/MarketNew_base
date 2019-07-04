package com.zx.marketnew_base.main.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.FuncBean
import com.zx.marketnew_base.main.func.adapter.FuncAdapter
import com.zx.marketnew_base.main.mvp.contract.UserContract
import com.zx.marketnew_base.main.mvp.model.UserModel
import com.zx.marketnew_base.main.mvp.presenter.UserPresenter
import com.zx.marketnew_base.system.ui.SettingActivity
import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.func.tool.GlideRoundTransformation
import com.zx.module_library.func.tool.UserManager
import com.zx.zxutils.ZXApp
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * Create By admin On 2017/7/11
 * 功能：我的
 */
class UserFragment : BaseFragment<UserPresenter, UserModel>(), UserContract.View {

    var dataBeans = arrayListOf<FuncBean>()
    var listAdapter = FuncAdapter(dataBeans)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): UserFragment {
            val fragment = UserFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_user
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        Glide.with(ZXApp.getContext()).load(R.drawable.app_default_headicon)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.app_default_headicon)
                        .transform(GlideRoundTransformation(ZXApp.getContext()))
                )
                .transition(DrawableTransitionOptions().crossFade())
                .into(iv_user_head)

        rv_user_func.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }
        dataBeans.add(FuncBean("个人工作计划", R.drawable.app_func_workplan))
        dataBeans.add(FuncBean("工作成果", R.drawable.app_func_workhistry, true))
        dataBeans.add(FuncBean("政务资讯", R.drawable.app_func_info))
        dataBeans.add(FuncBean("三会一课", R.drawable.app_func_dang))
        dataBeans.add(FuncBean("法律法规", R.drawable.app_func_law, true))
        dataBeans.add(FuncBean("设置", R.drawable.app_func_setting))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //修改按钮点击事件
        iv_user_modify.setOnClickListener { activity?.let { it1 -> UserDetailActivity.startAction(it1, false, UserManager.getUser()) } }
        //菜单点击事件
        listAdapter.setOnItemClickListener { adapter, view, position ->
            when(dataBeans[position].title){
                "个人工作计划"->{}//个人工作计划
                "工作成果"->{}//工作成果
                "政务资讯"->{}//政务资讯
                "三会一课"->{}//三会一课
                "法律法规"->{
                    XApp.startXApp(RoutePath.ROUTE_OTHER_LAW)
                }//法律法规
                "设置"->{
                    SettingActivity.startAction(activity!!, false)
                }//设置
                else->{
                    showToast("正在开发中")
                }
            }
        }
    }
}
