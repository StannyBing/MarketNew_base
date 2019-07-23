package com.zx.module_supervise.module.supervise.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.ConstStrings
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.toJson
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.supervise.mvp.contract.SuperviseDisposeContract
import com.zx.module_supervise.module.supervise.mvp.model.SuperviseDisposeModel
import com.zx.module_supervise.module.supervise.mvp.presenter.SuperviseDisposePresenter
import com.zx.zxutils.util.ZXBitmapUtil
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXFileUtil
import kotlinx.android.synthetic.main.activity_supervise_dispose.*


/**
 * Create By admin On 2017/7/11
 * 功能：专项检查-任务处置
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_SUPERVISE_DISPOSE)
class SuperviseDisposeActivity : BaseActivity<SuperviseDisposePresenter, SuperviseDisposeModel>(), SuperviseDisposeContract.View {

    private var disposeBaseFragment: DisposeBaseFragment? = null
    private var disposeCheckFragment: DisposeCheckFragment? = null

    private lateinit var fId: String
    private lateinit var fTaskId: String
    private lateinit var fEntityGuid: String
    private lateinit var status: String

    private var enterpriseSign = ""//被检查企业负责人签名id
    private var checkSign = ""//检察人员签字id

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, fId: String, fTaskId: String, fEntityGuid: String, status: String) {
            val intent = Intent(activity, SuperviseDisposeActivity::class.java)
            intent.putExtra("fId", fId)
            intent.putExtra("fTaskId", fTaskId)
            intent.putExtra("fEntityGuid", fEntityGuid)
            intent.putExtra("status", status)
            activity.startActivityForResult(intent, 0x01)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_supervise_dispose
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        fId = intent.getStringExtra("fId")
        fTaskId = intent.getStringExtra("fTaskId")
        fEntityGuid = intent.getStringExtra("fEntityGuid")
        status = intent.getStringExtra("status")

        toolBar_view.withXApp(XAppSupervise.get("专项检查"))
        btn_supervise_submit.background.setTint(ContextCompat.getColor(this, XAppSupervise.get("专项检查")!!.moduleColor))

        tvp_supervise_dispose.setManager(supportFragmentManager)
                .setIndicatorHeight(5)
                .setTablayoutHeight(40)
                .setTabScrollable(false)
                .setTitleColor(R.color.text_color_noraml, R.color.text_color_noraml)
                .setIndicatorColor(ContextCompat.getColor(this, XAppSupervise.get("专项检查")!!.moduleColor))
                .setTablayoutBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setTabTextSize(resources.getDimension(R.dimen.text_size_normal).toInt())
                .addTab(DisposeBaseFragment.newInstance().apply { disposeBaseFragment = this }, "基本信息")
        if (status == "101") tvp_supervise_dispose.addTab(DisposeCheckFragment.newInstance().apply { disposeCheckFragment = this }, "检查内容")
        tvp_supervise_dispose.build()

        if (status != "101") {
            tvp_supervise_dispose.tabLayout.visibility = View.GONE
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        btn_supervise_submit.setOnClickListener {
            when (status) {
                "101" -> {
                    if (disposeBaseFragment!!.disposeType == 0) {
                        if (disposeBaseFragment!!.checkItem()) {
                            if (!disposeCheckFragment!!.checkItem()) {
                                showToast("请先完成检查内容")
                                tvp_supervise_dispose.setSelectOn(1)
                            } else {
                                ZXDialogUtil.showYesNoDialog(this, "提示", "是否提交处置结果？") { _, _ ->
                                    getPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                        onFileUploadResult("", "", 0)
                                    }
                                }
                            }
                        }
                    } else {
                        ZXDialogUtil.showYesNoDialog(this, "提示", "是否提交处置结果？") { _, _ ->
                            mPresenter.submitBack(disposeBaseFragment!!.getDisposeInfo().toJson())
                        }
                    }
                }
                else -> {
                    if (disposeBaseFragment!!.checkItem()) {
                        ZXDialogUtil.showYesNoDialog(this, "提示", "是否提交处置结果？") { _, _ ->
                            mPresenter.submitAudit(disposeBaseFragment!!.getDisposeInfo().toJson())
                        }
                    }
                }
            }
        }
    }

    //提交成功
    override fun onSubmitResult() {
        showToast("提交成功")
        setResult(0x01)
        finish()
    }

    //文件上传回调
    override fun onFileUploadResult(id: String, paths: String, type: Int) {
        when (type) {
            0 -> {
                val file = ZXFileUtil.createNewFile(ConstStrings.getCachePath() + "signTemp1.png")
                ZXBitmapUtil.bitmapToFile(disposeBaseFragment!!.bitmapSign1, file)
                mPresenter.uploadFile(1, file)
            }
            1 -> {
                enterpriseSign = paths
                val file = ZXFileUtil.createNewFile(ConstStrings.getCachePath() + "signTemp2.png")
                ZXBitmapUtil.bitmapToFile(disposeBaseFragment!!.bitmapSign2, file)
                mPresenter.uploadFile(2, file)
            }
            2 -> {
                checkSign = paths
                val map = disposeBaseFragment!!.getDisposeInfo()
                map.putAll(disposeCheckFragment!!.getDisposeInfo())
                map["fEntitySign"] = enterpriseSign
                map["fCheckSign"] = checkSign
                mPresenter.submitDispose(map.toJson())
            }
        }
    }

}
