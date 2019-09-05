package com.zx.module_supervise.module.daily.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.XApp
import com.zx.module_library.app.ConstStrings
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.NormalList
import com.zx.module_library.func.tool.UserActionTool
import com.zx.module_library.func.tool.toJson
import com.zx.module_library.view.SearchView
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.daily.bean.EntityBean
import com.zx.module_supervise.module.daily.func.adapter.DailyEntityAdapter
import com.zx.module_supervise.module.daily.mvp.contract.DailyAddContract
import com.zx.module_supervise.module.daily.mvp.model.DailyAddModel
import com.zx.module_supervise.module.daily.mvp.presenter.DailyAddPresenter
import com.zx.zxutils.util.ZXBitmapUtil
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXFileUtil
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener
import com.zx.zxutils.views.SwipeRecylerView.ZXSwipeRecyler
import io.github.xudaojie.qrcodelib.CaptureActivity
import kotlinx.android.synthetic.main.activity_daily_add.*
import kotlinx.android.synthetic.main.fragment_daily_base.*
import java.io.File


/**
 * Create By admin On 2017/7/11
 * 功能：现场检查-新增
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_SUPERVISE_DAILY_ADD)
class DailyAddActivity : BaseActivity<DailyAddPresenter, DailyAddModel>(), DailyAddContract.View {

    private lateinit var dailyBaseFragment: DailyBaseFragment
    private lateinit var dailyCheckFragment: DailyCheckFragment

    private var attachment = ""
    private var entitySearchText = ""
    private var entityPageNo = 1
    private var entityList = arrayListOf<EntityBean>()
    private var entityAdapter = DailyEntityAdapter(entityList)
    private var srEntityList: ZXSwipeRecyler? = null
    private var tvEntityTips: TextView? = null
    private var enterpriseSign = ""//被检查企业负责人签名id
    private var checkSign = ""//检察人员签字id

    private var dailyId = ""

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, id: String = "") {
            val intent = Intent(activity, DailyAddActivity::class.java)
            if (id.isNotEmpty()) intent.putExtra("dailyId", id)
            activity.startActivityForResult(intent, 0x01)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_daily_add
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolBar_view.withXApp(XAppSupervise.DAILY)
        btn_daily_submit.background.setTint(ContextCompat.getColor(this, XAppSupervise.DAILY.moduleColor))

        dailyId = if (intent.hasExtra("dailyId")) intent.getStringExtra("dailyId") else ""

        tvp_daily_add.setManager(supportFragmentManager)
                .setIndicatorHeight(5)
                .setTablayoutHeight(40)
                .setTabScrollable(false)
                .setTitleColor(ContextCompat.getColor(this, R.color.text_color_light), ContextCompat.getColor(this, R.color.text_color_noraml))
                .setIndicatorColor(ContextCompat.getColor(this, XAppSupervise.DAILY.moduleColor))
                .setTablayoutBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setTabTextSize(resources.getDimension(R.dimen.text_size_normal).toInt())
                .addTab(DailyBaseFragment.newInstance(dailyId).apply { dailyBaseFragment = this }, "基本信息")
                .addTab(DailyCheckFragment.newInstance(dailyId).apply { dailyCheckFragment = this }, "检查内容")
                .build()

        if (dailyId.isEmpty()) {//查看详情
            if (intent.hasExtra("fEntityGuid")) {//从主体详情进入
                dailyBaseFragment.setEntityInfo(EntityBean(fEntityGuid = intent.getStringExtra("fEntityGuid"),
                        fEntityName = intent.getStringExtra("fEntityName"),
                        fLegalPerson = intent.getStringExtra("fLegalPerson"),
                        fContactInfo = intent.getStringExtra("fContactInfo"),
                        fAddress = intent.getStringExtra("fAddress")))
            } else {//点击新增
                showEntityDilaog()
            }
        } else {
            toolBar_view.setMidText("检查详情")
            btn_daily_submit.text = "再次检查"
        }
    }

    fun showEntityDilaog() {
        val entityView = LayoutInflater.from(this).inflate(R.layout.layout_daily_entity, null, false)
        val svEntity = entityView.findViewById<SearchView>(R.id.search_view)
        tvEntityTips = entityView.findViewById<TextView>(R.id.tv_entity_tips)
        srEntityList = entityView.findViewById<ZXSwipeRecyler>(R.id.sr_entity_list)
        svEntity.withXApp(XAppSupervise.DAILY)
        tvEntityTips?.setTextColor(ContextCompat.getColor(this, XAppSupervise.DAILY.moduleColor))
        //搜索事件
        svEntity.setSearchListener {
            entitySearchText = it
            loadEntityList(true)
        }
        svEntity.setOtherPicClick {
            getPermission(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                startActivityForResult(Intent(this, CaptureActivity::class.java), 0x09)
            }
        }
        srEntityList!!.setLayoutManager(LinearLayoutManager(this))
                .setAdapter(entityAdapter)
                .autoLoadMore()
                .setPageSize(15)
                .setSRListener(object : ZXSRListener<EntityBean> {
                    override fun onItemLongClick(item: EntityBean?, position: Int) {
                    }

                    override fun onLoadMore() {
                        entityPageNo++
                        loadEntityList()
                    }

                    override fun onRefresh() {
                        loadEntityList(true)
                    }

                    override fun onItemClick(item: EntityBean?, position: Int) {
                        dailyBaseFragment.setEntityInfo(item!!)
                        ZXDialogUtil.dismissDialog()
                    }

                })
        srEntityList?.swipeRefreshLayout?.setColorSchemeResources(R.color.daily_color)
        loadEntityList()
        ZXDialogUtil.showCustomViewDialog(this, "请先选择检查主体", entityView, null, { _, _ -> })
    }

    private fun loadEntityList(refresh: Boolean = false) {
        if (refresh) {
            entityPageNo = 1
            srEntityList?.clearStatus()
        }
        mPresenter.getEntityList(hashMapOf("pageNo" to entityPageNo.toString(), "pageSize" to 15.toString(), "fCondition" to entitySearchText))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        btn_daily_submit.setOnClickListener {
            if (dailyId.isEmpty()) {
                submitDaily()
            } else {
                if (dailyBaseFragment.entityBean != null) {
                    ZXDialogUtil.showYesNoDialog(this, "提示", "是否再次检查") { _, _ ->
                        XApp.startXApp(RoutePath.ROUTE_SUPERVISE_DAILY_ADD) {
                            it["fEntityGuid"] = dailyBaseFragment.entityBean!!.fEntityGuid ?: ""
                            it["fEntityName"] = dailyBaseFragment.entityBean!!.fEntityName ?: ""
                            it["fLegalPerson"] = dailyBaseFragment.entityBean!!.fLegalPerson ?: ""
                            it["fContactInfo"] = dailyBaseFragment.entityBean!!.fContactInfo ?: ""
                            it["fAddress"] = dailyBaseFragment.entityBean!!.fAddress ?: ""
                        }
                        finish()
                    }
                } else {
                    showToast("暂未获取到主体信息")
                }
            }
        }
    }

    //提交检查
    private fun submitDaily() {
        if (!dailyBaseFragment.checkItem()) {
            tvp_daily_add.setSelectOn(0)
        } else if (!dailyCheckFragment.checkItem()) {
            showToast("请先完成检查内容")
            tvp_daily_add.setSelectOn(1)
        } else if (dailyCheckFragment.checkList.isEmpty()) {
            ZXDialogUtil.showYesNoDialog(this, "提示", "未选择检查内容，是否继续提交？", "确定", "取消", { _, _ ->
                getPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (file_view.fileList.isNotEmpty()) {
                        val files = arrayListOf<File>()
                        file_view.fileList.forEach {
                            files.add(File(it.filePath))
                        }
                        mPresenter.uploadFile(0, files)
                    } else {
                        onFileUploadResult("", "", 0)
                    }
                }
            }, { _, _ ->
                tvp_daily_add.setSelectOn(1)
            })
        } else {
            ZXDialogUtil.showYesNoDialog(this, "提示", "是否提交处置结果？") { _, _ ->
                getPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (file_view.fileList.isNotEmpty()) {
                        val files = arrayListOf<File>()
                        file_view.fileList.forEach {
                            files.add(File(it.filePath))
                        }
                        mPresenter.uploadFile(0, files)
                    } else {
                        onFileUploadResult("", "", 0)
                    }
                }
            }
        }
    }

    override fun onFileUploadResult(id: String, paths: String, type: Int) {
        when (type) {
            0 -> {
                attachment = id
                val file = ZXFileUtil.createNewFile(ConstStrings.getCachePath() + "signTemp1.png")
                ZXBitmapUtil.bitmapToFile(dailyBaseFragment.bitmapSign1, file)
                mPresenter.uploadFile(1, arrayListOf(file))
            }
            1 -> {
                enterpriseSign = paths
                val file = ZXFileUtil.createNewFile(ConstStrings.getCachePath() + "signTemp2.png")
                ZXBitmapUtil.bitmapToFile(dailyBaseFragment.bitmapSign2, file)
                mPresenter.uploadFile(2, arrayListOf(file))
            }
            2 -> {
                checkSign = paths
                val map = dailyBaseFragment.getDailyInfo()
                map.putAll(dailyCheckFragment.getDailyInfo())
                map["enterpriseSign"] = enterpriseSign
                map["checkSign"] = checkSign
                if (attachment.isNotEmpty()) map["attachment"] = attachment
                mPresenter.saveDailyInfo(map.toJson())
            }
        }
    }

    override fun onDailySaveResult() {
        if (intent.hasExtra("fEntityGuid")) {
            UserActionTool.addUserAction(this, UserActionTool.ActionType.Supervise_Daily, intent.getStringExtra("fEntityGuid"))
        }
        showToast("提交成功")
        setResult(0x01)
        finish()
    }

    override fun onEntityListResult(entityList: NormalList<EntityBean>) {
        tvEntityTips?.text = "检索到主体共${entityList.total}条"
        srEntityList?.refreshData(entityList.list, entityList.total)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0x09) {
            if (data == null || data.getStringExtra("result").isEmpty()) {
                showToast("未获取到主体信息")
            } else {
                var result = data.getStringExtra("result")
                if (result.contains("信用代码：") && result.contains("；注册号")) {
                    result = result.substring(result.indexOf("信用代码：") + 5, result.indexOf("；注册号"))
                    mPresenter.getEntityByBizlicNum(hashMapOf("fUniscid" to result))
                } else if (result.contains("zch")) {
                    result = result.substring(result.indexOf("zch=") + 4)
                    mPresenter.getEntityByBizlicNum(hashMapOf("fBizlicNum" to result))
                } else if (result.contains("uniscid")) {
                    result = result.substring(result.indexOf("uniscid=") + 8)
                    mPresenter.getEntityByBizlicNum(hashMapOf("fUniscid" to result))
                } else {
                    showToast("未获取到主体信息")
                }
            }
        }
    }

    override fun onEntityDetailResult(entityBean: EntityBean?) {
        if (entityBean != null) {
            dailyBaseFragment.setEntityInfo(entityBean)
            ZXDialogUtil.dismissDialog()
        } else {
            showToast("未获取到主体信息")
        }
    }
}
