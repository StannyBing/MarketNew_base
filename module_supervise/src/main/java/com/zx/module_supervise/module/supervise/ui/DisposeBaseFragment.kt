package com.zx.module_supervise.module.supervise.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.func.tool.toJson
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.supervise.func.view.SignatureView
import com.zx.module_supervise.module.supervise.mvp.contract.DisposeBaseContract
import com.zx.module_supervise.module.supervise.mvp.model.DisposeBaseModel
import com.zx.module_supervise.module.supervise.mvp.presenter.DisposeBasePresenter
import com.zx.zxutils.entity.KeyValueEntity
import com.zx.zxutils.util.ZXBitmapUtil
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.fragment_dispose_base.*
import okhttp3.RequestBody

/**
 * Create By admin On 2017/7/11
 * 功能：监管任务-处置-基本信息
 */
class DisposeBaseFragment : BaseFragment<DisposeBasePresenter, DisposeBaseModel>(), DisposeBaseContract.View {

    private lateinit var fId: String
    private lateinit var fTaskId: String
    private lateinit var fEntityGuid: String
    private lateinit var status: String

    private var enterpriseSign = ""//被检查企业负责人签名id
    private var checkSign = ""//检察人员签字id
    private var bitmapSign1: Bitmap? = null//被检查企业负责人签名bitmap
    private var bitmapSign2: Bitmap? = null//检察人员签字bitmap

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
                if (status == "101") {
                    ll_dispose_hanlde.visibility = if (sp_dispose_type.selectedValue.toString() == "0") {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }
        //被检查负责人签名
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
        val tvSubmit = signView.findViewById<ImageView>(R.id.tv_sign_submit)
        val tvCancel = signView.findViewById<ImageView>(R.id.tv_sign_cancel)
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
                showSelectedTextColor(true, XAppSupervise.get("监管任务")!!.moduleColor)
                build()
            }
            //处理结果
            sp_dispose_result.apply {
                showUnderineColor(false)
                setData(arrayListOf<KeyValueEntity>().apply {
                    add(KeyValueEntity("符合", "符合"))
                    add(KeyValueEntity("不符合", "不符合"))
                    add(KeyValueEntity("基本符合", "基本符合"))
                })
                setItemHeightDp(40)
                setItemTextSizeSp(15)
                showSelectedTextColor(true, XAppSupervise.get("监管任务")!!.moduleColor)
                build()
            }
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
                showSelectedTextColor(true, XAppSupervise.get("监管任务")!!.moduleColor)
                build()
            }
        }
    }

    fun checkItem(): Boolean {
        if (status == "101") {
            if (enterpriseSign.isEmpty()) {
                showToast("请被检查企业负责人签名")
                return false
            } else if (checkSign.isEmpty()) {
                showToast("请检查人员签名")
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    fun getDisposeInfo(): RequestBody {
        if (status != "101") {
            return hashMapOf("fId" to fId, "fStatus" to status, "fQualify" to sp_dispose_type.selectedKey, "fResult" to et_dispose_remark.text.toString()).toJson()
        } else {
            return hashMapOf("fId" to fId, "fStatus" to status, "fQualify" to "", "fResult" to "").toJson()
        }
    }
}
