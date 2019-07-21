package com.zx.module_other.module.documentmanage.func.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.zx.module_other.R
import com.zx.module_other.module.documentmanage.bean.DocumentMainBean
import com.zx.module_other.module.documentmanage.bean.TemplateFieldBean
import com.zx.module_other.module.law.bean.LawMainBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

class DocumentFillAdapter<T>(dataBeans: List<T>, callBack: setDataCallBack) : ZXQuickAdapter<T, ZXBaseHolder>(R.layout.item_document_fill, dataBeans) {
    var callBack: setDataCallBack? = null

    init {
        this.callBack = callBack
    }

    interface setDataCallBack {
        fun setData(key: String, text: String)
    }

    override fun convert(helper: ZXBaseHolder?, item: T) {
        if (item is TemplateFieldBean) {
            if (helper != null) {
                helper.setText(R.id.tv_fill_title, item.lable)
                helper.getView<EditText>(R.id.et_fill_content).addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        callBack!!.setData(item.id, helper.getView<EditText>(R.id.et_fill_content).text.toString())
                    }

                })
            }
        }
    }
}