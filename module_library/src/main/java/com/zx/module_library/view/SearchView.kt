package com.zx.module_library.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.zx.module_library.R
import com.zx.module_library.bean.SearchFilterBean
import com.zx.module_library.bean.XAppBean
import com.zx.module_library.func.adapter.SearchFilterAdapter
import com.zx.zxutils.util.ZXSystemUtil
import com.zx.zxutils.views.BubbleView.ZXBubbleView

/**
 * Created by Xiangb on 2019/3/7.
 * 功能：搜索按钮
 */
@SuppressLint("NewApi")
class SearchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private var searchContent: LinearLayout
    private var searchText: EditText//输入框
    private var searchBtn: ImageView//搜索按钮
    private var searchFunc: TextView//搜索功能键

    private var module_color: Int//主题色
    private var filterBubble: ZXBubbleView? = null

    private var justSearchButton = false
    private var doSearch: (String) -> Unit = {}
    private var doFunc: () -> Unit = {}
    private var filterValues: List<SearchFilterBean>? = null
    private var filterAdater: SearchFilterAdapter? = null

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.SearchView, defStyleAttr, 0)
        inflate(context, R.layout.layout_search_view, this)
        searchText = findViewById(R.id.et_search_text)
        searchBtn = findViewById(R.id.iv_search_btn)
        searchFunc = findViewById(R.id.tv_search_func)
        searchContent = findViewById(R.id.ll_search_content)
        searchBtn.setOnClickListener { doSearch() }
        searchFunc.setOnClickListener {
            if (filterValues != null) {
                showFilterBubble()
            } else {
                doFunc()
            }
        }
        searchText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!justSearchButton) {
                    doSearch(false)
                }
            }
        })
        val funcText = if (typedArray.hasValue(R.styleable.SearchView_func_text)) typedArray.getString(R.styleable.SearchView_func_text) else ""
        val showFunc = typedArray.getBoolean(R.styleable.SearchView_show_func, false)
        searchText.hint = if (typedArray.hasValue(R.styleable.SearchView_hint_text)) typedArray.getString(R.styleable.SearchView_hint_text) else "搜索";
        module_color = typedArray.getColor(R.styleable.SearchView_module_color, ContextCompat.getColor(context, R.color.colorPrimary))

        resetColor()

        if (showFunc) {
            searchFunc.visibility = View.VISIBLE
            if (funcText.isNotEmpty()) searchFunc.text = funcText
        } else {
            searchFunc.visibility = View.GONE
        }
        typedArray.recycle()
    }

    /**
     * 打开过滤弹框
     */
    private fun showFilterBubble() {
        if (filterBubble == null) {
            filterBubble = ZXBubbleView(context)
            val filterView = LayoutInflater.from(context).inflate(R.layout.layout_search_filter, null, false)
            val tvReset = filterView.findViewById<TextView>(R.id.tv_filter_reset)
            val tvSubmit = filterView.findViewById<TextView>(R.id.tv_filter_submit)
            val viewDivider = filterView.findViewById<View>(R.id.view_filter_divider)
            val rvList = filterView.findViewById<RecyclerView>(R.id.rv_filter_list)
            rvList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = SearchFilterAdapter(filterValues!!, module_color).apply {
                    filterAdater = this
                    setSelectCall { index, value ->
                        if (!rvList.isComputingLayout) {
                            val selectItemKey = filterValues!![index].filterName
                            filterValues?.forEachIndexed { index, it ->
                                if (it.visibleBy != null) {
                                    val lastEnable = it.isEnable
                                    if (it.visibleBy?.first == selectItemKey) {
                                        it.isEnable = it.visibleBy?.second == value
                                    }
                                    if (lastEnable != it.isEnable) {
                                        notifyItemChanged(index)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            tvReset.setTextColor(module_color)
            tvSubmit.setTextColor(module_color)
            viewDivider.setBackgroundColor(module_color)

            tvReset.setOnClickListener {
                //重置选中值
                filterValues!!.forEach {
                    val bean = it
                    it.values.forEach {
                        if (bean.filterType == SearchFilterBean.FilterType.EDIT_TYPE) {
                            it.value = ""
                        } else if (bean.filterType == SearchFilterBean.FilterType.SELECT_TYPE) {
                            it.isSelect = false
                        }
                    }
                }
                //重置列表状态
                rvList.adapter.notifyDataSetChanged()
                //重置搜索栏
                searchText.setText("")
                doFunc()
            }
            tvSubmit.setOnClickListener {
                doFunc()
                doSearch()
                filterBubble!!.dismiss()
            }

            filterBubble!!.setBubbleView(filterView, R.color.bubble_bg)
        }
        filterBubble!!.show(searchFunc, Gravity.BOTTOM)
    }

    /**
     * 设置光标样式
     */
    private fun setCursorDrawable() {
        try {
            val cursorFiled = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            cursorFiled.isAccessible = true
            val drawable = ContextCompat.getDrawable(context, R.drawable.shape_search_cursor)
            drawable!!.setTint(module_color)
            cursorFiled.set(searchText, drawable)
        } catch (e: java.lang.Exception) {

        }
    }

    //搜索功能-点击搜索
    fun setSearchListener(justSearchButton: Boolean = false, doSearch: (String) -> Unit) {
        this.justSearchButton = justSearchButton
        this@SearchView.doSearch = doSearch
    }

    //功能按钮
    fun setFuncListener(filterValues: List<SearchFilterBean>? = null, doFunc: () -> Unit) {
        if (filterValues != null) {
            filterValues.forEach {
                if (it.filterType == SearchFilterBean.FilterType.EDIT_TYPE && it.values.isEmpty()) {
                    it.values.add(SearchFilterBean.ValueBean("edit", ""))
                }
            }
        }
        this@SearchView.doFunc = doFunc
        this@SearchView.filterValues = filterValues
    }

    //获取筛选结果
    fun getFilterResult(): List<SearchFilterBean>? {
        return null
    }

    fun withXApp(xappBean: XAppBean?) {
        if (xappBean != null) {
            module_color = ContextCompat.getColor(context, xappBean.moduleColor)
        }
        resetColor()
    }

    private fun resetColor() {
        searchText.setTextColor(module_color)
        searchFunc.setTextColor(module_color)
        val searchDrawable = searchBtn.drawable.mutate()
        searchDrawable.setTint(module_color)
        searchBtn.setImageDrawable(searchDrawable)
        setCursorDrawable()
    }

    /**
     * 设置功能按钮可见性及文字
     */
    fun setFuncVisible(visible: Boolean, text: String = "筛选") {
        searchFunc.visibility = if (visible) View.VISIBLE else View.GONE
        searchFunc.text = text
    }

    fun notifyDataSetChanged() {
        filterAdater?.notifyDataSetChanged()
    }

    fun setSearchText(searchKeywords: String) {
        searchText.setText(searchKeywords)
    }

    private fun doSearch(closeKeybord: Boolean = true) {
        doSearch(searchText.text.toString())
        try {
            if (closeKeybord) ZXSystemUtil.closeKeybord(context as Activity)
        } catch (e: Exception) {
        }
    }

}