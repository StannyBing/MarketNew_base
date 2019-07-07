package com.zx.module_map.module.func.adapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.zx.module_map.R
import com.zx.module_map.module.bean.MapBtnBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

/**
 * Created by Xiangb on 2019/7/7.
 * 功能：
 */
@SuppressLint("NewApi")
class BtnAdapter(dataList : List<MapBtnBean>) :ZXQuickAdapter<MapBtnBean, ZXBaseHolder>(R.layout.item_map_btn, dataList) {

    private var color : Int?=null

    override fun convert(helper: ZXBaseHolder?, item: MapBtnBean?) {
        if (helper!=null&&item!=null){
            val drawable = ContextCompat.getDrawable(mContext, item.drawable)
            if (color!=null)drawable?.setTint(color!!)
            drawable?.mutate()
            helper.getView<ImageView>(R.id.iv_map_btn).setImageDrawable(drawable)

            helper.setText(R.id.tv_map_btn, item.name)
            helper.setTextColor(R.id.tv_map_btn, color!!)
        }
    }

    fun setColor(color : Int){
        this.color = color
    }
}