package com.zx.module_other.module.law.func.util

import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * Created by Xiangb on 2019/6/20.
 * 功能：
 */
class util{
    companion object{
        fun getHtmlText(html:String):Spanned{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
            } else {
                return Html.fromHtml(html);
            }
        }
    }
}