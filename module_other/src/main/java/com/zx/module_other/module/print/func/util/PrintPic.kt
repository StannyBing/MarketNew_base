package com.zx.module_other.module.print.func.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint


class PrintPic private constructor() {
    var canvas: Canvas? = null

    var paint: Paint? = null

    var bm: Bitmap? = null
    var width: Int = 0
    var length = 0.0f

    var bitbuf: ByteArray? = null

    fun getLength(): Int {
        return this.length.toInt() + 20
    }

    fun init(bitmap: Bitmap?) {
        if (null != bitmap) {
            initCanvas(bitmap.width)
        }
        if (null == paint) {
            initPaint()
        }
        if (null != bitmap) {
            drawImage(0f, 0f, bitmap)
        }
    }

    fun initCanvas(w: Int) {
        val h = 10 * w

        this.bm = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565)
        this.canvas = Canvas(this.bm)

        this.canvas!!.drawColor(-1)
        this.width = w
        this.bitbuf = ByteArray(this.width / 8)
    }

    fun initPaint() {
        this.paint = Paint()// 新建一个画笔

        this.paint!!.setAntiAlias(true)//

        this.paint!!.setColor(-16777216)

        this.paint!!.setStyle(Paint.Style.STROKE)
    }

    /**
     * draw bitmap
     */
    fun drawImage(x: Float, y: Float, btm: Bitmap?) {
        try {
            // Bitmap btm = BitmapFactory.decodeFile(path);
            this.canvas!!.drawBitmap(btm, x, y, null)
            if (this.length < y + btm!!.height)
                this.length = y + btm.height
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            btm?.recycle()
        }
    }

    /**
     * 使用光栅位图打印
     *
     * @return 字节
     */
    fun printDraw(): ByteArray {
        val nbm = Bitmap
                .createBitmap(this.bm!!, 0, 0, this.width, getLength())

        val imgbuf = ByteArray(this.width / 8 * getLength() + 8)

        var s = 0

        // 打印光栅位图的指令
        imgbuf[0] = 29// 十六进制0x1D
        imgbuf[1] = 118// 十六进制0x76
        imgbuf[2] = 48// 30
        imgbuf[3] = 0// 位图模式 0,1,2,3
        // 表示水平方向位图字节数（xL+xH × 256）
        imgbuf[4] = (this.width / 8).toByte()
        imgbuf[5] = 0
        // 表示垂直方向位图点数（ yL+ yH × 256）
        imgbuf[6] = (getLength() % 256).toByte()//
        imgbuf[7] = (getLength() / 256).toByte()

        s = 7
        for (i in 0 until getLength()) {// 循环位图的高度
            for (k in 0 until this.width / 8) {// 循环位图的宽度
                val c0 = nbm.getPixel(k * 8 + 0, i)// 返回指定坐标的颜色
                val p0: Int
                if (c0 == -1)
                // 判断颜色是不是白色
                    p0 = 0// 0,不打印该点
                else {
                    p0 = 1// 1,打印该点
                }
                val c1 = nbm.getPixel(k * 8 + 1, i)
                val p1: Int
                if (c1 == -1)
                    p1 = 0
                else {
                    p1 = 1
                }
                val c2 = nbm.getPixel(k * 8 + 2, i)
                val p2: Int
                if (c2 == -1)
                    p2 = 0
                else {
                    p2 = 1
                }
                val c3 = nbm.getPixel(k * 8 + 3, i)
                val p3: Int
                if (c3 == -1)
                    p3 = 0
                else {
                    p3 = 1
                }
                val c4 = nbm.getPixel(k * 8 + 4, i)
                val p4: Int
                if (c4 == -1)
                    p4 = 0
                else {
                    p4 = 1
                }
                val c5 = nbm.getPixel(k * 8 + 5, i)
                val p5: Int
                if (c5 == -1)
                    p5 = 0
                else {
                    p5 = 1
                }
                val c6 = nbm.getPixel(k * 8 + 6, i)
                val p6: Int
                if (c6 == -1)
                    p6 = 0
                else {
                    p6 = 1
                }
                val c7 = nbm.getPixel(k * 8 + 7, i)
                val p7: Int
                if (c7 == -1)
                    p7 = 0
                else {
                    p7 = 1
                }
                val value = (p0 * 128 + p1 * 64 + p2 * 32 + p3 * 16 + p4 * 8
                        + p5 * 4 + p6 * 2 + p7)
                this.bitbuf!![k] = value.toByte()
            }

            for (t in 0 until this.width / 8) {
                s++
                imgbuf[s] = this.bitbuf!![t]
            }
        }
        if (null != this.bm) {
            this.bm!!.recycle()
            this.bm = null
        }
        return imgbuf
    }

    companion object {

        val instance = PrintPic()
    }
}