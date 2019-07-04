package com.zx.module_complain.module.info.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_complain.R
import com.zx.module_complain.module.info.bean.DetailBean
import com.zx.module_complain.module.info.func.adapter.DetailInfoAdapter
import com.zx.module_complain.module.info.func.tool.ComplainStatusTool
import com.zx.module_complain.module.info.mvp.contract.DetailInfoContract
import com.zx.module_complain.module.info.mvp.model.DetailInfoModel
import com.zx.module_complain.module.info.mvp.presenter.DetailInfoPresenter
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.KeyValueBean
import com.zx.zxutils.util.ZXTimeUtil
import kotlinx.android.synthetic.main.fragment_complain_detail_info.*

/**
 * Create By admin On 2017/7/11
 * 功能：投诉举报-详情-信息
 */
class DetailInfoFragment : BaseFragment<DetailInfoPresenter, DetailInfoModel>(), DetailInfoContract.View {

    private var type: Int = 0
    private val dataList = arrayListOf<KeyValueBean>()
    private val mAdapter = DetailInfoAdapter(dataList)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(type: Int): DetailInfoFragment {
            val fragment = DetailInfoFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_complain_detail_info
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        type = arguments!!.getInt("type")

        rv_complain_detailInfo.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = mAdapter
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    /**
     * 数据初始化
     */
    fun reSetInfo(infoBean: DetailBean.BaseInfo) {
            when (type) {
                0 -> {//登记信息
                    updateRegData(infoBean)
                }
                1 -> {//主体信息
                    updateEntityData(infoBean)
                }
                2 -> {//投诉内容
                    updateReportData(infoBean)
                }
                3 -> {//处置结果
                    updateResultData(infoBean)
                }
            }
            mAdapter.notifyDataSetChanged()
    }

    private fun updateRegData(infoBean: DetailBean.BaseInfo) {
        dataList.add(KeyValueBean("登记编号", infoBean.fRegId))
        dataList.add(KeyValueBean("登记时间", if (infoBean.fRegTime == 0L || infoBean.fRegTime == null) "" else ZXTimeUtil.getTime(infoBean.fRegTime!!)))
        dataList.add(KeyValueBean("登记人", infoBean.fRegName))
        dataList.add(KeyValueBean("简要情况", infoBean.fBriefInfo))
        dataList.add(KeyValueBean("信息来源", infoBean.fSource))
        dataList.add(KeyValueBean("当前状态", ComplainStatusTool.getStatusString(infoBean.fStatus)))
        dataList.add(KeyValueBean("投诉方", infoBean.fName))
        dataList.add(KeyValueBean("投诉人电话", infoBean.fTelephone))
    }

    private fun updateEntityData(infoBean: DetailBean.BaseInfo) {
        dataList.add(KeyValueBean("名称（姓名）", infoBean.fEntityName))
        dataList.add(KeyValueBean("联系电话", infoBean.fEntityPhone))
        dataList.add(KeyValueBean("行业类型", infoBean.fEntityType))
        dataList.add(KeyValueBean("地址", infoBean.fEntityAddress))
        dataList.add(KeyValueBean("商品名称", infoBean.fProductName))
        dataList.add(KeyValueBean("品牌名牌", infoBean.fBrandName))
        dataList.add(KeyValueBean("数量（单位）", infoBean.fCount))
        dataList.add(KeyValueBean("涉及金额", infoBean.fMoney))
    }

    private fun updateReportData(infoBean: DetailBean.BaseInfo) {
        dataList.add(KeyValueBean("投诉举报类别", infoBean.fType))
        dataList.add(KeyValueBean("投诉举报内容", infoBean.fContent))
        dataList.add(KeyValueBean("事发地", infoBean.fEventAddress))
        dataList.add(KeyValueBean("事发时间", if (infoBean.fEventTime == 0L || infoBean.fEventTime == null) "" else ZXTimeUtil.getTime(infoBean.fEventTime!!)))
        dataList.add(KeyValueBean("经济损失值", infoBean.fEconomyLost))
        dataList.add(KeyValueBean("性质", infoBean.fNature))
        dataList.add(KeyValueBean("紧急程度", infoBean.fUrgency))

        dataList.add(KeyValueBean("是否重大案件", infoBean.fSignificant))
        dataList.add(KeyValueBean("是否出现场", infoBean.fSpotNeed))
        dataList.add(KeyValueBean("是否重点关注", infoBean.fFollow))
        dataList.add(KeyValueBean("是否多次举报", infoBean.fRepeatedly))
        dataList.add(KeyValueBean("是否群诉", infoBean.fGroup))
    }

    private fun updateResultData(infoBean: DetailBean.BaseInfo) {
        dataList.add(KeyValueBean("受理类型", infoBean.fAcceptType))
        dataList.add(KeyValueBean("调解结果", infoBean.fMediationResult))
        dataList.add(KeyValueBean("反馈内容", infoBean.fFeedbackContent))
    }
}
