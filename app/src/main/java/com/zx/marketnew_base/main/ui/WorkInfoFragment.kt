package com.zx.marketnew_base.main.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import android.widget.TextView
import com.youth.banner.BannerConfig
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.func.util.GlideImageLoader
import com.zx.marketnew_base.main.mvp.contract.WorkInfoContract
import com.zx.marketnew_base.main.mvp.model.WorkInfoModel
import com.zx.marketnew_base.main.mvp.presenter.WorkInfoPresenter
import com.zx.module_library.base.BaseFragment
import com.zx.zxutils.util.ZXSystemUtil
import kotlinx.android.synthetic.main.fragment_work_info.*

/**
 * Create By admin On 2017/7/11
 * 功能：办公-信息
 */
class WorkInfoFragment : BaseFragment<WorkInfoPresenter, WorkInfoModel>(), WorkInfoContract.View {
    companion object {
        /**
         * 启动器
         */
        fun newInstance(): WorkInfoFragment {
            val fragment = WorkInfoFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_work_info
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        val images = arrayListOf<String>()
        for (i in 0..5) {
            images.add("http://jk.xxu.edu.cn/images/logo.jpg")
        }

        //轮播控件
        banner_work_info
                .setImageLoader(GlideImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .isAutoPlay(true)
                .setDelayTime(6000)
                .setOnBannerListener {
                    showToast(it.toString())
                }
                .setImages(images)
                .start()

        for (i in 0..5) {
            val tv = TextView(activity)
            tv.apply {
                text = "关于移动监督管理APP的使用通知$i"
                setSingleLine(true)
                textSize = ZXSystemUtil.px2sp(resources.getDimension(R.dimen.text_size_small))
                setTextColor(ContextCompat.getColor(activity!!, R.color.text_color_noraml))
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        .apply {
                            leftMargin = 10
                        }
                tag = i
            }
            vf_info_view.addView(tv)
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //消息滚动条点击事件
        vf_info_view.setOnClickListener {
            showToast(vf_info_view.currentView.tag.toString())
        }
    }
}
