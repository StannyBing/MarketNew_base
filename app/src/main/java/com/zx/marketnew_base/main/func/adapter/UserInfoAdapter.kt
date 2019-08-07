package com.zx.marketnew_base.main.func.adapter

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.zx.marketnew_base.R
import com.zx.module_library.bean.KeyValueBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/3/11.
 * 功能：
 */
class UserInfoAdapter(dataBeans: List<KeyValueBean>, val editable: Boolean) : ZXQuickAdapter<KeyValueBean, ZXBaseHolder>(R.layout.item_userdetail, dataBeans) {
    override fun convert(helper: ZXBaseHolder?, item: KeyValueBean?) {
        if (helper != null && item != null) {
            if (editable && "电话".equals(item.key)) {
                helper.getView<TextView>(R.id.tv_userDetail_itemValue).visibility = View.GONE
                helper.getView<ImageView>(R.id.iv_userDetail_canEdit).visibility = View.VISIBLE
                helper.getView<EditText>(R.id.et_userDetail_itemValue).apply {
                    visibility = View.VISIBLE
                    setText(item.value)
                    inputType = InputType.TYPE_CLASS_NUMBER
                }
            } else {
                helper.getView<TextView>(R.id.tv_userDetail_itemValue).visibility = View.VISIBLE
                helper.getView<EditText>(R.id.et_userDetail_itemValue).visibility = View.GONE
                helper.getView<ImageView>(R.id.iv_userDetail_canEdit).visibility = View.GONE
                helper.setText(R.id.tv_userDetail_itemValue, item.value)
            }
            helper.setText(R.id.tv_userDetail_itemKey, item.key)
            helper.getView<EditText>(R.id.et_userDetail_itemValue).apply {
                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        item.value = text.toString()
                    }
                })
            }
        }
    }
}