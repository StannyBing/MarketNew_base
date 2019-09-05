package com.zx.marketnew_base.main.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import android.widget.TextView
import com.youth.banner.BannerConfig
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.OfficeBean
import com.zx.marketnew_base.main.func.util.GlideImageLoader
import com.zx.marketnew_base.main.mvp.contract.WorkInfoContract
import com.zx.marketnew_base.main.mvp.model.WorkInfoModel
import com.zx.marketnew_base.main.mvp.presenter.WorkInfoPresenter
import com.zx.module_library.XApp
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseFragment
import com.zx.zxutils.util.ZXSystemUtil
import kotlinx.android.synthetic.main.fragment_work_info.*

/**
 * Create By admin On 2017/7/11
 * 功能：办公-信息
 */
class WorkInfoFragment : BaseFragment<WorkInfoPresenter, WorkInfoModel>(), WorkInfoContract.View {

    private var banners = arrayListOf<OfficeBean.Banner>()
    private var notices = arrayListOf<OfficeBean.Notice>()

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

        initBanner()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //消息滚动条点击事件
        vf_info_view.setOnClickListener {
            val index = vf_info_view.currentView.tag as Int
            if (!notices[index].url.isNullOrEmpty()) {
                XApp.startXApp(RoutePath.ROUTE_LIBRARY_WEB) {
                    it["mTitle"] = notices[index].name
                    it["mUrl"] = notices[index].url!!
                }
            }
        }
    }

    //设置信息
    fun setInfo(officeBean: OfficeBean) {
        banners.clear()
        banners.addAll(officeBean.banner)
        notices.clear()
        notices.addAll(officeBean.notice)

        initBanner()
    }

    private fun initBanner() {
        if (banners.isNotEmpty() && banner_work_info != null) {
            val images = arrayListOf<String>()
            banners.forEach {
                //轮播控件
                images.add(BaseConfigModule.BASE_IP + it.info)
            }
            banner_work_info
                    .setImageLoader(GlideImageLoader())
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                    .isAutoPlay(true)
                    .setDelayTime(6000)
                    .setOnBannerListener { index ->
                        if (!banners[index].url.isNullOrEmpty()) {
                            XApp.startXApp(RoutePath.ROUTE_LIBRARY_WEB) {
                                it["mTitle"] = banners[index].name
                                it["mUrl"] = banners[index].url
                            }
                        }
                    }
                    .setImages(images)
                    .start()
        }
        if (notices.isNotEmpty() && vf_info_view != null) {
            notices.forEachIndexed { index, it ->
                val tv = TextView(activity!!)
                tv.apply {
                    text = it.info
                    setSingleLine(true)
                    textSize = ZXSystemUtil.px2sp(resources.getDimension(R.dimen.text_size_small))
                    setTextColor(ContextCompat.getColor(activity!!, R.color.text_color_noraml))
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                            .apply {
                                leftMargin = 10
                            }
                    tag = index
                }
                vf_info_view.addView(tv)
            }
        }
    }
}
