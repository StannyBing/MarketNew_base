package com.zx.module_library.func.adapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zx.module_library.R
import com.zx.module_library.bean.FileBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import com.zx.zxutils.util.ZXTimeUtil

/**
 * Created by Xiangb on 2019/6/28.
 * 功能：
 */
@SuppressLint("NewApi")
class AddFileAdapter(dataList: List<FileBean>) : ZXQuickAdapter<FileBean, ZXBaseHolder>(R.layout.item_file_handle, dataList) {

    private var module_color: Int = 0
    private var showDelete: Boolean = false

    override fun convert(helper: ZXBaseHolder?, item: FileBean?) {
        if (helper != null && item != null) {
            val drawable = ContextCompat.getDrawable(mContext, R.drawable.file_default)!!.mutate()
            drawable!!.setTint(module_color)
            val typeId = when (item.getType()) {
                FileType.IMAGE -> {
                    Glide.with(mContext)
                            .load(item.filePath)
                            .apply(RequestOptions().placeholder(drawable).error(drawable))
                            .into(helper.getView(R.id.iv_file_icon))
                    R.drawable.file_type_image
                }
                FileType.VIDEO -> {
                    Glide.with(mContext)
                            .load(item.filePath)
                            .apply(RequestOptions().placeholder(drawable).error(drawable))
                            .into(helper.getView(R.id.iv_file_icon))
                    R.drawable.file_type_video
                }
                FileType.OTHER -> {
                    Glide.with(mContext)
                            .load(drawable)
                            .apply(RequestOptions().placeholder(drawable).error(drawable))
                            .into(helper.getView(R.id.iv_file_icon))
                    R.drawable.file_type_other
                }
            }
            if (module_color != 0) {
                val typeDrawable = ContextCompat.getDrawable(mContext, typeId)!!.mutate()
                typeDrawable!!.setTint(module_color)
                helper.getView<ImageView>(R.id.iv_file_type).background = typeDrawable
            }

            helper.setText(R.id.tv_file_name, item.fileName)
            helper.setVisible(R.id.iv_file_delete, showDelete)
            if (!item.date.isNullOrEmpty()) {
                helper.setText(R.id.tv_file_date, item.date)
            } else if (item.dateLong != null && item.dateLong != 0L) {
                helper.setText(R.id.tv_file_date, ZXTimeUtil.millis2String(item.dateLong!!))
            } else {
                helper.setText(R.id.tv_file_date, "")
            }

            helper.addOnClickListener(R.id.iv_file_delete)
        }
    }

    fun init(module_color: Int = 0, showDelete: Boolean = false) {
        this.module_color = module_color
        this.showDelete = showDelete
    }

    private fun FileBean.getType(): FileType {
        if (fileName == null) {
            return FileType.OTHER
        }
        if (fileName!!.endsWith("png") || fileName!!.endsWith("jpg")) {
            return FileType.IMAGE
        } else if (fileName!!.endsWith("mp4") || fileName!!.endsWith("rmvb") || fileName!!.endsWith("3gp") || fileName!!.endsWith("avi")) {
            return FileType.VIDEO
        } else {
            return FileType.OTHER
        }
    }

    enum class FileType {
        IMAGE,
        VIDEO,
        OTHER
    }
}
