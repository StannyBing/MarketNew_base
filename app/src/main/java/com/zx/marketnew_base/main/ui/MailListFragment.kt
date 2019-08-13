package com.zx.marketnew_base.main.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.MailListBean
import com.zx.marketnew_base.main.func.adapter.MailListAdapter
import com.zx.marketnew_base.main.mvp.contract.MailListContract
import com.zx.marketnew_base.main.mvp.model.MailListModel
import com.zx.marketnew_base.main.mvp.presenter.MailListPresenter
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.UserBean
import kotlinx.android.synthetic.main.fragment_mail_list.*

/**
 * Create By admin On 2017/7/11
 * 功能：通讯录
 */
class MailListFragment : BaseFragment<MailListPresenter, MailListModel>(), MailListContract.View {

    var dataBeans = arrayListOf<UserBean>()
    var listAdapter = MailListAdapter(dataBeans)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): MailListFragment {
            val fragment = MailListFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_mail_list
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        rv_maillist_list.layoutManager = LinearLayoutManager(activity)
        rv_maillist_list.adapter = listAdapter

        mPresenter.getMailList(hashMapOf())


//        for (i in 0..5) {
//            dataBeans.add(UserBean("", "", listType = 0))
//            for (j in 0..5) {
//                dataBeans.add(UserBean("", "", listType = 1))
//            }
//        }
//        listAdapter.notifyDataSetChanged()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //搜索事件
        sv_mailList_search.setSearchListener {
            if (it.isEmpty()) {
                rv_maillist_list.scrollToPosition(0)
                return@setSearchListener
            }
            for (index in 0 until dataBeans.size) {
                if (dataBeans[index].realName.contains(it)) {
//                    var topSmoothScroller: TopSmoothScroller = TopSmoothScroller(mActivity)
//                    topSmoothScroller.setTargetPosition(index);
//                    rv_maillist_list.layoutManager?.startSmoothScroll(topSmoothScroller);
                    (rv_maillist_list.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(index, 0);
                    (rv_maillist_list.layoutManager as LinearLayoutManager).stackFromEnd = true
                    return@setSearchListener
                }
            }
            showToast("没有搜索到$it")
        }
        //列表点击事件
        listAdapter.setOnItemClickListener { adapter, view, position ->
            if (dataBeans[position].listType == 1) {
                this@MailListFragment.activity?.let { UserDetailActivity.startAction(it, false, dataBeans[position]) }
            }
        }
    }

    override fun onMailListResult(mailListBeans: List<MailListBean>) {
//        dataBeans.clear()
        if (mailListBeans.isNotEmpty()) {
            mailListBeans.forEach {
                dataBeans.add(UserBean(it.id, "", realName = it.realName, department = it.remark
                        ?: "", imgUrl = it.imgUrl ?: "", listType = 0))
                if (it.children.isNotEmpty()) {
                    it.children.forEach {
                        dataBeans.add(UserBean(it.id, "", realName = it.realName, department = it.remark
                                ?: "", imgUrl = it.imgUrl ?: "", listType = 1))
                    }
                }
            }
        }
        listAdapter.setNewData(dataBeans)
//        listAdapter.notifyDataSetChanged()
    }

    internal class TopSmoothScroller(context: Context) : LinearSmoothScroller(context) {
        override fun getHorizontalSnapPreference(): Int {
            return LinearSmoothScroller.SNAP_TO_START
        }

        override fun getVerticalSnapPreference(): Int {
            return LinearSmoothScroller.SNAP_TO_START
        }
    }
}
