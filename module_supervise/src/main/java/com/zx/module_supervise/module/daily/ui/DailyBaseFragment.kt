package com.zx.module_supervise.module.daily.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.FileBean
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.daily.bean.DailyDetailBean
import com.zx.module_supervise.module.daily.bean.EntityBean
import com.zx.module_supervise.module.daily.mvp.contract.DailyBaseContract
import com.zx.module_supervise.module.daily.mvp.model.DailyBaseModel
import com.zx.module_supervise.module.daily.mvp.presenter.DailyBasePresenter
import com.zx.module_supervise.module.task.func.view.SignatureView
import com.zx.zxutils.util.ZXBitmapUtil
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.fragment_daily_base.*

/**
 * Create By admin On 2017/7/11
 * 功能：现场检查详情
 */
@SuppressLint("NewApi")
class DailyBaseFragment : BaseFragment<DailyBasePresenter, DailyBaseModel>(), DailyBaseContract.View {

    var bitmapSign1: Bitmap? = null//被检查企业负责人签名bitmap
    var bitmapSign2: Bitmap? = null//检察人员签字bitmap

    var entityBean: EntityBean? = null

    private var dailyId = ""

    companion object {
        /**
         * 启动器
         */
        fun newInstance(dailyId: String): DailyBaseFragment {
            val fragment = DailyBaseFragment()
            val bundle = Bundle()
            bundle.putString("dailyId", dailyId)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_daily_base
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        dailyId = arguments!!.getString("dailyId")

        file_view.withXApp(XAppSupervise.DAILY)
        file_view.setModifiable(dailyId.isEmpty())

        if (entityBean != null) {
            tv_daily_entity.text = "主体信息（点击切换主体）"
            tv_daily_entityName.text = entityBean?.fEntityName
            tv_daily_entityPerson.text = entityBean?.fLegalPerson
            tv_daily_entityContract.text = entityBean?.fContactInfo
            tv_daily_entityAddress.text = entityBean?.fAddress
            et_daily_checkName.hint = entityBean?.fEntityName
        }

        if (dailyId.isNotEmpty()) {
            mPresenter.getDailyDetail(hashMapOf("id" to dailyId))
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //被检查企业负责人签名
        tv_daily_sign1.setOnClickListener {
            showSignView(1)
        }
        //检查人员签名
        tv_daily_sign2.setOnClickListener {
            showSignView(2)
        }

        tv_daily_entity.setOnClickListener {
            if (dailyId.isEmpty()) {
                (activity as DailyAddActivity).showEntityDilaog()
            }
        }
    }

    fun checkItem(): Boolean {
        if (entityBean == null) {
            showToast("请选择检查主体")
            return false
        } else if (bitmapSign1 == null) {
            showToast("请被检查企业负责人签名")
            return false
        } else if (bitmapSign2 == null) {
            showToast("请检查人员签名")
            return false
        } else {
            return true
        }
    }

    fun getDailyInfo(): HashMap<String, Any> {
        return hashMapOf("enterpriseId" to entityBean!!.fEntityGuid!!,
                "name" to if (et_daily_checkName.text.toString().isEmpty()) entityBean!!.fEntityName!! else et_daily_checkName.text.toString(),
                "result" to if (rb_check_value3.isChecked) {
                    "3"
                } else if (rb_check_value3.isChecked) {
                    "4"
                } else if (rb_check_value5.isChecked) {
                    "5"
                } else if (rb_check_value6.isChecked) {
                    "6"
                } else if (rb_check_value7.isChecked) {
                    "7"
                } else if (rb_check_value8.isChecked) {
                    "8"
                } else "",
                "remark" to et_daily_remark.text.toString())
    }

    //打开签名弹框
    private fun showSignView(type: Int) {
        val signView = LayoutInflater.from(activity).inflate(R.layout.layout_daily_sign, null, false)
        val signatureView = signView.findViewById<SignatureView>(R.id.sign_view)
        val tvClear = signView.findViewById<TextView>(R.id.tv_sign_clear)
        val tvSubmit = signView.findViewById<TextView>(R.id.tv_sign_submit)
        val tvCancel = signView.findViewById<TextView>(R.id.tv_sign_cancel)
        tvCancel.setOnClickListener { signatureView.clear() }
        tvClear.setOnClickListener { ZXDialogUtil.dismissDialog() }
        tvSubmit.setOnClickListener {
            if (type == 1) {
                bitmapSign1 = signatureView.buildBitmap()
                iv_daily_sign1.background = ZXBitmapUtil.bitmapToDrawable(bitmapSign1)
            } else if (type == 2) {
                bitmapSign2 = signatureView.buildBitmap()
                iv_daily_sign2.background = ZXBitmapUtil.bitmapToDrawable(bitmapSign2)
            }
            ZXDialogUtil.dismissDialog()
        }
        ZXDialogUtil.showCustomViewDialog(activity, "", signView, null, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        file_view.onActivityResult(requestCode, resultCode, data)
    }

    //检查详情
    override fun onDailyDetailResult(dailyDetailBean: DailyDetailBean) {
        //设置主体信息
        setEntityInfo(EntityBean(fEntityGuid = dailyDetailBean.fEntityGuid,
                fEntityName = dailyDetailBean.fEntityName,
                fLegalPerson = dailyDetailBean.fLegalPerson,
                fContactInfo = dailyDetailBean.fContactInfo,
                fAddress = dailyDetailBean.fAddress))
        //设置检查信息
        et_daily_remark.hint = ""
        et_daily_remark.setText(dailyDetailBean.remark)
        et_daily_remark.isClickable = false
        et_daily_remark.isFocusableInTouchMode = false
        ll_daily_checkName.visibility = View.GONE
        divider_checkName.visibility = View.GONE
        for (i in 0 until rg_check_value.childCount) {
            rg_check_value.getChildAt(i).isEnabled = false
        }
        when (dailyDetailBean.result) {
//            0 -> rg_check_value.check(R.id.rb_check_value0)
//            1 -> rg_check_value.check(R.id.rb_check_value1)
//            2 -> rg_check_value.check(R.id.rb_check_value2)
            3 -> rg_check_value.check(R.id.rb_check_value3)
            4 -> rg_check_value.check(R.id.rb_check_value4)
            5 -> rg_check_value.check(R.id.rb_check_value5)
            6 -> rg_check_value.check(R.id.rb_check_value6)
            7 -> rg_check_value.check(R.id.rb_check_value7)
            8 -> rg_check_value.check(R.id.rb_check_value8)
        }
        //设置附件信息
        if (dailyDetailBean.enterpriseSign != null) {
            Glide.with(this).load(BaseConfigModule.BASE_IP + dailyDetailBean.enterpriseSign).into(iv_daily_sign1)
        }
        if (dailyDetailBean.checkSign != null) {
            Glide.with(this).load(BaseConfigModule.BASE_IP + dailyDetailBean.checkSign).into(iv_daily_sign2)
        }
        if (dailyDetailBean.attachment != null && dailyDetailBean.attachment!!.isNotEmpty()) {
            val fileBean = arrayListOf<FileBean>()
            dailyDetailBean.attachment!!.forEach {
                fileBean.add(FileBean(it.id, it.realName, BaseConfigModule.BASE_IP + it.savePath, dateLong = it.saveDate))
            }
            file_view.setDatas(fileBean)
        }
        tv_daily_sign1.visibility = View.GONE
        tv_daily_sign2.visibility = View.GONE
    }

    fun setEntityInfo(entityBean: EntityBean) {
        this.entityBean = entityBean
        if (tv_daily_entityName != null) {
            tv_daily_entity.text = "主体信息" + if (dailyId.isEmpty()) "（点击切换主体）" else ""
            tv_daily_entityName.text = entityBean.fEntityName
            tv_daily_entityPerson.text = entityBean.fLegalPerson
            tv_daily_entityContract.text = entityBean.fContactInfo
            tv_daily_entityAddress.text = entityBean.fAddress
            et_daily_checkName.hint = entityBean.fEntityName
        }
    }
}
