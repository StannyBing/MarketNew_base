package com.zx.module_supervise.module.daily.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.daily.bean.TemplateBean
import com.zx.module_supervise.module.daily.func.adapter.TemplateAdapter
import com.zx.module_supervise.module.daily.mvp.contract.TemplateContract
import com.zx.module_supervise.module.daily.mvp.model.TemplateModel
import com.zx.module_supervise.module.daily.mvp.presenter.TemplatePresenter
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.activity_template.*


/**
 * Create By admin On 2017/7/11
 * 功能：检查模板
 */
@Route(path = RoutePath.ROUTE_SUPERVISE_DAILY_TEMPLATE)
class TemplateActivity : BaseActivity<TemplatePresenter, TemplateModel>(), TemplateContract.View {

    private val templateList = arrayListOf<TemplateBean>()
    private val templateAdapter = TemplateAdapter(templateList)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, TemplateActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }

        fun startAction(fragment: Fragment) {
            val intent = Intent(fragment.activity, TemplateActivity::class.java)
            fragment.startActivityForResult(intent, 0x01)
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_template
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolBar_view.withXApp(XAppSupervise.DAILY)

        rv_template_list.apply {
            layoutManager = LinearLayoutManager(this@TemplateActivity)
            adapter = templateAdapter
        }

        mPresenter.getModelList(hashMapOf("pageNo" to "1", "pageSize" to "999"))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //模板编辑
        templateAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.tv_template_edit) {
                CheckListActivity.startAction(this, false, templateList[position].id, templateList[position].templateName)
            }
        }
        //选择模板
        templateAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent()
            intent.putExtra("templateId", templateList[position].id)
            intent.putExtra("templateName", templateList[position].templateName)
            setResult(0x01, intent)
            finish()
        }
        //新增模板
        toolBar_view.setRightClickListener {
            CheckListActivity.startAction(this, false, "", "")
        }
    }

    override fun onModelListResult(templateList: List<TemplateBean>) {
        this.templateList.clear()
        this.templateList.addAll(templateList)
        templateAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0x01 && data != null) {
            mPresenter.getModelList(hashMapOf("pageNo" to "1", "pageSize" to "999"))
            ZXDialogUtil.showYesNoDialog(this, "提示", "模板保存成功，是否立即使用？") { _, _ ->
                val intent = Intent()
                intent.putExtra("templateId", data.getStringExtra("templateId"))
                intent.putExtra("templateName", data.getStringExtra("templateName"))
                setResult(0x01, intent)
                finish()
            }
        }
    }

}
