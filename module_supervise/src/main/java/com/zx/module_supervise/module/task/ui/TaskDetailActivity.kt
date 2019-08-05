package com.zx.module_supervise.module.task.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.MapTaskBean
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.task.bean.EntityInfoBean
import com.zx.module_supervise.module.task.bean.TaskInfoBean
import com.zx.module_supervise.module.task.mvp.contract.TaskDetailContract
import com.zx.module_supervise.module.task.mvp.model.TaskDetailModel
import com.zx.module_supervise.module.task.mvp.presenter.TaskDetailPresenter
import kotlinx.android.synthetic.main.activity_supervise_detail.*


/**
 * Create By admin On 2017/7/11
 * 功能：专项检查-详情
 */
@SuppressLint("NewApi")
@Route(path = RoutePath.ROUTE_SUPERVISE_TASK_DETAIL)
class TaskDetailActivity : BaseActivity<TaskDetailPresenter, TaskDetailModel>(), TaskDetailContract.View {


    private lateinit var id: String
    private lateinit var taskId: String
    private var optable: Boolean = false

    private var taskInfoBean: TaskInfoBean? = null
    private var entityInfoBean: EntityInfoBean? = null

    private lateinit var taskInfoFragment: DetailTaskInfoFragment
    private lateinit var entityInfoFragment: DetailEntityInfoFragment
    private lateinit var checkInfoFragment: DetailCheckInfoFragment
    private lateinit var dynamicFragment: DetailDynamicFragment

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, id: String, taskId: String, optable: Boolean = false) {
            val intent = Intent(activity, TaskDetailActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("taskId", taskId)
            intent.putExtra("optable", optable)
            activity.startActivityForResult(intent, 0x01)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_supervise_detail
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolBar_view.withXApp(XAppSupervise.SUPERVISE)

        optable = if (intent.hasExtra("optable")) intent.getBooleanExtra("optable", false) else false
        id = intent.getStringExtra("id")
        taskId = intent.getStringExtra("taskId")

        tvp_supervise_detail.setManager(supportFragmentManager)
                .setIndicatorHeight(5)
                .setTablayoutHeight(40)
                .setTabScrollable(false)
                .setTitleColor(ContextCompat.getColor(this,R.color.text_color_light), ContextCompat.getColor(this,R.color.text_color_noraml))
                .setIndicatorColor(ContextCompat.getColor(this, XAppSupervise.SUPERVISE.moduleColor))
                .setTablayoutBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setTabTextSize(resources.getDimension(R.dimen.text_size_normal).toInt())
                .addTab(DetailTaskInfoFragment.newInstance().apply { taskInfoFragment = this }, "任务信息")
                .addTab(DetailEntityInfoFragment.newInstance().apply { entityInfoFragment = this }, "主体信息")
                .addTab(DetailCheckInfoFragment.newInstance(id, taskId).apply { checkInfoFragment = this }, "检查指标")
                .addTab(DetailDynamicFragment.newInstance().apply { dynamicFragment = this }, "处置动态")
                .build()

        btn_supervise_dispose.background.setTint(ContextCompat.getColor(this, XAppSupervise.SUPERVISE.moduleColor))

        mPresenter.getDetailInfo(id, taskId)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //任务处置按钮
        btn_supervise_dispose.setOnClickListener {
            if (entityInfoBean != null) {
                TaskDisposeActivity.startAction(this, false, id, taskId, entityInfoBean!!.fEntityGuid, entityInfoBean!!.fStatus!!)
            } else {
                showToast("未获取到主体信息")
            }
        }
        //地图按钮
        toolBar_view.setRightClickListener {
            if (entityInfoBean != null && taskInfoBean != null) {
                XApp.startXApp(RoutePath.ROUTE_MAP_MAP) {
                    it["type"] = 1
                    it["taskBean"] = MapTaskBean("专项检查",
                            XAppSupervise.SUPERVISE.appIcon,
                            taskInfoBean!!.fName ?: "",
                            "主体地址：" + (entityInfoBean!!.fAddress ?: ""),
                            "涉及主体：" + (entityInfoBean!!.fEntityName ?: ""),
                            taskId,
                            entityInfoBean!!.fLongitude,
                            entityInfoBean!!.fLatitude)
                }
            }
        }
    }

    //任务信息
    override fun onTaskInfoResult(taskInfoBean: TaskInfoBean) {
        this.taskInfoBean = taskInfoBean
        taskInfoFragment.resetInfo(taskInfoBean)

        toolBar_view.setMidText(taskInfoBean.fName)

    }

    //主体信息
    override fun onEntityInfoResult(entityInfoBean: EntityInfoBean) {
        this.entityInfoBean = entityInfoBean
        entityInfoFragment.resetInfo(entityInfoBean)
        dynamicFragment.resetInfo(taskId, entityInfoBean.fEntityGuid)

        if (optable) {
            btn_supervise_dispose.visibility = View.VISIBLE
            btn_supervise_dispose.text = when (entityInfoBean.fStatus) {
                "101" -> "开始执行"
                "102" -> "初审"
                "103" -> "核审"
                "104" -> "终审"
                else -> {
                    btn_supervise_dispose.visibility = View.GONE
                    ""
                }
            }
        }
        checkInfoFragment.setfStatus(entityInfoBean.fStatus)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01) {
            btn_supervise_dispose.visibility = View.GONE
            setResult(0x01)
        }
    }

}
