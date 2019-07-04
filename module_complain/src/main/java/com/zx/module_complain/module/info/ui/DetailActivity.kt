package com.zx.module_complain.module.info.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_complain.R
import com.zx.module_complain.XAppComplain
import com.zx.module_complain.api.ApiParamUtil
import com.zx.module_complain.module.info.bean.DetailBean
import com.zx.module_complain.module.info.mvp.contract.DetailContract
import com.zx.module_complain.module.info.mvp.model.DetailModel
import com.zx.module_complain.module.info.mvp.presenter.DetailPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.UserManager
import kotlinx.android.synthetic.main.activity_complain_detail.*


/**
 * Create By admin On 2017/7/11
 * 功能：投诉举报详情
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_COMPLAIN_DETAIL)
class DetailActivity : BaseActivity<DetailPresenter, DetailModel>(), DetailContract.View {

    private lateinit var regInfoFragment: DetailInfoFragment
    private lateinit var entityInfoFragment: DetailInfoFragment
    private lateinit var reportInfoFragment: DetailInfoFragment
    private lateinit var resultInfoFragment: DetailInfoFragment
    private lateinit var fileInfoFragment: FileInfoFragment
    private lateinit var dynamicFragment: DynamicFragment

    private var detailBean: DetailBean? = null

    private var fGuid: String = ""

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, fGuid: String) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("fGuid", fGuid)
            activity.startActivityForResult(intent,0x01)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_complain_detail
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolBar_view.withXApp(XAppComplain.get("投诉举报"))
        toolBar_view.setMidText("详情")

        fGuid = intent.getStringExtra("fGuid")

        tvp_complain_detail.setManager(supportFragmentManager)
                .setIndicatorHeight(5)
                .setTablayoutHeight(40)
                .setTabScrollable(true)
                .setTitleColor(R.color.text_color_noraml, R.color.text_color_noraml)
                .setIndicatorColor(ContextCompat.getColor(this, XAppComplain.get("投诉举报")!!.moduleColor))
                .setTablayoutBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setTabTextSize(resources.getDimension(R.dimen.text_size_normal).toInt())
                .addTab(DetailInfoFragment.newInstance(0).apply { regInfoFragment = this }, "登记信息")
                .addTab(DetailInfoFragment.newInstance(1).apply { entityInfoFragment = this }, "主体信息")
                .addTab(DetailInfoFragment.newInstance(2).apply { reportInfoFragment = this }, "投诉内容")
                .addTab(DetailInfoFragment.newInstance(3).apply { resultInfoFragment = this }, "处置结果")
                .addTab(FileInfoFragment.newInstance().apply { fileInfoFragment = this }, "附件")
                .addTab(DynamicFragment.newInstance().apply { dynamicFragment = this }, "处置动态")
                .build()

        btn_complain_dispose.background.setTint(ContextCompat.getColor(this, XAppComplain.get("投诉举报")!!.moduleColor))

        mPresenter.getDetailInfo(ApiParamUtil.detailParam(fGuid))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //任务处置按钮
        btn_complain_dispose.setOnClickListener {
            DisposeActivity.startAction(this, false, detailBean!!)
        }
    }

    /**
     * 详情数据返回
     */
    override fun onDetailResult(detailBean: DetailBean) {
        this.detailBean = detailBean
            toolBar_view.setMidText((detailBean.baseInfo.fName ?: "")
                    + (detailBean.baseInfo.fType ?: "")
                    + (detailBean.baseInfo.fEntityName ?: ""))
            //设置按钮
            val fStatus = detailBean.baseInfo.fStatus
            val roles = UserManager.getUser().role
            btn_complain_dispose.visibility = View.VISIBLE
            if (fStatus == 20 && detailBean.baseInfo.fInputUser.equals(UserManager.getUser().id)) {
                btn_complain_dispose.text = "任务处置(分流)"
            } else if (fStatus == 30 && roles.containsAll(arrayListOf("1001", "2020"))) {
                btn_complain_dispose.text = "任务处置(指派)"
            } else if (fStatus == 40 && detailBean.baseInfo.fInputUser.equals(UserManager.getUser().id)) {
                btn_complain_dispose.text = "任务处置(联系)"
            } else if (fStatus == 50 && detailBean.baseInfo.fInputUser.equals(UserManager.getUser().id)) {
                btn_complain_dispose.text = "任务处置(处置)"
            } else if (fStatus == 60 && roles.containsAll(arrayListOf("1001", "2020"))) {
                btn_complain_dispose.text = "任务处置(初审)"
            } else if (fStatus == 70 && roles.contains("2010")) {
                btn_complain_dispose.text = "任务处置(终审)"
            } else if (fStatus == 80 && detailBean.baseInfo.fInputUser.equals(UserManager.getUser().id)) {
                btn_complain_dispose.text = "任务处置(办结)"
            } else {
                btn_complain_dispose.visibility = View.GONE
            }
        //初始化数据
        regInfoFragment.reSetInfo(detailBean.baseInfo)
        entityInfoFragment.reSetInfo(detailBean.baseInfo)
        reportInfoFragment.reSetInfo(detailBean.baseInfo)
        resultInfoFragment.reSetInfo(detailBean.baseInfo)
        fileInfoFragment.reSetInfo(detailBean.files)
        dynamicFragment.reSetInfo(detailBean.statusInfo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01) {
            btn_complain_dispose.visibility = View.GONE
            setResult(0x01)
        }
    }

}
