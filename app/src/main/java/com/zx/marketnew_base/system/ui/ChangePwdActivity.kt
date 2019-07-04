package com.zx.marketnew_base.system.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.R
import com.zx.marketnew_base.api.ApiParamUtil
import com.zx.marketnew_base.system.mvp.contract.ChangePwdContract
import com.zx.marketnew_base.system.mvp.model.ChangePwdModel
import com.zx.marketnew_base.system.mvp.presenter.ChangePwdPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.UserManager
import kotlinx.android.synthetic.main.activity_change_pwd.*


/**
 * Create By admin On 2019/3/6
 * 功能：修改密码
 */
@Route(path = RoutePath.ROUTE_APP_CHANGEPWD)
class ChangePwdActivity : BaseActivity<ChangePwdPresenter, ChangePwdModel>(), ChangePwdContract.View {

    var id = ""

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, id: String) {
            val intent = Intent(activity, ChangePwdActivity::class.java)
            intent.putExtra("id", id)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_change_pwd
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        id = intent.getStringExtra("id")
        btn_change_next.tag = false
        btn_change_next.background = ContextCompat.getDrawable(this@ChangePwdActivity, R.drawable.myuselessbutton_shape)

    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //设置删除按钮的可见性
        et_change_pwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_change_pwd.text.toString().length == 0) {
                    ll_change_deletepwd.visibility = View.GONE
                    btn_change_next.tag = false
                    btn_change_next.background = ContextCompat.getDrawable(this@ChangePwdActivity, R.drawable.myuselessbutton_shape)
                } else if (et_change_pwd.text.toString().length < 6 || et_change_pwd.text.toString().length > 12) {
                    ll_change_deletepwd.visibility = View.VISIBLE
                    tv_change_hint.setTextColor(ContextCompat.getColor(this@ChangePwdActivity, R.color.red_color_normal))
                    btn_change_next.tag = false
                    btn_change_next.background = ContextCompat.getDrawable(this@ChangePwdActivity, R.drawable.myuselessbutton_shape)
                } else {
                    ll_change_deletepwd.visibility = View.VISIBLE
                    tv_change_hint.setTextColor(ContextCompat.getColor(this@ChangePwdActivity, R.color.colorPrimary))
                    btn_change_next.tag = true
                    btn_change_next.background = ContextCompat.getDrawable(this@ChangePwdActivity, R.drawable.mybutton_shape)
                }
            }
        })
        //设置删除按钮点击事件
        ll_change_deletepwd.setOnClickListener { et_change_pwd.setText("") }
        //设置密码可见按钮点击事件
        ll_change_showpwd.setOnClickListener {
            if (iv_change_showpwd.tag == null || iv_change_showpwd.tag.toString().isEmpty() || "hide".equals(iv_change_showpwd.tag.toString())) {
                iv_change_showpwd.background = ContextCompat.getDrawable(this, R.drawable.app_change_showpwd)
                iv_change_showpwd.tag = "show"
                et_change_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                iv_change_showpwd.background = ContextCompat.getDrawable(this, R.drawable.app_change_hidepwd)
                iv_change_showpwd.tag = "hide"
                et_change_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            et_change_pwd.setSelection(et_change_pwd.text.toString().length)
        }
        //下一步按钮点击事件
        btn_change_next.setOnClickListener { if (btn_change_next.tag as Boolean) mPresenter.changePwd(ApiParamUtil.changePwdParam(id, et_change_pwd.text.toString())) }
    }

    /**
     * 密码修改成功
     */
    override fun onChangeResult() {
        showToast("密码修改成功，请重新登录")
        UserManager.loginOut()
        LoginActivity.startAction(this, true)
    }

}
