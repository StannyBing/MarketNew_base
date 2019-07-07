package com.zx.marketnew_base.main.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.func.adapter.UserInfoAdapter
import com.zx.module_library.func.tool.GlideRoundTransformation
import com.zx.marketnew_base.main.mvp.contract.UserDetailContract
import com.zx.marketnew_base.main.mvp.model.UserDetailModel
import com.zx.marketnew_base.main.mvp.presenter.UserDetailPresenter
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.KeyValueBean
import com.zx.module_library.bean.UserBean
import com.zx.module_library.func.tool.UserManager
import com.zx.zxutils.ZXApp
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXSystemUtil
import com.zx.zxutils.views.ZXStatusBarCompat
import kotlinx.android.synthetic.main.activity_user_detail.*


/**
 * Create By admin On 2017/7/11
 * 功能：用户详情模块
 */
class UserDetailActivity : BaseActivity<UserDetailPresenter, UserDetailModel>(), UserDetailContract.View {

    var dataBeans = arrayListOf<KeyValueBean>()
    lateinit var listAdapter: UserInfoAdapter
    lateinit var userBean: UserBean

    var isUserModify = false//是否为本人信息修改

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, userBean: UserBean) {
            val intent = Intent(activity, UserDetailActivity::class.java)
            intent.putExtra("userBean", userBean)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_user_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ZXStatusBarCompat.setStatusBarDarkMode(this)
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ZXStatusBarCompat.translucent(this)

        userBean = intent.getSerializableExtra("userBean") as UserBean

        isUserModify = userBean.equals(UserManager.getUser())

        Glide.with(ZXApp.getContext()).load(userBean.imgurl)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.app_default_headicon)
                        .transform(GlideRoundTransformation(ZXApp.getContext()))
                )
                .transition(DrawableTransitionOptions().crossFade())
                .into(iv_userDetail_head)

        listAdapter = UserInfoAdapter(dataBeans, isUserModify) {
            toolbar_view.showRightText("保存")
            toolbar_view.setRightClickListener {
                showToast("保存操作")
            }
        }
        ll_userDetail_call.visibility = if (isUserModify) View.GONE else View.VISIBLE
        rv_userDetail_info.layoutManager = LinearLayoutManager(this)
        rv_userDetail_info.adapter = listAdapter

        dataBeans.add(KeyValueBean("组织", "组织组织组织组织组织"))
        dataBeans.add(KeyValueBean("姓名", userBean.realName))
        dataBeans.add(KeyValueBean("电话", userBean.telephone))
        dataBeans.add(KeyValueBean("部门", "部门部门"))
        dataBeans.add(KeyValueBean("部门座机", userBean.officeTel + ""))
        dataBeans.add(KeyValueBean("职位", userBean.department))

        tv_userDetail_name.setText(userBean.realName);
        tv_userDetail_dept.setText(userBean.remark)
        tv_userDetail_duty.setText(userBean.department)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        ll_userDetail_call.setOnClickListener {
            ZXDialogUtil.showListDialog(this, "请选择拨打对象", "取消", arrayListOf("电话", "座机")) { dialog: DialogInterface?, which: Int ->
                ZXDialogUtil.dismissDialog()
                val telephone = if (which == 0) {
                    userBean.telephone
                } else {
                    userBean.officeTel
                }
                ZXDialogUtil.showYesNoDialog(this, "提示", "即将拨打电话\n${telephone}") { _, _ ->
                    ZXSystemUtil.callTo(this, telephone)
                }
            }
        }
    }

}
