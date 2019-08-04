package com.zx.module_library.module.camera.func.listener

import com.zx.module_library.bean.FileBean

/**
 * Created by Xiangb on 2019/6/28.
 * 功能：
 */
interface FileFuncListener {
    fun onFileDelete(fileBean: FileBean, position: Int) {}

    fun onFileAdd(fileBean: FileBean, position: Int) {}

    fun onFileClick(fileBean: FileBean, position: Int) {}
}