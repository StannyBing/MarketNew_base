package com.zx.marketnew_base.main.func.adapter

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.zx.marketnew_base.R
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.bean.UserBean
import com.zx.module_library.func.tool.GlideRoundTransformation
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import com.zx.zxutils.util.ZXSystemUtil


/**
 * Created by Xiangb on 2019/3/8.
 * 功能：通讯录列表适配器
 */
class MailListAdapter(dataBeans: List<UserBean>) : ZXQuickAdapter<UserBean, ZXBaseHolder>(R.layout.item_maillist, dataBeans) {
    override fun convert(helper: ZXBaseHolder?, item: UserBean?) {
        if (item != null && helper != null) {
            if (item.listType == 0) {
                helper.getView<ImageView>(R.id.iv_maillist_head).visibility = View.GONE
                helper.getView<TextView>(R.id.tv_maillist_head).visibility = View.GONE
                helper.getView<ImageView>(R.id.iv_maillist_star).visibility = View.GONE
                helper.getView<TextView>(R.id.tv_maillist_duty).visibility = View.GONE
                helper.getView<View>(R.id.view_maillist_divider).visibility = View.GONE
                helper.setTextColor(R.id.tv_maillist_name, ContextCompat.getColor(mContext, R.color.text_color_drak))
                helper.getView<TextView>(R.id.tv_maillist_name).paint.isFakeBoldText = true
                helper.getView<TextView>(R.id.tv_maillist_name).layoutParams = helper.getView<TextView>(R.id.tv_maillist_name).layoutParams.apply {
                    width = LinearLayout.LayoutParams.MATCH_PARENT
                }
            } else {
                Glide.with(mContext)
                        .load(BaseConfigModule.BASE_IP+item.imgUrl)
                        .apply(RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.mipmap.ic_launcher)
                                .placeholder(R.mipmap.ic_launcher)
                                .transform(GlideRoundTransformation(mContext))
                        ).listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                helper.getView<TextView>(R.id.tv_maillist_head).visibility = View.VISIBLE
                                helper.getView<ImageView>(R.id.iv_maillist_head).visibility = View.GONE
                                helper.setText(R.id.tv_maillist_head, item.realName.substring(item.realName.length - 2, item.realName.length))
                                return true
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                helper.getView<TextView>(R.id.tv_maillist_head).visibility = View.GONE
                                helper.getView<ImageView>(R.id.iv_maillist_head).visibility = View.VISIBLE
                                return false
                            }

                        })
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(helper.getView<ImageView>(R.id.iv_maillist_head))
                helper.getView<ImageView>(R.id.iv_maillist_star).visibility = View.VISIBLE
                helper.getView<TextView>(R.id.tv_maillist_duty).visibility = View.VISIBLE
                helper.getView<View>(R.id.view_maillist_divider).visibility = View.VISIBLE
                helper.setTextColor(R.id.tv_maillist_name, ContextCompat.getColor(mContext, R.color.text_color_noraml))
                helper.getView<TextView>(R.id.tv_maillist_name).paint.isFakeBoldText = false
                helper.getView<TextView>(R.id.tv_maillist_name).layoutParams = helper.getView<TextView>(R.id.tv_maillist_name).layoutParams.apply {
                    width = ZXSystemUtil.dp2px(80.0f)
                }
            }
            helper.setText(R.id.tv_maillist_name, item.realName)
            helper.setText(R.id.tv_maillist_duty, item.department)
            if (item.department.contains("局长") || item.department.contains("领导")
                    || item.department.contains("科长") || item.department.contains("主任")
                    || item.department.contains("队长") || item.department.contains("所长")
                    || item.department.contains("书记")) {
                helper.setVisible(R.id.iv_maillist_star, true)
            } else {
                helper.setVisible(R.id.iv_maillist_star, false)
            }
        }
    }
}