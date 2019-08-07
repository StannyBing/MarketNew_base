package com.zx.marketnew_base.main.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.zx.marketnew_base.R
import com.zx.marketnew_base.api.ApiParamUtil
import com.zx.marketnew_base.main.bean.MessageBean
import com.zx.marketnew_base.main.func.adapter.MessageAdapter
import com.zx.marketnew_base.main.mvp.contract.MessageContract
import com.zx.marketnew_base.main.mvp.model.MessageModel
import com.zx.marketnew_base.main.mvp.presenter.MessagePresenter
import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.NormalList
import com.zx.module_library.func.tool.UserManager
import com.zx.module_library.func.tool.toJson
import com.zx.zxutils.util.ZXDialogUtil
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

    var clickItemPosition = 0
    var deleteItemPosition = 0

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
                .setClickable {
                    clickItemPosition = it
                    mPresenter.getMessageDetail(hashMapOf("guid" to dataBeans[it].guid))
                    if (dataBeans[it].text.isNullOrEmpty()) {
                        ZXDialogUtil.showInfoDialog(activity, dataBeans[it].title, dataBeans[it].ticker)
                        return@setClickable
                    }
                    var textBean: MessageBean.TextBean? = null
                    try {
                        textBean = Gson().fromJson<MessageBean.TextBean>(dataBeans[it].text!!, MessageBean.TextBean::class.java)
                    } catch (e: Exception) {

                    }
                    when (textBean?.business) {
                        "专项检查" -> {
                            XApp.startXApp(RoutePath.ROUTE_SUPERVISE_TASK_DETAIL) {
                                it["id"] = textBean.businessId
                                it["taskId"] = textBean.taskId
                                it["optable"] = true
                            }
                        }
                        "投诉举报" -> {
                            XApp.startXApp(RoutePath.ROUTE_COMPLAIN_TASK_DETAIL) {
                                it["fGuid"] = textBean.businessId
                            }
                        }
                        "综合执法" -> {
                            XApp.startXApp(RoutePath.ROUTE_LEGALCASE_TASK_DETAIL) {
                                it["id"] = textBean.businessId
                                it["taskId"] = textBean.taskId
                                it["optable"] = true
                                it["processType"] = textBean.processType
                            }
                        }
                        "版本更新" -> {
                            XApp.startXApp(RoutePath.ROUTE_APP_SETTING) {
                                it["checkVerson"] = true
                            }
                        }
                        else -> {
                            ZXDialogUtil.showInfoDialog(activity, dataBeans[it].title, dataBeans[it].ticker)
                        }
                    }
                }
                .setSwipeOptionViews(R.id.tv_delete)
                .setSwipeable(R.id.rl_content, R.id.ll_menu) { viewID, position ->
                    deleteItemPosition = position
                    mPresenter.deleteMessage(hashMapOf("guid" to dataBeans[position].guid).toJson())
                }
        loadData(true)

    }

    /**
     * 数据加载
     */
    fun loadData(refresh: Boolean = false) {
        if (refresh) {
            isRefresh = true
            pageNo = 1
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
            pageNo++
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

        if (pageNo * 15 < messageList.total) {
            listAdapter.loadMoreComplete()
        } else {
            listAdapter.loadMoreEnd()
        }
    }

    override fun onMessageDetailResult(messageBean: MessageBean) {
        dataBeans[clickItemPosition].isRead = 1
        listAdapter.notifyItemChanged(clickItemPosition)
    }

    override fun onMessageDeleteResult() {
        dataBeans.removeAt(deleteItemPosition)
        listAdapter.notifyItemRemoved(deleteItemPosition)
    }

    override fun onResume() {
        super.onResume()
        loadData(true)
    }
}
