package com.zx.marketnew_base.system.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.EditText
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.R
import com.zx.marketnew_base.api.ApiParamUtil
import com.zx.marketnew_base.system.bean.VerifiCodeBean
import com.zx.marketnew_base.system.mvp.contract.ForgetPwdContract
import com.zx.marketnew_base.system.mvp.model.ForgetPwdModel
import com.zx.marketnew_base.system.mvp.presenter.ForgetPwdPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXFormatCheckUtil
import com.zx.zxutils.util.ZXSystemUtil
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * Create By admin On 2019/3/5
 * 功能：找回密码
 */
@Route(path = RoutePath.ROUTE_APP_FORGETPWD)
class ForgetPwdActivity : BaseActivity<ForgetPwdPresenter, ForgetPwdModel>(), ForgetPwdContract.View {

    var verifiBean: VerifiCodeBean? = null

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, phone: String) {
            val intent = Intent(activity, ForgetPwdActivity::class.java)
            intent.putExtra("phone", phone)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_forget_pwd
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val phone = intent.getStringExtra("phone")
        if (phone.length == 11 && ZXFormatCheckUtil.isAllNum(phone)) {
            et_forget_phone.setText(phone)
            btn_forget_send.background = ContextCompat.getDrawable(this, R.drawable.mybutton_shape)
            btn_forget_send.tag = "normal"
        } else {
            et_forget_phone.setText("")
            btn_forget_send.background = ContextCompat.getDrawable(this, R.drawable.myuselessbutton_shape)
            btn_forget_send.tag = "useless"
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        et_forget_phone.addTextChangedListener(ForgetTextWatcher(et_forget_phone))
        et_forget_code1.addTextChangedListener(ForgetTextWatcher(et_forget_code1))
        et_forget_code2.addTextChangedListener(ForgetTextWatcher(et_forget_code2))
        et_forget_code3.addTextChangedListener(ForgetTextWatcher(et_forget_code3))
        et_forget_code4.addTextChangedListener(ForgetTextWatcher(et_forget_code4))

        //发送按钮点击事件
        btn_forget_send.setOnClickListener {
            when (btn_forget_send.tag) {
                "normal" -> {
                    mPresenter.sendSms(ApiParamUtil.verifiCodeParam(et_forget_phone.text.toString()))
                }
                "useless" -> {
                    showToast("请先输入正确的手机号")
                }
            }
        }

        //帮助电话点击事件
        ll_forget_help.setOnClickListener {
            ZXDialogUtil.showYesNoDialog(this, "提示", "是否拨打电话：\n${tv_forget_helptel.text}") { dialogInterface: DialogInterface, i: Int ->
                ZXSystemUtil.callTo(this, tv_forget_helptel.text.toString())
            }
        }
    }

    inner class ForgetTextWatcher(val editText: EditText) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (editText) {
                et_forget_phone -> {
                    if (et_forget_phone.text.length == 11) {
                        btn_forget_send.background = ContextCompat.getDrawable(this@ForgetPwdActivity, R.drawable.mybutton_shape)
                        btn_forget_send.tag = "normal"
                    } else {
                        btn_forget_send.background = ContextCompat.getDrawable(this@ForgetPwdActivity, R.drawable.myuselessbutton_shape)
                        btn_forget_send.tag = "useless"
                    }
                    verifiBean = null
                }
                //验证码1
                et_forget_code1 -> if (et_forget_code1.text.length == 1) {
                    et_forget_code2.requestFocus()
                }
                //验证码2
                et_forget_code2 -> if (et_forget_code2.text.length == 1) {
                    et_forget_code3.requestFocus()
                } else {
                    et_forget_code1.requestFocus()
                }
                //验证码3
                et_forget_code3 -> if (et_forget_code3.text.length == 1) {
                    et_forget_code4.requestFocus()
                } else {
                    et_forget_code2.requestFocus()
                }
                //验证码4
                et_forget_code4 -> if (et_forget_code4.text.length == 1) {
                    verifiCode("${et_forget_code1.text}${et_forget_code2.text}${et_forget_code3.text}${et_forget_code4.text}")
                } else {
                    et_forget_code3.requestFocus()
                }
            }
        }
    }

    /**
     * 验证 验证码
     */
    private fun verifiCode(code: String) {
        if (verifiBean == null) {
            showToast("请先发送验证码")
        } else if ((System.currentTimeMillis() - verifiBean!!.time) / 1000 > 5 * 60) {
            showToast("验证码超时，请重新申请")
        } else if (code.equals(verifiBean!!.code)) {
            showToast("验证成功")
            ChangePwdActivity.startAction(this, false, verifiBean!!.userId)
        } else {
            showToast("验证码错误，请重新输入")
            val translateAnimation = TranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f)
            translateAnimation.interpolator = CycleInterpolator(3.0f)
            translateAnimation.duration = 400
            ll_forget_code.startAnimation(translateAnimation)
        }
        et_forget_code4.setText("")
        et_forget_code3.setText("")
        et_forget_code2.setText("")
        et_forget_code1.setText("")
        try {
            ZXSystemUtil.closeKeybord(this)
        } catch (e: Exception) {
        }
    }

    /**
     * 短信发送回调
     */
    override fun onSmsSendResult(codeBean: VerifiCodeBean) {
        verifiBean = codeBean
        Observable.interval(0, 1, TimeUnit.SECONDS)//0秒延迟。每隔一秒发送一条数据
                .subscribeOn(Schedulers.io())//观察者在主线程
                .take(60 + 1)//设置循环61次
                .map {
                    return@map 60 - it
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    //设置开始订阅时的操作
                    showToast("验证码发送成功，请注意接收")
                    btn_forget_send.tag = "countdown"
                    btn_forget_send.background = ContextCompat.getDrawable(this, R.drawable.myuselessbutton_shape)
                    et_forget_code4.setText("")
                    et_forget_code3.setText("")
                    et_forget_code2.setText("")
                    et_forget_code1.setText("")
                }
                .subscribe({
                    //onnext
                    btn_forget_send.text = "重新发送(${it}s)"
                }, {}, {
                    //oncomplete
                    btn_forget_send.text = "发送"
                    btn_forget_send.tag = "normal"
                    btn_forget_send.background = ContextCompat.getDrawable(this, R.drawable.mybutton_shape)
                })
    }
}
