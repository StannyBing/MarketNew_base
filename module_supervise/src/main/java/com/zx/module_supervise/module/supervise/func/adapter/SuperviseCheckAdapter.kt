package com.zx.module_supervise.module.supervise.func.adapter

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.zx.module_supervise.R
import com.zx.module_supervise.module.supervise.bean.SuperviseCheckBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/7/23.
 * 功能：
 */
class SuperviseCheckAdapter(dataList: List<SuperviseCheckBean>) : ZXQuickAdapter<SuperviseCheckBean, ZXBaseHolder>(R.layout.item_check_view, dataList) {

    var isEdit = true
    var isShow = true

    override fun convert(helper: ZXBaseHolder?, item: SuperviseCheckBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_check_key, (helper.adapterPosition + 1).toString() + "." + item.fName)
            if (isShow) {
                helper.getView<LinearLayout>(R.id.ll_check_value).visibility = View.VISIBLE
                when (item.fValueType) {//radiobutton
                    "0" -> {
                        val rgValue = helper.getView<RadioGroup>(R.id.rg_check_value)
                        rgValue.visibility = View.VISIBLE
                        helper.getView<EditText>(R.id.et_check_value).visibility = View.GONE
                        helper.setText(R.id.tv_check_tips, if (isEdit) "请选择处置结果：" else "处置结果：")
                        when (item.fCheckResult) {
                            "0" -> rgValue.check(R.id.rb_check_value0)
                            "1" -> rgValue.check(R.id.rb_check_value1)
                            "2" -> rgValue.check(R.id.rb_check_value2)
                            else -> rgValue.clearCheck()
                        }
                        if (isEdit) {
                            rgValue.setOnCheckedChangeListener { _, checkedId ->
                                item.fCheckResult = when (checkedId) {
                                    R.id.rb_check_value0 -> "0"
                                    R.id.rb_check_value1 -> "1"
                                    R.id.rb_check_value2 -> "2"
                                    else -> ""
                                }
                            }
                        } else {
                            for (i in 0 until rgValue.childCount) {
                                rgValue.getChildAt(i).isEnabled = false
                            }
                        }
                    }
                    "1" -> {//tv-num
                        val etValue = helper.getView<EditText>(R.id.et_check_value)
                        helper.getView<RadioGroup>(R.id.rg_check_value).visibility = View.GONE
                        etValue.visibility = View.VISIBLE
                        helper.setText(R.id.tv_check_tips, if (isEdit) "请输入处置结果：" else "处置结果：")
                        if (isEdit) {
                            etValue.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                            etValue.addTextChangedListener(object : TextWatcher {
                                override fun afterTextChanged(s: Editable?) {
                                }

                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                }

                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                    item.fCheckResult = etValue.text.toString()
                                }
                            })
                        } else {
                            etValue.isClickable = false
                            etValue.isFocusableInTouchMode = false
                            etValue.setOnTouchListener { _, _ -> true }
                        }
                    }
                    else -> {//tv-text
                        val etValue = helper.getView<EditText>(R.id.et_check_value)
                        helper.getView<RadioGroup>(R.id.rg_check_value).visibility = View.GONE
                        etValue.visibility = View.VISIBLE
                        helper.setText(R.id.tv_check_tips, if (isEdit) "请输入处置结果：" else "处置结果：")
                        if (isEdit) {
                            etValue.inputType = InputType.TYPE_CLASS_TEXT
                            etValue.addTextChangedListener(object : TextWatcher {
                                override fun afterTextChanged(s: Editable?) {
                                }

                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                }

                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                    item.fCheckResult = etValue.text.toString()
                                }
                            })
                        } else {
                            etValue.isClickable = false
                            etValue.isFocusableInTouchMode = false
                            etValue.setOnTouchListener { _, _ -> true }
                        }
                    }
                }
            } else {
                helper.getView<LinearLayout>(R.id.ll_check_value).visibility = View.GONE
            }
        }
    }
}