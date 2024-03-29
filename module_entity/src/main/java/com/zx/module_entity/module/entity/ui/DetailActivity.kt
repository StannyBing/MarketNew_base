package com.zx.module_entity.module.entity.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_entity.R
import com.zx.module_entity.XAppEntity
import com.zx.module_entity.module.entity.bean.EntityDetailBean
import com.zx.module_entity.module.entity.mvp.contract.DetailContract
import com.zx.module_entity.module.entity.mvp.model.DetailModel
import com.zx.module_entity.module.entity.mvp.presenter.DetailPresenter
import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.MapTaskBean
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.activity_entity_detail.*


/**
 * Create By admin On 2017/7/11
 * 功能：主体查询-详情
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_ENTITY_QUERY_DETAIL)
class DetailActivity : BaseActivity<DetailPresenter, DetailModel>(), DetailContract.View {

    private lateinit var infoFragment: DetailInfoFragment
    private lateinit var creditFragment: DetailCreditFragment
    private lateinit var businessFragment: DetailBusinessFragment
    private lateinit var imageFragment: DetailImageFragment

    private var detailBean: EntityDetailBean? = null

    private var fEntityGuid = ""
    private var isSpeacial = false
    private var fUniscid = ""
    private var fBizlicNum = ""

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, fEntityGuid: String, isSpeacial: Boolean = false) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("fEntityGuid", fEntityGuid)
            intent.putExtra("isSpeacial", isSpeacial)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }

        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, fUniscid: String, fBizlicNum: String) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("fUniscid", fUniscid)
            intent.putExtra("fBizlicNum", fBizlicNum)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_entity_detail
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolBar_view.withXApp(XAppEntity.ENTITY)
        toolBar_view.setMidText("主体详情")

        fEntityGuid = if (intent.hasExtra("fEntityGuid")) intent.getStringExtra("fEntityGuid") else ""
        isSpeacial = if (intent.hasExtra("isSpeacial")) intent.getBooleanExtra("isSpeacial", false) else false
        fUniscid = if (intent.hasExtra("fUniscid")) intent.getStringExtra("fUniscid") else ""
        fBizlicNum = if (intent.hasExtra("fBizlicNum")) intent.getStringExtra("fBizlicNum") else ""

        btn_entity_check.background.setTint(ContextCompat.getColor(this, XAppEntity.ENTITY.moduleColor))

        tvp_entity_detail.setManager(supportFragmentManager)
                .setIndicatorHeight(5)
                .setTablayoutHeight(40)
                .setTabScrollable(false)
                .setTitleColor(ContextCompat.getColor(this, R.color.text_color_light), ContextCompat.getColor(this, R.color.text_color_noraml))
                .setIndicatorColor(ContextCompat.getColor(this, XAppEntity.ENTITY.moduleColor))
                .setTablayoutBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setTabTextSize(resources.getDimension(R.dimen.text_size_normal).toInt())
                .addTab(DetailInfoFragment.newInstance().apply { infoFragment = this }, "基本信息")
                .addTab(DetailCreditFragment.newInstance().apply { creditFragment = this }, "信用信息")
                .addTab(DetailBusinessFragment.newInstance().apply { businessFragment = this }, "业务信息")
                .addTab(DetailImageFragment.newInstance().apply { imageFragment = this }, "主体图片")
                .build()

        if (fEntityGuid.isNotEmpty() && isSpeacial) {
            mPresenter.getEntityDetail(0, hashMapOf("fEntityGuid" to fEntityGuid))
        } else if (fEntityGuid.isNotEmpty()) {
            mPresenter.getEntityDetail(1, hashMapOf("id" to fEntityGuid))
        } else {
            mPresenter.getEntityDetail(2, hashMapOf("fUniscid" to fUniscid, "fBizlicNum" to fBizlicNum))
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //地图按钮
        toolBar_view.setRightClickListener {
            if (detailBean != null) {
                XApp.startXApp(RoutePath.ROUTE_MAP_MAP) {
                    it["type"] = 1
                    it["taskBean"] = MapTaskBean("主体查询",
                            XAppEntity.ENTITY.appIcon,
                            detailBean!!.fEntityName ?: "",
                            "主体地址：" + (detailBean!!.fAddress ?: ""),
                            "所属片区：" + (detailBean!!.fStation ?: "") + "-" + (detailBean!!.fGrid ?: ""),
                            detailBean!!.fEntityGuid ?: "",
                            detailBean!!.fLongitude,
                            detailBean!!.fLatitude)
                }
            }
        }

        btn_entity_check.setOnClickListener {
            ZXDialogUtil.showYesNoDialog(this@DetailActivity, "提示", "是否对该主体进行现场检查？") { _, _ ->
                XApp.startXApp(RoutePath.ROUTE_SUPERVISE_DAILY_ADD) {
                    it["fEntityGuid"] = detailBean?.fEntityGuid ?: ""
                    it["fEntityName"] = detailBean?.fEntityName ?: ""
                    it["fLegalPerson"] = detailBean?.fLegalPerson ?: ""
                    it["fContactInfo"] = detailBean?.fContactInfo ?: ""
                    it["fAddress"] = detailBean?.fAddress ?: ""
                }
            }
        }
    }

    override fun onEntityDetailResult(entityDetail: EntityDetailBean?) {
        if (entityDetail == null) {
            showToast("未获取到主体信息")
            finish()
            return
        }
        toolBar_view.setMidText(entityDetail.fEntityName)
        btn_entity_check.visibility = View.VISIBLE
        detailBean = entityDetail
        if (entityDetail.fCreditLevel == "Z") {
            tvp_entity_detail.tabLayout.visibility = View.GONE
            tvp_entity_detail.setViewpagerCanScroll(false)
        }
        infoFragment.resetInfo(entityDetail)
        creditFragment.resetInfo(entityDetail.fCreditLevel, entityDetail.fEntityGuid)
        businessFragment.resetInfo(entityDetail.fEntityGuid)
        imageFragment.resetInfo(entityDetail)
    }

}
