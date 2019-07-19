package com.zx.module_complain.module.info.func.adapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import com.zx.module_complain.R
import com.zx.module_complain.module.info.bean.DisposeBean
import com.zx.module_library.func.tool.DatePickerTool
import com.zx.zxutils.entity.KeyValueEntity
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import com.zx.zxutils.util.ZXToastUtil
import com.zx.zxutils.views.ZXSpinner

/**
 * Created by Xiangb on 2019/7/2.
 * 功能：
 */
@SuppressLint("NewApi")
class DisposeAdapter(dataList: List<DisposeBean>) : ZXQuickAdapter<DisposeBean, ZXBaseHolder>(R.layout.item_dispose_vew, dataList) {

    private var moduleColor: Int = 0
    private var onSpinnerCall: (Int) -> Unit = {}

    override fun convert(helper: ZXBaseHolder?, item: DisposeBean?) {
        if (helper != null && item != null) {
            val etValue = helper.getView<EditText>(R.id.et_dispose_value)
            val spValue = helper.getView<ZXSpinner>(R.id.sp_dispose_value)
            val tvValue = helper.getView<TextView>(R.id.tv_dispose_value)
            etValue.visibility = View.GONE
            spValue.visibility = View.GONE
            tvValue.visibility = View.GONE
            helper.setVisible(R.id.tv_dispose_required, item.isRequired)
            helper.setText(R.id.tv_dispose_name, item.disposeName + ":")
            when (item.disposeType) {
                DisposeBean.DisposeType.Text -> {
                    tvValue.visibility = View.VISIBLE
                    helper.setText(R.id.tv_dispose_value, item.resultValue)
                }
                DisposeBean.DisposeType.Edit -> {
                    etValue.visibility = View.VISIBLE
                    etValue.tag = helper.adapterPosition
                    etValue.init(item)
                }
                DisposeBean.DisposeType.Spinner -> {
                    spValue.visibility = View.VISIBLE
                    spValue.tag = helper.adapterPosition
                    spValue.init(item)
                }
                DisposeBean.DisposeType.Time -> {
                    tvValue.visibility = View.VISIBLE
                    tvValue.init(item)
                }
            }

            try {
                val cursorFiled = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                cursorFiled.isAccessible = true
                val drawable = ContextCompat.getDrawable(mContext, R.drawable.shape_search_cursor)
                drawable!!.setTint(moduleColor)
                cursorFiled.set(etValue, drawable)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setSpinnerCall(onSpinnerCall: (Int) -> Unit) {
        this.onSpinnerCall = onSpinnerCall
    }

    private fun TextView.init(bean: DisposeBean) {
        if (bean.disposeType == DisposeBean.DisposeType.Time) {
            hint = "请选择..."
            setOnClickListener {
                DatePickerTool.showDatePicker(mContext, this){
                    bean.resultValue = it
                }
            }
        }
    }

    private fun EditText.init(bean: DisposeBean) {
        setText("")
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                bean.resultValue = text.toString()
            }

        })
    }

    private fun ZXSpinner.init(bean: DisposeBean) {
        val spList = arrayListOf<KeyValueEntity>()
        var selection = -1
        if (bean.disposeValue.isNotEmpty()) {
            bean.disposeValue.forEachIndexed { index, it ->
                spList.add(KeyValueEntity(it.key, it.value))
                if (it.isSelect) {
                    bean.resultValue = it.value
                    selection = index
                }
            }
        }

        //构建ZXSpinner
        showUnderineColor(false)
        setData(spList)
        setItemHeightDp(40)
        setItemTextSizeSp(15)
        showSelectedTextColor(true, moduleColor)
        if (bean.hasDefalut) setDefaultItem("请选择..")
        if (selection != -1) if (bean.hasDefalut) setDefaultItem(selection + 1) else setDefaultItem(selection)
        build()

        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bean.disposeValue.forEach {
                    it.isSelect = false
                }
                if (position == -1) {
                    bean.resultValue = ""
                    onSpinnerCall(tag as Int)
                    return
                } else if (bean.resultValue == bean.disposeValue[position].value) {
                    return
                }
                bean.resultKey = bean.disposeValue[position].key
                bean.resultValue = bean.disposeValue[position].value
                bean.disposeValue.get(position).isSelect = true
                onSpinnerCall(tag as Int)
            }
        }
    }

    /**
     * 检查item是否已填写完成
     */
    fun checkItem(): Boolean {
        if (data.isNotEmpty()) {
            data.forEach {
                when (it.disposeType) {
                    DisposeBean.DisposeType.Text -> {

                    }
                    DisposeBean.DisposeType.Edit -> {
                        if (it.resultValue.isEmpty() && it.isRequired) {
                            ZXToastUtil.showToast("请输入${it.disposeName}")
                            return false
                        }
                    }
                    DisposeBean.DisposeType.Spinner -> {
                        if (it.resultValue.isEmpty() && it.isRequired) {
                            ZXToastUtil.showToast("请选择${it.disposeName}")
                            return false
                        }
                    }
                    DisposeBean.DisposeType.Time -> {

                    }
                }
            }
        }
        return true
    }

    fun setModuleColor(moduleColor: Int) {
        this.moduleColor = moduleColor
    }
}