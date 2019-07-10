package com.zx.module_legalcase.module.query.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_legalcase.R
import com.zx.module_legalcase.module.query.bean.DetailBean
import com.zx.module_legalcase.module.query.func.adapter.DetailInfoAdapter
import com.zx.module_legalcase.module.query.mvp.contract.DetailInfoContract
import com.zx.module_legalcase.module.query.mvp.model.DetailInfoModel
import com.zx.module_legalcase.module.query.mvp.presenter.DetailInfoPresenter
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.KeyValueBean
import com.zx.zxutils.util.ZXTimeUtil
import kotlinx.android.synthetic.main.fragment_detail_info.*

/**
 * Create By admin On 2017/7/11
 * 功能：案件执法-详情-信息
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
        return R.layout.fragment_detail_info
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        type = arguments!!.getInt("type")

        rv_legalcase_detailInfo.apply {
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
    fun reSetInfo(infoBean: DetailBean.InfoBean) {
        when (type) {
            0 -> {//案件信息
                updateCaseData(infoBean)
            }
            1 -> {//当事人
                updateEntityData(infoBean)
            }
        }
        mAdapter.notifyDataSetChanged()
    }

    private fun updateCaseData(infoBean: DetailBean.InfoBean) {
        dataList.add(KeyValueBean("当事人名称", infoBean.enterpriseName))
        dataList.add(KeyValueBean("营业执照注册号", infoBean.enterpriseBizlicNum))
        dataList.add(KeyValueBean("统一社会信用代码", infoBean.enterpriseCreditCode))
        dataList.add(KeyValueBean("法定代表人", infoBean.enterprisePerson))
        dataList.add(KeyValueBean("联系电话", infoBean.enterpriseContact))
        dataList.add(KeyValueBean("地址", infoBean.enterpriseAddress))
    }

    private fun updateEntityData(infoBean: DetailBean.InfoBean) {
        dataList.add(KeyValueBean("案件名称", infoBean.caseName))
        dataList.add(KeyValueBean("移交机构", infoBean.departmentName))
        dataList.add(KeyValueBean("信息来源", infoBean.typeName))
        dataList.add(KeyValueBean("案件领域", infoBean.domainName))
        dataList.add(KeyValueBean("移交时间", if (infoBean.foundDate == null || infoBean.foundDate == 0L) "" else ZXTimeUtil.getTime(infoBean.foundDate!!)))
        dataList.add(KeyValueBean("违法类型", infoBean.illegalityName))
        dataList.add(KeyValueBean("当前环节", infoBean.statusName))
        dataList.add(KeyValueBean("是否立案", if (infoBean.isCase == 0) "是" else "否"))
        dataList.add(KeyValueBean("案件内容", infoBean.regContent))
    }
}
