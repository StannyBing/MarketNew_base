package com.zx.module_supervise.module.task.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.zx.module_library.base.BaseFragment
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.task.func.view.SignatureView
import com.zx.module_supervise.module.task.mvp.contract.DisposeBaseContract
import com.zx.module_supervise.module.task.mvp.model.DisposeBaseModel
import com.zx.module_supervise.module.task.mvp.presenter.DisposeBasePresenter
import com.zx.zxutils.entity.KeyValueEntity
import com.zx.zxutils.util.ZXBitmapUtil
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.fragment_dispose_base.*

/**
 * Create By admin On 2017/7/11
 * 功能：专项检查-处置-基本信息
 */
class DisposeBaseFragment : BaseFragment<DisposeBasePresenter, DisposeBaseModel>(), DisposeBaseContract.View {

    private lateinit var fId: String
    private lateinit var fTaskId: String
    private lateinit var fEntityGuid: String
    private lateinit var status: String

    var disposeType = 0//0同意 1退回

    var bitmapSign1: Bitmap? = null//被检查企业负责人签名bitmap
    var bitmapSign2: Bitmap? = null//检察人员签字bitmap

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DisposeBaseFragment {
            val fragment = DisposeBaseFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_dispose_base
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        fId = activity!!.intent.getStringExtra("fId")
        fTaskId = activity!!.intent.getStringExtra("fTaskId")
        fEntityGuid = activity!!.intent.getStringExtra("fEntityGuid")
        status = activity!!.intent.getStringExtra("status")

        initItem()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //处置方式
        sp_dispose_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (sp_dispose_type.selectedValue.toString() == "0") {
                    tv_dispose_remark.text = "检查意见："
                    disposeType = 0
                    if (status == "101")
                        if (status == "101") ll_dispose_hanlde.visibility = View.VISIBLE
                } else {
                    tv_dispose_remark.text = "退回说明："
                    disposeType = 1
                    if (status == "101") ll_dispose_hanlde.visibility = View.GONE
                }
            }
        }
        //被检查企业负责人签名
        tv_dispose_sign1.setOnClickListener {
            showSignView(1)
        }
        //检查人员签名
        tv_dispose_sign2.setOnClickListener {
            showSignView(2)
        }
    }

    //打开签名弹框
    private fun showSignView(type: Int) {
        val signView = LayoutInflater.from(activity).inflate(R.layout.layout_supervise_sign, null, false)
        val signatureView = signView.findViewById<SignatureView>(R.id.sign_view)
        val tvClear = signView.findViewById<TextView>(R.id.tv_sign_clear)
        val tvSubmit = signView.findViewById<TextView>(R.id.tv_sign_submit)
        val tvCancel = signView.findViewById<TextView>(R.id.tv_sign_cancel)
        tvClear.setOnClickListener { signatureView.clear() }
        tvCancel.setOnClickListener { ZXDialogUtil.dismissDialog() }
        tvSubmit.setOnClickListener {
            if (type == 1) {
                bitmapSign1 = signatureView.buildBitmap()
                iv_dispose_sign1.background = ZXBitmapUtil.bitmapToDrawable(bitmapSign1)
            } else if (type == 2) {
                bitmapSign2 = signatureView.buildBitmap()
                iv_dispose_sign2.background = ZXBitmapUtil.bitmapToDrawable(bitmapSign2)
            }
            ZXDialogUtil.dismissDialog()
        }
        ZXDialogUtil.showCustomViewDialog(activity, "", signView, null, false)
    }

    private fun initItem() {
        if (status == "101") {
            ll_dispose_hanlde.visibility = View.VISIBLE
            //处置方式
            sp_dispose_type.apply {
                showUnderineColor(false)
                setData(arrayListOf<KeyValueEntity>().apply {
                    add(KeyValueEntity("处置", "0"))
                    add(KeyValueEntity("退回", "1"))
                })
                setItemHeightDp(40)
                setItemTextSizeSp(15)
                showSelectedTextColor(true, XAppSupervise.SUPERVISE.moduleColor)
                build()
            }
            //处理结果
//            sp_dispose_result.apply {
//                showUnderineColor(false)
//                setData(arrayListOf<KeyValueEntity>().apply {
//                    add(KeyValueEntity("符合", "符合"))
//                    add(KeyValueEntity("不符合", "不符合"))
//                    add(KeyValueEntity("基本符合", "基本符合"))
//                })
//                setItemHeightDp(40)
//                setItemTextSizeSp(15)
//                showSelectedTextColor(true, XAppSupervise.SUPERVISE.moduleColor)
//                build()
//            }
        } else {
            ll_dispose_hanlde.visibility = View.GONE
            //处置方式
            sp_dispose_type.apply {
                showUnderineColor(false)
                setData(arrayListOf<KeyValueEntity>().apply {
                    add(KeyValueEntity("同意", "0"))
                    add(KeyValueEntity("不同意", "1"))
                })
                setItemHeightDp(40)
                setItemTextSizeSp(15)
                showSelectedTextColor(true, XAppSupervise.SUPERVISE.moduleColor)
                build()
            }
        }
    }

    fun checkItem(): Boolean {
        if (status == "101" && disposeType == 0) {
            if (bitmapSign1 == null) {
                showToast("请被检查企业负责人签名")
                return false
            } else if (bitmapSign2 == null) {
                showToast("请检查人员签名")
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    fun getDisposeInfo(): HashMap<String, Any> {
        if (status != "101") {
            return hashMapOf("fId" to fId, "fStatus" to status, "fQualify" to sp_dispose_type.selectedKey, "fResult" to et_dispose_remark.text.toString())
        } else {
            if (disposeType == 0) {//通过
                return hashMapOf("fId" to fId, "fQualify" to if (rb_dispose_value0.isChecked) "符合" else if (rb_dispose_value1.isChecked) "不符合" else  "基本符合", "fResult" to et_dispose_remark.text.toString())
            } else {//退回
                return hashMapOf("fTaskId" to fTaskId, "fEntityGuidList" to arrayListOf(fEntityGuid), "fResultList" to arrayListOf(et_dispose_remark.text.toString()))
            }
        }
    }
}
