package com.zx.module_other.module.law.func.adapter

import android.net.Uri
import android.os.Build
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.zx.module_other.R
import com.zx.module_other.module.law.bean.LawStandardQueryBean
import com.zx.module_other.module.law.func.util.util
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXMultiItemQuickAdapter
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity
import kotlinx.android.synthetic.main.activity_law_detail.*

class LawStandardQueryAdapter<T : MultiItemEntity> : ZXMultiItemQuickAdapter<T, ZXBaseHolder> {
    constructor(datas: List<T>) : super(datas) {
        addItemType(1, R.layout.item_string)
        addItemType(2, R.layout.item_law_standard)
    }

    override fun convert(helper: ZXBaseHolder?, item: T?) {
        if (helper != null && item != null) {
            if (item is LawStandardQueryBean) {
                if (item.itemType == 1) {
                    helper.setVisible(R.id.viewItemDivider, true)
                    helper.setTextColor(R.id.tvLawName, ContextCompat.getColor(mContext, R.color.text_color_drak))
                    helper.getView<TextView>(R.id.tvLawName).setPadding(mContext.resources.getDimensionPixelOffset(R.dimen.dp_16), mContext.resources.getDimensionPixelOffset(R.dimen.dp_8), 0, mContext.resources.getDimensionPixelOffset(R.dimen.dp_8))
                    helper.setText(R.id.tvLawName, item.illegalAct)
                } else if (item.itemType == 2) {
                    helper.setText(R.id.tv_law_standard_queryId, helper.adapterPosition.toString())
                    helper.setText(R.id.tv_law_standard_type, "类型：" + item.type)
                    var content: Spanned? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        content = Html.fromHtml(item.illegalAct, Html.FROM_HTML_MODE_COMPACT);
                    } else {
                        content = Html.fromHtml(item.illegalAct);
                    }
                    helper.setText(R.id.tv_law_standard_content, item.illegalAct?.let { util.getHtmlText(it) })
                }
            }

        }
    }
}