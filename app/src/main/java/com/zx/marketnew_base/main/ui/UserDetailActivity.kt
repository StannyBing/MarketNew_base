package com.zx.marketnew_base.main.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.frame.zxmvp.baserx.RxManager
import com.zx.marketnew_base.R
import com.zx.marketnew_base.api.ApiParamUtil
import com.zx.marketnew_base.main.func.adapter.UserInfoAdapter
import com.zx.marketnew_base.main.mvp.contract.UserDetailContract
import com.zx.marketnew_base.main.mvp.model.UserDetailModel
import com.zx.marketnew_base.main.mvp.presenter.UserDetailPresenter
import com.zx.module_library.XApp
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.FileBean
import com.zx.module_library.bean.KeyValueBean
import com.zx.module_library.bean.UserBean
import com.zx.module_library.bean.XAppBean
import com.zx.module_library.func.tool.GlideRoundTransformation
import com.zx.module_library.func.tool.UserManager
import com.zx.zxutils.ZXApp
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXSystemUtil
import com.zx.zxutils.util.ZXTimeUtil
import com.zx.zxutils.util.ZXToastUtil
import com.zx.zxutils.views.ZXStatusBarCompat
import kotlinx.android.synthetic.main.activity_user_detail.*
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：用户详情模块
 */
class UserDetailActivity : BaseActivity<UserDetailPresenter, UserDetailModel>(), UserDetailContract.View {

    var dataBeans = arrayListOf<KeyValueBean>()
    lateinit var listAdapter: UserInfoAdapter
    lateinit var userBean: UserBean
    var filePath: String? = null

    var isChange = false//是否为本人信息修改

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, userBean: UserBean, isChange: Boolean) {
            val intent = Intent(activity, UserDetailActivity::class.java)
            intent.putExtra("userBean", userBean)
            intent.putExtra("isChange", isChange)
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

        toolbar_view.withXApp(XAppBean("", R.color.white, 0, ""))

        userBean = intent.getSerializableExtra("userBean") as UserBean

        isChange = intent.getBooleanExtra("isChange", false)
        if (isChange) {
            toolbar_view.showRightText("保存")
        }

        Glide.with(ZXApp.getContext()).load(BaseConfigModule.BASE_IP + userBean.imgUrl)
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.app_default_headicon)
                        .transform(GlideRoundTransformation(ZXApp.getContext()))
                )
                .transition(DrawableTransitionOptions().crossFade())
                .into(iv_userDetail_head)

        listAdapter = UserInfoAdapter(dataBeans, isChange)
        ll_userDetail_call.visibility = if (isChange) View.GONE else View.VISIBLE
        rv_userDetail_info.layoutManager = LinearLayoutManager(this)
        rv_userDetail_info.adapter = listAdapter

        dataBeans.add(KeyValueBean("姓名", userBean.realName))
        dataBeans.add(KeyValueBean("电话", userBean.telephone))
        dataBeans.add(KeyValueBean("部门", userBean.department))
        dataBeans.add(KeyValueBean("部门座机", userBean.officeTel + ""))
        dataBeans.add(KeyValueBean("职位", userBean.department))

        tv_userDetail_name.setText(userBean.realName);
        tv_userDetail_dept.setText(userBean.remark)
        tv_userDetail_duty.setText(userBean.department)
    }

    /**
     * View事件设置
     */
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewListener() {
        toolbar_view.setRightClickListener {
            ZXDialogUtil.showYesNoDialog(this, "提示", "是否保存") { _, _ ->
                if (TextUtils.isEmpty(filePath)) {
                    save("")
                } else {
                    mPresenter.uploadFile(2, listOf(File(filePath)))
                }
            }
        }

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
        iv_userDetail_head.setOnClickListener {
            if (isChange) {
                getPermission(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)) {
                    XApp.startXApp(RoutePath.ROUTE_LIBRARY_CAMERA, this, 0x02) {
                        it["cameraType"] = 1
                    }
                }
            } else {
                XApp.startXApp(RoutePath.ROUTE_LIBRARY_PREVIEW) {

                    it["name"] = userBean.imgUrl!!.substring(userBean.imgUrl!!.length-3)
                    it["path"] = BaseConfigModule.BASE_IP + userBean.imgUrl
                }
            }
        }
    }

    fun save(url: String) {
        var telephone = ""
        for (info in dataBeans) {
            if (info.key.equals("电话")) {
                telephone = info.value!!
            }
        }
        userBean.telephone = telephone
        if (!TextUtils.isEmpty(url)) {
            userBean.imgUrl = url
        }
        mPresenter.changeUserInfo(ApiParamUtil.changeUserInfoParam(userBean.id, telephone, url))
    }


    override fun onChangeResult() {
        UserManager.setUser(userBean)
        RxManager().post("userInfoChange", userBean)
        ZXToastUtil.showToast("修改成功")
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x02) {//拍摄-录像
            if (data != null) {
                val fileBean = FileBean(fileName = data.getStringExtra("name"), filePath = data.getStringExtra("path"), date = ZXTimeUtil.getCurrentTime())
                filePath = fileBean.filePath
                Glide.with(ZXApp.getContext()).load(filePath)
                        .apply(RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.drawable.app_default_headicon)
                                .transform(GlideRoundTransformation(ZXApp.getContext()))
                        )
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(iv_userDetail_head)
            }
        }
    }

    override fun onFileUploadResult(id: String, paths: String, type: Int) {
        save(paths)
    }
}

