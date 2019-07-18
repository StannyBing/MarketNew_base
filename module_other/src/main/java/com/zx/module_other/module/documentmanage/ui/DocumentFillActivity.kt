package com.zx.module_other.module.workplan.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.module.documentmanage.bean.DocumentContentBean
import com.zx.module_other.module.documentmanage.func.adapter.DocumentFillAdapter
import com.zx.module_other.module.workplan.mvp.contract.DocumentSeeContract
import com.zx.module_other.module.workplan.mvp.model.DocumentSeeModel
import com.zx.module_other.module.workplan.mvp.presenter.DocumentSeePresenter
import kotlinx.android.synthetic.main.activity_document_fill.*

class DocumentFillActivity : BaseActivity<DocumentSeePresenter, DocumentSeeModel>(), DocumentSeeContract.View {
    var stringDatas = arrayListOf<String>()
    var fillAdapter: DocumentFillAdapter<String> = DocumentFillAdapter(stringDatas)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, documentContentBean: DocumentContentBean) {
            val intent = Intent(activity, DocumentFillActivity::class.java)
            intent.putExtra("documentContentBean", documentContentBean)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun onViewListener() {
        rv_document_fill.setOnClickListener {
            DocumentSeeActivity.startAction(this, false, intent.getSerializableExtra("documentContentBean") as DocumentContentBean, DocumentSeeActivity.TYPE_CHANGE)
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
        initData()
    }

    fun initData() {
        if (stringDatas != null) {
            stringDatas.add("市监：")
            stringDatas.add("字号：")
            stringDatas.add("单位名称：")
            stringDatas.add("违法规定：")
            stringDatas.add("时间：")
            stringDatas.add("处罚规定：")
            fillAdapter.setNewData(stringDatas)
        }
    }

    override fun getDocumentWebSeeResult(weburl: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}