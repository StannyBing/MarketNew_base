package com.zx.module_legalcase.module.query.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_legalcase.R
import com.zx.module_legalcase.XAppLegalcase
import com.zx.module_legalcase.api.ApiParamUtil
import com.zx.module_legalcase.module.query.bean.DetailBean
import com.zx.module_legalcase.module.query.mvp.contract.DetailContract
import com.zx.module_legalcase.module.query.mvp.model.DetailModel
import com.zx.module_legalcase.module.query.mvp.presenter.DetailPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.activity_detail.*


/**
 * Create By admin On 2017/7/11
 * 功能：案件执法-详情
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_LEGALCASE_DETAIL)
class DetailActivity : BaseActivity<DetailPresenter, DetailModel>(), DetailContract.View {

    private var id: String = ""
    private lateinit var caseInfoFragment: DetailInfoFragment
    private lateinit var entityInfoFragment: DetailInfoFragment
    private lateinit var fileInfoFragment: FileInfoFragment
    private lateinit var dynamicFragment: DynamicFragment

    private val optList = arrayListOf<String>()
    private var detailBean: DetailBean? = null

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, id: String, optable: Boolean = false) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("optable", optable)
            activity.startActivityForResult(intent, 0x01)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolBar_view.withXApp(XAppLegalcase.get("案件执法"))
        toolBar_view.setMidText("详情")

        id = intent.getStringExtra("id")

        tvp_legalcase_detail.setManager(supportFragmentManager)
                .setIndicatorHeight(5)
                .setTablayoutHeight(40)
                .setTabScrollable(false)
                .setTitleColor(R.color.text_color_noraml, R.color.text_color_noraml)
                .setIndicatorColor(ContextCompat.getColor(this, XAppLegalcase.get("案件执法")!!.moduleColor))
                .setTablayoutBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setTabTextSize(resources.getDimension(R.dimen.text_size_normal).toInt())
                .addTab(DetailInfoFragment.newInstance(0).apply { caseInfoFragment = this }, "案件信息")
                .addTab(DetailInfoFragment.newInstance(1).apply { entityInfoFragment = this }, "当事人")
                .addTab(FileInfoFragment.newInstance().apply { fileInfoFragment = this }, "附件")
                .addTab(DynamicFragment.newInstance(id).apply { dynamicFragment = this }, "流程轨迹")
                .build()

        btn_legalcase_dispose.background.setTint(ContextCompat.getColor(this, XAppLegalcase.get("案件执法")!!.moduleColor))

        mPresenter.getDetail(ApiParamUtil.detailParam(id))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        btn_legalcase_dispose.setOnClickListener {
            ZXDialogUtil.showListDialog(this, "请选择操作", "", optList, { _, which ->
                when (optList[which]) {
                    "启动流程" -> {
                        DisposeNormalActivity.startAction(this, false, detailBean!!)
                    }
                    "流程操作" -> {
                        DisposeNormalActivity.startAction(this, false, detailBean!!)
                    }
                    "强制措施" -> {
                        DisposeForceActivity.startAction(this, false, detailBean!!)
                    }
                    "简易流程" -> {
                        DisposeSimpleActivity.startAction(this, false, detailBean!!)
                    }
                    "案件移送" -> {
                        DisposeTransActivity.startAction(this, false, detailBean!!)
                    }
                }
            }, true)
        }
    }

    override fun onDetailResult(detailBean: DetailBean) {
        this.detailBean = detailBean
        if (detailBean.info.status == "00") {
            optList.add("启动流程")
        } else if (!detailBean.info.status.startsWith("E") && intent.hasExtra("optable") && intent.getBooleanExtra("optable", false)) {
            optList.add("流程操作")
        }
        optList.add("强制措施")
        optList.add("简易流程")
        optList.add("案件移送")

        if (detailBean.info.status == "03") {//已归档
            optList.clear()
        }
        if (optList.isNotEmpty()) {
            btn_legalcase_dispose.visibility = View.VISIBLE
        }

        //初始化数据
        caseInfoFragment.reSetInfo(detailBean.info)
        entityInfoFragment.reSetInfo(detailBean.info)
        fileInfoFragment.reSetInfo(detailBean.files)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01) {
            btn_legalcase_dispose.visibility = View.GONE
            setResult(0x01)
        }
    }

}
