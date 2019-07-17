package com.zx.module_library.func.adapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import com.zx.module_library.R
import com.zx.module_library.bean.SearchFilterBean
import com.zx.zxutils.entity.KeyValueEntity
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import com.zx.zxutils.views.ZXSpinner


/**
 * Created by Xiangb on 2019/6/26.
 * 功能：
 */
@SuppressLint("NewApi")
class SearchFilterAdapter(dataBeans: List<SearchFilterBean>, var color: Int) : ZXQuickAdapter<SearchFilterBean, ZXBaseHolder>(R.layout.item_filter_view, dataBeans) {

    private var selectCall: (Int, String) -> Unit = { _, _ -> }

    override fun convert(helper: ZXBaseHolder?, item: SearchFilterBean?) {
        if (helper != null && item != null) {
            val etValue = helper.getView<EditText>(R.id.et_filter_value)
            val spValue = helper.getView<ZXSpinner>(R.id.sp_filter_value)
            val tvTips = helper.getView<TextView>(R.id.tv_filter_tips)
            helper.setText(R.id.tv_filter_name, item.filterName + ":")
            when (item.filterType) {
                SearchFilterBean.FilterType.EDIT_TYPE -> {
                    etValue.visibility = View.VISIBLE
                    spValue.visibility = View.GONE

                    etValue.init(item.values)
                }
                SearchFilterBean.FilterType.SELECT_TYPE -> {
                    etValue.visibility = View.GONE
                    spValue.visibility = View.VISIBLE

                    spValue.tag = helper.adapterPosition
                    spValue.init(item)
                }
            }
            if (!item.isEnable) {
                etValue.visibility = View.GONE
                spValue.visibility = View.GONE
                tvTips.visibility = View.VISIBLE
            } else {
                tvTips.visibility = View.GONE
            }

            try {
                val cursorFiled = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                cursorFiled.isAccessible = true
                val drawable = ContextCompat.getDrawable(mContext, R.drawable.shape_search_cursor)
                drawable!!.setTint(color)
                cursorFiled.set(etValue, drawable)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setSelectCall(selectCall: (Int, String) -> Unit) {
        this.selectCall = selectCall
    }

    private fun EditText.init(list: List<SearchFilterBean.ValueBean>) {
        setText("")
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                list.get(0).value = text.toString()
            }

        })
    }

    private fun ZXSpinner.init(bean: SearchFilterBean) {
        val spList = arrayListOf<KeyValueEntity>()
        bean.values.forEach {
            spList.add(KeyValueEntity(it.key, it.value))
        }

        //构建ZXSpinner
        showUnderineColor(false)
        setData(spList)
        setItemHeightDp(40)
        setItemTextSizeSp(13)
        showSelectedTextColor(true, color)
        if (bean.addDefalut) setDefaultItem("请选择..")
        build()

        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bean.values.forEach {
                    it.isSelect = false
                }
                if (position == -1) {
                    if (bean.singleFunc) {
                        selectCall(tag as Int, "")
                    }
                    return
                }
                bean.values[position].isSelect = true
                selectCall(tag as Int, bean.values[position].value)
            }

        }
    }
}