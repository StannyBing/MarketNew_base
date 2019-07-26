package com.zx.marketnew_base.main.func.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zx.marketnew_base.R
import com.zx.marketnew_base.main.bean.MessageBean
import com.zx.module_library.func.tool.GlideRoundTransformation
import com.zx.module_library.func.tool.TaskTimeUtil
import com.zx.zxutils.ZXApp
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/3/14.
 * 功能：任务通用适配器
 */
class MessageAdapter(databeans: List<MessageBean>) : ZXQuickAdapter<MessageBean, ZXBaseHolder>(R.layout.item_message, databeans) {
    override fun convert(helper: ZXBaseHolder?, item: MessageBean?) {
        if (helper != null && item != null) {
            helper.setText(R.id.tv_message_itemTitle, item.title)
            helper.setText(R.id.tv_message_itemInfo, item.text)
            helper.setText(R.id.tv_message_itemDate, TaskTimeUtil.displayTime(item.inserDate))
            Glide.with(ZXApp.getContext()).load(R.drawable.app_task_msg)
                    .apply(RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .transform(GlideRoundTransformation(ZXApp.getContext()))
                    )
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(helper.getView(R.id.iv_task_itemPic))
        }
    }

}