package com.zx.marketnew_base.system.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.R
import com.zx.marketnew_base.system.mvp.contract.FontSettingContract
import com.zx.marketnew_base.system.mvp.model.FontSettingModel
import com.zx.marketnew_base.system.mvp.presenter.FontSettingPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.zxutils.views.BubSeekBar.ZXSeekBar
import kotlinx.android.synthetic.main.activity_font_setting.*


/**
 * Create By admin On 2017/7/11
 * 功能：字体设置
 */
@Route(path = RoutePath.ROUTE_APP_SETTING_FONT)
class FontSettingActivity : BaseActivity<FontSettingPresenter, FontSettingModel>(), FontSettingContract.View {

    private var selectScale = 1.0f
    private var defaultNameSize = 1.0f
    private var defaultValueSize = 1.0f
    private var defaultToolBackSize = 1.0f
    private var defaultToolTitleSize = 1.0f

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, FontSettingActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_font_setting
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val fontScale = mSharedPrefUtil.getFloat("fontScale", 1.0f)
        sb_font.setRange(0.8f, 1.2f)
                .setProgress(fontScale)
                .setSectionMark(4, true)
                .setTrackColor(R.color.colorPrimary, R.color.bg_color)
                .setText(20, R.color.colorPrimary, ZXSeekBar.TextPosition.BELOW_SECTION_MARK)

        defaultNameSize = tv_name1.textSize / fontScale
        defaultValueSize = tv_value1.textSize / fontScale
        defaultToolBackSize = toolbar_view.toolbarBackText.textSize / fontScale
        defaultToolTitleSize = toolbar_view.toolbarTitle.textSize / fontScale
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        sb_font.onProgressChangedListener = object : ZXSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(progress: Int, progressFloat: Float) {
                selectScale = progressFloat
                if (defaultNameSize != 1.0f) {
                    tv_name1.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultNameSize * selectScale)
                    tv_name2.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultNameSize * selectScale)
                    tv_name3.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultNameSize * selectScale)
                    tv_name4.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultNameSize * selectScale)
                    tv_value1.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultValueSize * selectScale)
                    tv_value2.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultValueSize * selectScale)
                    tv_value3.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultValueSize * selectScale)
                    tv_value4.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultValueSize * selectScale)
                    tv_font_tips.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultNameSize * selectScale)
                    toolbar_view.toolbarBackText.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultToolBackSize * selectScale)
                    toolbar_view.toolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultToolTitleSize * selectScale)
                }
            }

            override fun getProgressOnActionUp(progress: Int, progressFloat: Float) {
            }

            override fun getProgressOnFinally(progress: Int, progressFloat: Float) {
            }
        }
    }

    override fun onBackPressed() {
        mSharedPrefUtil.putFloat("fontScale", selectScale)
        mRxManager.post("resetFontScale", "")
        showLoading("正在修改...")
        handler.postDelayed({
            dismissLoading()
            super.onBackPressed()
        }, 200)
    }
}