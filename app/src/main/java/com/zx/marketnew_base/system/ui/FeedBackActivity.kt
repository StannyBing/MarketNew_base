package com.zx.marketnew_base.system.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.marketnew_base.R
import com.zx.marketnew_base.system.mvp.contract.FeedBackContract
import com.zx.marketnew_base.system.mvp.model.FeedBackModel
import com.zx.marketnew_base.system.mvp.presenter.FeedBackPresenter
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.func.tool.toJson
import com.zx.zxutils.util.ZXDialogUtil
import kotlinx.android.synthetic.main.activity_feed_back.*


/**
 * Create By admin On 2017/7/11
 * 功能：意见反馈
 */
@Route(path = RoutePath.ROUTE_APP_SETTING_FEEDBACK)
class FeedBackActivity : BaseActivity<FeedBackPresenter, FeedBackModel>(), FeedBackContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, FeedBackActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_feed_back
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        toolbar_view.setRightClickListener {
            if (et_feedback_content.text.toString().isEmpty()) {
                showToast("请先输入反馈内容")
                return@setRightClickListener
            }
            ZXDialogUtil.showYesNoDialog(this, "提示", "是否提交意见反馈？") { _, _ ->
                mPresenter.addFeedBack(hashMapOf("feedbackDate" to System.currentTimeMillis(), "feedbackContent" to et_feedback_content.text.toString()).toJson())
            }
        }
    }

    override fun onFeedBackResult() {
        showToast("意见反馈提交成功！")
        finish()
    }

}
