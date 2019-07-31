package com.zx.module_other.module.documentmanage.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.documentmanage.bean.Children
import com.zx.module_other.module.documentmanage.bean.TemplateFieldBean
import com.zx.module_other.module.documentmanage.func.adapter.DocumentFillAdapter
import com.zx.module_other.module.workplan.mvp.contract.DocumentFillContract
import com.zx.module_other.module.workplan.mvp.model.DocumentFillModel
import com.zx.module_other.module.workplan.mvp.presenter.DocumentFillPresenter
import kotlinx.android.synthetic.main.activity_document_fill.*

@Route(path = RoutePath.ROUTE_OTHER_DOCUMENTFIll)
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
        btn_fill_preview.setOnClickListener {
            map["id"] = (intent.getSerializableExtra("children") as Children).id
            mPresenter.getDocumentPrintHtml(ApiParamUtil.toJson(map))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_document_fill
    }

    @SuppressLint("ResourceAsColor", "NewApi")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toobar_view.withXApp(XAppOther.DOCUMENT)

        btn_fill_preview.background.setTint(ContextCompat.getColor(this, XAppOther.DOCUMENT.moduleColor))

        rv_document_fill.apply {
            layoutManager = LinearLayoutManager(this@DocumentFillActivity) as RecyclerView.LayoutManager?
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