package com.zx.module_other.module.workplan.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText
import android.widget.RelativeLayout
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.documentmanage.bean.Children
import com.zx.module_other.module.documentmanage.bean.TemplateFieldBean
import com.zx.module_other.module.documentmanage.func.adapter.DocumentFillAdapter
import com.zx.module_other.module.workplan.mvp.contract.DocumentFillContract
import com.zx.module_other.module.workplan.mvp.contract.DocumentSeeContract
import com.zx.module_other.module.workplan.mvp.model.DocumentFillModel
import com.zx.module_other.module.workplan.mvp.model.DocumentSeeModel
import com.zx.module_other.module.workplan.mvp.presenter.DocumentFillPresenter
import com.zx.module_other.module.workplan.mvp.presenter.DocumentSeePresenter
import kotlinx.android.synthetic.main.activity_document_fill.*

class DocumentFillActivity : BaseActivity<DocumentFillPresenter, DocumentFillModel>(), DocumentFillContract.View {

    val map = hashMapOf<String, String>()

    var fieldDatas = arrayListOf<TemplateFieldBean>()
    var fillAdapter: DocumentFillAdapter<TemplateFieldBean> = DocumentFillAdapter(fieldDatas, object : DocumentFillAdapter.setDataCallBack {
        override fun setData(key: String, text: String) {
            map[key] = text
        }

    })

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, children: Children) {
            val intent = Intent(activity, DocumentFillActivity::class.java)
            intent.putExtra("children", children)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun onViewListener() {
        tv_fill_preview.setOnClickListener {
            map["id"] = (intent.getSerializableExtra("children") as Children).id
            mPresenter.getDocumentPrintHtml(ApiParamUtil.toJson(map))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_document_fill
    }

    @SuppressLint("ResourceAsColor")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toobar_view.withXApp(XAppOther.get("文书"))
        rv_document_fill.apply {
            layoutManager = LinearLayoutManager(this@DocumentFillActivity)
            adapter = fillAdapter
        }
        mPresenter.getDocumentFieldList(ApiParamUtil.getDocumentFieldParam((intent.getSerializableExtra("children") as Children).id))
    }

    override fun getDocumentFieldResult(fields: List<TemplateFieldBean>) {
        fieldDatas.clear()
        fieldDatas.addAll(fields)
        fillAdapter.setNewData(fieldDatas)
    }

    override fun getDocumentPrintResult(print: String) {
        DocumentSeeActivity.startAction(this, false, intent.getSerializableExtra("children") as Children, DocumentSeeActivity.TYPE_CHANGE, print)
    }
}