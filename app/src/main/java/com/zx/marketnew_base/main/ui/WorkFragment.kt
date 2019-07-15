package com.zx.marketnew_base.main.ui

import android.os.Bundle
import com.zx.marketnew_base.R
import com.zx.marketnew_base.XAppMain
import com.zx.marketnew_base.api.ApiParamUtil
import com.zx.marketnew_base.main.bean.OfficeBean
import com.zx.marketnew_base.main.bean.XAppListBean
import com.zx.marketnew_base.main.func.adapter.WorkXAppListAdapter
import com.zx.marketnew_base.main.mvp.contract.WorkContract
import com.zx.marketnew_base.main.mvp.model.WorkModel
import com.zx.marketnew_base.main.mvp.presenter.WorkPresenter
import com.zx.module_complain.XAppComplain
import com.zx.module_entity.XAppEntity
import com.zx.module_legalcase.XAppLegalcase
import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.XAppBean
import com.zx.module_map.XAppMap
import com.zx.module_other.XAppOther
import com.zx.module_statistics.XAppStatistics
import com.zx.module_supervise.XAppSupervise
import com.zx.zxutils.other.ZXInScrollRecylerManager
import com.zx.zxutils.util.ZXFragmentUtil
import kotlinx.android.synthetic.main.fragment_work.*

/**
 * Create By admin On 2017/7/11
 * 功能：办公
 */
class WorkFragment : BaseFragment<WorkPresenter, WorkModel>(), WorkContract.View {

    var dataBeans = arrayListOf<XAppListBean>()
    var listAdapter: WorkXAppListAdapter = WorkXAppListAdapter(dataBeans)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): WorkFragment {
            val fragment = WorkFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_work
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        //添加信息界面
        ZXFragmentUtil.addFragment(childFragmentManager, WorkInfoFragment.newInstance(), R.id.fm_work_info)

        rv_work_xApp.apply {
            layoutManager = ZXInScrollRecylerManager(activity)
            adapter = listAdapter
        }

        if (mSharedPrefUtil.contains("officeBean")) {
            val officeBean = mSharedPrefUtil.getObject<OfficeBean>("officeBean")
            onOfficeResult(officeBean)
        } else {
            onOfficeResult(null)
        }
        mPresenter.getOfficeInfo()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //xapp编辑按钮点击事件
        listAdapter.setManagerClickListener {

        }
        //xapp点击事件
        listAdapter.setXAppClickLiistener { title, xapp ->
            mPresenter.sendXappOpt(ApiParamUtil.xappOptParam("APP", "办公", title, xapp.name))
            XApp.startXApp(xapp.appRoutePath)
        }
        //公司详情按钮点击事件
        iv_work_compannyInfo.setOnClickListener {
            XApp.startXApp(RoutePath.ROUTE_OTHER_WEB) {
                it["mTitle"] = "重庆知行宏图科技有限公司"
                it["mUrl"] = "http://www.zxgeo.com"
            }
        }
    }

    override fun onOfficeResult(officeBean: OfficeBean?) {
        dataBeans.clear()
        if (officeBean == null) {
            dataBeans.add(XAppListBean("待办统计", XAppListBean.XTYPE.TASK_STATISTICS, arrayListOf(
                    XAppMain.get("累计待办")!!.apply { num = 0 },
                    XAppMain.get("即将逾期")!!.apply { num = 0 },
                    XAppMain.get("已经逾期")!!.apply { num = 0 }
            )))
            dataBeans.add(XAppListBean("常用应用", XAppListBean.XTYPE.NORMAL_XAPP, getXAppList(listOf("主体查询", "投诉举报", "综合执法", "监管任务"))))
            dataBeans.add(XAppListBean("全部应用", XAppListBean.XTYPE.ALL_XAPP, getXAppList()))
        } else {
            mSharedPrefUtil.putObject("officeBean", officeBean)
            dataBeans.add(XAppListBean("待办统计", XAppListBean.XTYPE.TASK_STATISTICS, arrayListOf(
                    XAppMain.get("累计待办")!!.apply { num = officeBean.todo.allTask },
                    XAppMain.get("即将逾期")!!.apply { num = officeBean.todo.willOverdue },
                    XAppMain.get("已经逾期")!!.apply { num = officeBean.todo.overdue }
            )))
            if (officeBean.myXApp.isNotEmpty()) {
                dataBeans.add(XAppListBean("最近使用", XAppListBean.XTYPE.MY_XAPP, getXAppList(officeBean.myXApp)))
            }
            if (officeBean.normalXApp.isNotEmpty()) {
                dataBeans.add(XAppListBean("常用应用", XAppListBean.XTYPE.NORMAL_XAPP, getXAppList(officeBean.normalXApp)))
            }
            if (officeBean.allXApp.isNotEmpty()) {
                dataBeans.add(XAppListBean("全部应用", XAppListBean.XTYPE.ALL_XAPP, getXAppList(officeBean.allXApp)))
            }
        }
        listAdapter.notifyDataSetChanged()
    }

    /**
     * 获取XApp集合
     */
    private fun getXAppList(names: List<String> = emptyList()): List<XAppBean> {
        val xAppBeans = arrayListOf<XAppBean>()
        if (names.isNotEmpty()) {//根据id获取
            var xapp: XAppBean? = null
            for (it in names) {
                xapp = if (XAppComplain.get(it) != null) {
                    XAppComplain.get(it)
                } else if (XAppLegalcase.get(it) != null) {
                    XAppLegalcase.get(it)
                } else if (XAppStatistics.get(it) != null) {
                    XAppStatistics.get(it)
                } else if (XAppSupervise.get(it) != null) {
                    XAppSupervise.get(it)
                } else if (XAppOther.get(it) != null) {
                    XAppOther.get(it)
                } else if (XAppMap.get(it) != null) {
                    XAppMap.get(it)
                } else if (XAppEntity.get(it) != null) {
                    XAppEntity.get(it)
                } else {
                    null
                }
                if (xapp != null) {
                    xAppBeans.add(xapp)
                }
            }
        } else {//获取全部
            xAppBeans.addAll(XAppEntity.all())
            xAppBeans.addAll(XAppComplain.all())
            xAppBeans.addAll(XAppLegalcase.all())
            xAppBeans.addAll(XAppSupervise.all())
            xAppBeans.addAll(XAppStatistics.all())
            xAppBeans.addAll(XAppMap.all())
            xAppBeans.addAll(XAppOther.all())
        }
        return xAppBeans
    }

}
