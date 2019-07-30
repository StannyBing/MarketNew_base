package com.zx.module_other.module.print.func.util

object GPrinterCommand {

    val left = byteArrayOf(0x1b, 0x61, 0x00)// 靠左
    val center = byteArrayOf(0x1b, 0x61, 0x01)// 居中
    val right = byteArrayOf(0x1b, 0x61, 0x02)// 靠右
    val bold = byteArrayOf(0x1b, 0x45, 0x01)// 选择加粗模式
    val bold_cancel = byteArrayOf(0x1b, 0x45, 0x00)// 取消加粗模式
    val text_normal_size = byteArrayOf(0x1d, 0x21, 0x00)// 字体不放大
    val text_big_height = byteArrayOf(0x1b, 0x21, 0x10)// 高加倍
    val text_big_size = byteArrayOf(0x1d, 0x21, 0x11)// 宽高加倍
    val reset = byteArrayOf(0x1b, 0x40)//复位打印机
    val print = byteArrayOf(0x0a)//打印并换行
    val under_line = byteArrayOf(0x1b, 0x2d, 2)//下划线
    val under_line_cancel = byteArrayOf(0x1b, 0x2d, 0)//下划线

    /**
     * 走纸
     *
     * @param n 行数
     * @return 命令
     */
    fun walkPaper(n: Byte): ByteArray {
        return byteArrayOf(0x1b, 0x64, n)
    }

    /**
     * 设置横向和纵向移动单位
     *
     * @param x 横向移动
     * @param y 纵向移动
     * @return 命令
     */
    fun move(x: Byte, y: Byte): ByteArray {
        return byteArrayOf(0x1d, 0x50, x, y)
    }

}