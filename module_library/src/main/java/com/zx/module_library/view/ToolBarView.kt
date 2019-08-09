package com.zx.module_library.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.zx.module_library.R
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/5.
 * 功能：顶部状态栏
 */
@SuppressLint("NewApi")
class ToolBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var toolbarBack: LinearLayout//返回按钮
    private lateinit var toolbarBackText: TextView//返回文字
    private lateinit var toolbarBackPic: ImageView//返回图片
    private lateinit var toolbarMidPic: ImageView//中间图片
    private lateinit var toolbarTitle: TextView//中间标题
    private lateinit var toolbarRightImage: ImageView//右侧图片
    private lateinit var toolbarRightText: TextView//右侧文字
    private lateinit var toolbarBg: RelativeLayout//toolbar
    private var titleText = ""
    private var backText = "返回"
    private var showRightPic = false//是否展示右侧按钮
    private var showRightText = false//是否展示右侧文字
    private var midPic: Drawable? = null//中间图片
    private var rightPic: Drawable? = null//右侧图片
    private var rightText: String = "保存"//右侧文字
    private var module_color: Int//xapp主题色
    private var bg_color: Int//title背景色

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ToolBarView, defStyleAttr, 0)
        titleText = if (typedArray.hasValue(R.styleable.ToolBarView_title_text)) typedArray.getString(R.styleable.ToolBarView_title_text) else ""
        backText = if (typedArray.hasValue(R.styleable.ToolBarView_back_text)) typedArray.getString(R.styleable.ToolBarView_back_text) else "返回"
        midPic = typedArray.getDrawable(R.styleable.ToolBarView_midpic_bg)
        rightPic = typedArray.getDrawable(R.styleable.ToolBarView_rightpic_bg)
        rightText = if (typedArray.hasValue(R.styleable.ToolBarView_right_text)) typedArray.getString(R.styleable.ToolBarView_right_text) else ""
        showRightPic = typedArray.getBoolean(R.styleable.ToolBarView_show_rightpic, false)
        showRightText = typedArray.getBoolean(R.styleable.ToolBarView_show_righttext, false)
        module_color = typedArray.getColor(R.styleable.ToolBarView_module_color, ContextCompat.getColor(context, R.color.colorPrimary))
        bg_color = typedArray.getColor(R.styleable.ToolBarView_bg_color, ContextCompat.getColor(context, R.color.white))
        init(context)
        typedArray.recycle()
    }

    private fun init(context: Context) {
        inflate(context, R.layout.layout_tool_bar, this)
        toolbarBack = findViewById(R.id.tool_bar_back)
        toolbarBackText = findViewById(R.id.tool_bar_backText)
        toolbarBackPic = findViewById(R.id.tool_bar_back_pic)
        toolbarMidPic = findViewById(R.id.tool_bar_midpic)
        toolbarTitle = findViewById(R.id.tool_bar_title)
        toolbarRightImage = findViewById(R.id.tool_bar_rightImage)
        toolbarRightText = findViewById(R.id.tool_bar_rightText)
        toolbarBg = findViewById(R.id.tool_bar_bg)

        toolbarBackText.text = backText
        toolbarTitle.text = titleText
        toolbarMidPic.visibility = if (midPic == null) View.GONE else View.VISIBLE
        toolbarMidPic.background = midPic
        toolbarRightImage.visibility = if (showRightPic) View.VISIBLE else View.GONE
        toolbarRightText.visibility = if (showRightText) View.VISIBLE else View.GONE
        if (rightPic != null) toolbarRightImage.setImageDrawable(rightPic)
        toolbarRightText.text = rightText

        toolbarBg.setBackgroundColor(bg_color)
        resetColor()

        toolbarBack.setOnClickListener { v -> (context as Activity).onBackPressed() }
    }

    /**
     * 设置右侧按钮点击事件
     */
    fun setRightClickListener(onClick: () -> Unit) {
        toolbarRightImage.setOnClickListener { onClick() }
        toolbarRightText.setOnClickListener { onClick() }
    }

    fun setMidClickListener(onClick: () -> Unit) {
        toolbarTitle.setOnClickListener { onClick() }
    }

    /**
     * 设置中间文字
     */
    fun setMidText(titleText: String?) {
        toolbarTitle.text = titleText
        toolbarTitle.ellipsize = TextUtils.TruncateAt.MARQUEE
        toolbarTitle.setSingleLine(true)
        toolbarTitle.isSelected = true
        toolbarTitle.isFocusable = true
        toolbarTitle.isFocusableInTouchMode = true
    }

    fun showRightImg(@DrawableRes imgRes: Int) {
        toolbarRightImage.visibility = View.VISIBLE
        toolbarRightImage.setBackgroundResource(imgRes)
    }

    fun showRightImg() {
        toolbarRightImage.visibility = View.VISIBLE
    }

    fun showRightText() {
        toolbarRightText.visibility = View.VISIBLE
    }

    fun showRightText(text: String) {
        toolbarRightText.visibility = View.VISIBLE
        toolbarRightText.setText(text)
    }

    fun withXApp(xappBean: XAppBean) {
        module_color = ContextCompat.getColor(context, xappBean.moduleColor)
        resetColor()
    }

    private fun resetColor() {
        val backDrawable = toolbarBackPic.drawable.mutate()
        val mapDrawable = toolbarRightImage.drawable.mutate()
        toolbarBackText.setTextColor(module_color)
        toolbarTitle.setTextColor(ContextCompat.getColor(context, R.color.text_color_noraml))
        toolbarRightText.setTextColor(module_color)
        backDrawable.setTint(module_color)
        mapDrawable.setTint(module_color)
    }

    /**
     * 设置中间图片
     */
    fun setMidPic(@DrawableRes imgRes: Int) {
        toolbarMidPic.visibility = View.VISIBLE
        toolbarMidPic.background = ContextCompat.getDrawable(context, imgRes)
    }
}