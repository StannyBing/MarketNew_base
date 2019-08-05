package com.zx.marketnew_base.main.func.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.XAppListBean
import com.zx.module_library.bean.XAppBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：XApp列表的适配器
 */
class WorkXAppListAdapter(dataBeans: ArrayList<XAppListBean>) : ZXQuickAdapter<XAppListBean, ZXBaseHolder>(R.layout.item_work_xapplist, dataBeans) {

    private var onManagerClick: (XAppListBean.XTYPE, Int) -> Unit = { _, _ -> }//manager的点击事件
    private var onXAppClick: (String, XAppBean) -> Unit = { _, _ -> }//xapp点击事件

    override fun convert(helper: ZXBaseHolder?, item: XAppListBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_xappList_title, item.title)
            when (item.type) {
                XAppListBean.XTYPE.TASK_STATISTICS -> {//待办统计
                    helper.getView<TextView>(R.id.tv_xappList_manager).visibility = View.GONE
                }
                XAppListBean.XTYPE.MY_XAPP -> {//我常用的
                    helper.getView<TextView>(R.id.tv_xappList_manager).visibility = View.GONE
                    helper.setText(R.id.tv_xappList_manager, "编辑")
                }
                XAppListBean.XTYPE.NORMAL_XAPP -> {//常用应用
                    helper.getView<TextView>(R.id.tv_xappList_manager).visibility = View.GONE
                }
                XAppListBean.XTYPE.ALL_XAPP -> {//所有应用
                    helper.getView<TextView>(R.id.tv_xappList_manager).visibility = View.VISIBLE
                    helper.setText(R.id.tv_xappList_manager, if (item.isOpen) "收起" else "展开")
                    if (item.xAppList.size <= 8) {
                        helper.getView<TextView>(R.id.tv_xappList_manager).visibility = View.GONE
                    }
                }
            }
            helper.getView<TextView>(R.id.tv_xappList_manager).setOnClickListener {
                item.isOpen = !item.isOpen
                notifyItemChanged(helper.adapterPosition)
//                onManagerClick(item.type, helper.adapterPosition)
            }

            helper.getView<RecyclerView>(R.id.rv_xapplist)?.apply {
                layoutManager = GridLayoutManager(mContext, 4) as RecyclerView.LayoutManager?
                val xAppAdapter = XAppAdapter(if (!item.isOpen && item.xAppList.size > 8) {
                    item.xAppList.subList(0, 8)
                } else {
                    item.xAppList
                }, item.type)
                adapter = xAppAdapter
                xAppAdapter.setOnItemClickListener { _, _, position -> onXAppClick(item.title, item.xAppList[position]) }
            }
        }
    }

    fun setXAppClickLiistener(onXAppClick: (String, XAppBean) -> Unit) {
        this.onXAppClick = onXAppClick
    }

    fun setManagerClickListener(onClick: (XAppListBean.XTYPE, Int) -> Unit) {
        onManagerClick = onClick
    }
}