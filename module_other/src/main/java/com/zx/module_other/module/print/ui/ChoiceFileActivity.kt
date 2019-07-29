package com.zx.module_other.module.print.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther

import com.zx.module_other.module.print.mvp.contract.ChoiceFileContract
import com.zx.module_other.module.print.mvp.model.ChoiceFileModel
import com.zx.module_other.module.print.mvp.presenter.ChoiceFilePresenter
import kotlinx.android.synthetic.main.activity_choice_file.*


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class ChoiceFileActivity : BaseActivity<ChoiceFilePresenter, ChoiceFileModel>(), ChoiceFileContract.View {

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, ChoiceFileActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_choice_file
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar_view.withXApp(XAppOther.get("文件打印"))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

}
