package com.zx.module_supervise.module.supervise.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.KeyValueBean
import com.zx.module_supervise.R
import com.zx.module_supervise.module.supervise.bean.EntityInfoBean
import com.zx.module_supervise.module.supervise.func.adapter.DetailInfoAdapter
import com.zx.module_supervise.module.supervise.mvp.contract.DetailEntityInfoContract
import com.zx.module_supervise.module.supervise.mvp.model.DetailEntityInfoModel
import com.zx.module_supervise.module.supervise.mvp.presenter.DetailEntityInfoPresenter
import kotlinx.android.synthetic.main.fragment_detail_entity_info.*

/**
 * Create By admin On 2017/7/11
 * 功能：监管任务-详情-主体信息
 */
class DetailEntityInfoFragment : BaseFragment<DetailEntityInfoPresenter, DetailEntityInfoModel>(), DetailEntityInfoContract.View {

    private val dataList = arrayListOf<KeyValueBean>()
    private val mAdapter = DetailInfoAdapter(dataList)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DetailEntityInfoFragment {
            val fragment = DetailEntityInfoFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_detail_entity_info
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)


        rv_supervise_entityinfo.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = mAdapter
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    fun resetInfo(entityInfoBean: EntityInfoBean) {
        dataList.clear()
        dataList.add(KeyValueBean("主体名称", entityInfoBean.fEntityName))
        dataList.add(KeyValueBean("处理人", entityInfoBean.fHandleUser))
        dataList.add(KeyValueBean("是否合格", entityInfoBean.fQualify))
        dataList.add(KeyValueBean("联系方式", entityInfoBean.fContactInfo))
        dataList.add(KeyValueBean("法定代表人", entityInfoBean.fLegalPerson))
        dataList.add(KeyValueBean("营业执照注册号", entityInfoBean.fBizlicNum))
        dataList.add(KeyValueBean("组织机构代码", entityInfoBean.fOrgCode))
        dataList.add(KeyValueBean("许可证", entityInfoBean.fLicenses))
        dataList.add(KeyValueBean("地址", entityInfoBean.fAddress))
        mAdapter.notifyDataSetChanged()
    }
}
