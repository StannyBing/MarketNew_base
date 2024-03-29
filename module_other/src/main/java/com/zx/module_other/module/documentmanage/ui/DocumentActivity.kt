package com.zx.module_other.module.documentmanage.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.documentmanage.bean.Children
import com.zx.module_other.module.documentmanage.bean.DocumentBean
import com.zx.module_other.module.documentmanage.bean.DocumentMainBean
import com.zx.module_other.module.documentmanage.func.adapter.DocumentAdpater
import com.zx.module_other.module.documentmanage.func.adapter.DocumentMainAdapter
import com.zx.module_other.module.workplan.mvp.contract.DocumentContract
import com.zx.module_other.module.workplan.mvp.model.DocumentModel
import com.zx.module_other.module.workplan.mvp.presenter.DocumentPresenter
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity
import kotlinx.android.synthetic.main.activity_document.*

@Route(path = RoutePath.ROUTE_OTHER_DOCUMENT)
class DocumentActivity : BaseActivity<DocumentPresenter, DocumentModel>(), DocumentContract.View {

    var userfulData = arrayListOf<DocumentMainBean>()
    var userfulAdapter = DocumentMainAdapter(userfulData);

    private var adapterDatas = arrayListOf<MultiItemEntity>()
    private var documentAdapter = DocumentAdpater(adapterDatas)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, DocumentActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    override fun onViewListener() {
        documentAdapter.setOnItemClickListener { adapter, view, position ->
            if (adapterDatas[position].itemType == DocumentAdpater.TYPE_LEVEL_1) {
                DocumentSeeActivity.startAction(this, false, adapterDatas[position] as Children, DocumentSeeActivity.TYPE_FILL, "")
            }
        }

        userfulAdapter.setOnItemClickListener { adapter, view, position ->
            sv_document_search.setSearchText(userfulData[position].name)
//            mPresenter.getDocumentList(ApiParamUtil.getDocumentParam(userfulData[position].name))

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_document
    }

    @SuppressLint("ResourceAsColor")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toobar_view.withXApp(XAppOther.DOCUMENT)
        sv_document_search.withXApp(XAppOther.DOCUMENT)
        rv_document.apply {
            layoutManager = LinearLayoutManager(this@DocumentActivity)
            adapter = documentAdapter
        }
        rv_document_useful.apply {
            layoutManager = GridLayoutManager(this@DocumentActivity, 2)
            adapter = userfulAdapter
        }
        sv_document_search.setSearchListener {
            mPresenter.getDocumentList(ApiParamUtil.getDocumentParam(it))
        }
        mPresenter.getDocumentList(ApiParamUtil.getDocumentParam(""))
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        addData(this.resources.getStringArray(R.array.documentName), this.resources.getIntArray(R.array.documentId), 0, userfulData, userfulAdapter)
    }

    /**
     * 添加数据源
     */
    private fun addData(dataSource: Array<String>, id: IntArray, type: Int, data: ArrayList<DocumentMainBean>, adapter: ZXQuickAdapter<DocumentMainBean, ZXBaseHolder>) {
        if (dataSource.isNotEmpty()) {
            for ((index, e) in dataSource.withIndex()) {
                data.add(DocumentMainBean(e, id[index], type))
            }
            adapter.setNewData(data)
        }
    }

    override fun getDocumentResult(documents: List<DocumentBean>) {
        adapterDatas.clear()
        for (document in documents) {
            adapterDatas.add(document)
            for (child in document.children) {
                adapterDatas.add(child)
            }
        }
        documentAdapter.setNewData(adapterDatas)
    }
}