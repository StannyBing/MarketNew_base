package com.zx.marketnew_base.main.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zx.marketnew_base.R
import com.zx.marketnew_base.api.ApiParamUtil
import com.zx.marketnew_base.main.bean.MessageBean
import com.zx.marketnew_base.main.func.adapter.MessageAdapter
import com.zx.marketnew_base.main.mvp.contract.MessageContract
import com.zx.marketnew_base.main.mvp.model.MessageModel
import com.zx.marketnew_base.main.mvp.presenter.MessagePresenter
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.NormalList
import com.zx.module_library.func.tool.UserManager
import com.zx.zxutils.views.RecylerMenu.ZXRecyclerDeleteHelper
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * Create By admin On 2017/7/11
 * 功能：消息
 */
class MessageFragment : BaseFragment<MessagePresenter, MessageModel>(), MessageContract.View {

    var pageNo = 1
    var searchText = ""
    var isRefresh = false
    var dataBeans = arrayListOf<MessageBean>()
    var listAdapter = MessageAdapter(dataBeans)

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): MessageFragment {
            val fragment = MessageFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_message
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        rv_message_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }
        sr_message.setColorSchemeResources(R.color.colorPrimary)
        ZXRecyclerDeleteHelper(activity, rv_message_list)
                .setClickable { showToast("点击$it") }
                .setSwipeOptionViews(R.id.tv_delete)
                .setSwipeable(R.id.rl_content, R.id.ll_menu) { viewID, position ->
                    showToast("删除$position")
                }
        loadData()
    }

    /**
     * 数据加载
     */
    fun loadData(refresh: Boolean = false) {
        if (refresh) {
            isRefresh = true
            pageNo = 1
        } else {
            pageNo++
        }
        mPresenter.getMessageList(ApiParamUtil.messageListParam(UserManager.getUser().id, pageNo, searchText))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //搜索事件
        sv_message_search.setSearchListener {
            searchText = it
            loadData(true)
        }
        //刷新事件
        sr_message.setOnRefreshListener { loadData(true) }
        //加载更多事件
        listAdapter.setOnLoadMoreListener({
            loadData()
        }, rv_message_list)
    }

    /**
     * 消息列表
     */
    override fun onMessageListResult(messageList: NormalList<MessageBean>) {
        if (isRefresh) {
            isRefresh = false
            sr_message.isRefreshing = false
            dataBeans.clear()
            dataBeans.addAll(messageList.list)
            listAdapter.setNewData(dataBeans)
        } else {
            dataBeans.addAll(messageList.list)
            listAdapter.notifyDataSetChanged()
        }

        if (pageNo < messageList.pages && messageList.pages > 0) {
            listAdapter.loadMoreComplete()
        } else {
            listAdapter.loadMoreEnd()
        }
    }
}
