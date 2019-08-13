package com.zx.module_supervise.module.task.func.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.zx.module_supervise.R
import com.zx.module_supervise.module.task.bean.TaskCheckBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/7/23.
 * 功能：
 */
@SuppressLint("NewApi")
class TaskCheckAdapter(dataList: List<TaskCheckBean>, var color: Int) : ZXQuickAdapter<TaskCheckBean, ZXBaseHolder>(R.layout.item_check_view, dataList) {

    var isEdit = true
    var isShow = true

    override fun convert(helper: ZXBaseHolder?, item: TaskCheckBean?) {
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
                        helper.setTextColor(R.id.rb_check_value0, ContextCompat.getColor(mContext, color))
                        helper.setTextColor(R.id.rb_check_value1, ContextCompat.getColor(mContext, color))
                        helper.setTextColor(R.id.rb_check_value2, ContextCompat.getColor(mContext, color))
                        helper.getView<RadioButton>(R.id.rb_check_value0).buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(mContext, color))
                        helper.getView<RadioButton>(R.id.rb_check_value1).buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(mContext, color))
                        helper.getView<RadioButton>(R.id.rb_check_value2).buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(mContext, color))
                        when (item.fCheckResult) {
                            "0" -> rgValue.check(R.id.rb_check_value0)
                            "1" -> rgValue.check(R.id.rb_check_value1)
                            "2" -> rgValue.check(R.id.rb_check_value2)
                            else -> rgValue.clearCheck()
                        }
                        if (isEdit) {
                            rgValue.setOnCheckedChangeListener { _, checkedId ->
                                if (checkedId == -1) {
                                    return@setOnCheckedChangeListener
                                }
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

                        try {
                            val cursorFiled = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                            cursorFiled.isAccessible = true
                            val drawable = ContextCompat.getDrawable(mContext, R.drawable.shape_search_cursor)
                            drawable!!.setTint(ContextCompat.getColor(mContext, color))
                            cursorFiled.set(etValue, drawable)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

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