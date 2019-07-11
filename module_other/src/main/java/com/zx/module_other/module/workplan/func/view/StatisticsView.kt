package com.zx.module_other.module.workplan.func.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.Path


class StatisticsView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var hight: Int = 0
    private var widths: Int = 0
    private var paint: Paint = Paint()
    private var datas = arrayListOf<Int>()
    private var times = arrayListOf<String>()
    private var interval: Int = 5
    private var yItemValue: Float = 0f
    private var xItemValue: Float = 0f



    init {
        paint.color = Color.WHITE
//        linePaint.setShadowLayer(5f, 5f, 5f, Color.BLACK)
        paint.style = Paint.Style.STROKE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.hight = h
        this.widths = w
        var ally: Int = 0
        for (i in datas) {
            ally += i
        }
        yItemValue = ((hight - hight * 0.2) / ally).toFloat()
        xItemValue = (width / (datas.size + 1)).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (index in 0..datas.size - 1) {
            var w: Float = (index + 1) * xItemValue
            var h: Float = 0f
            paint.strokeWidth = 1f
            paint.alpha = 120
            while (h < hight) {
                canvas!!.drawLine(w, h, w, h + 10, paint)
                h = h + 10 + interval
            }
            val path = Path()
            if (index > 0) {
                var startx = (index * width / (datas.size + 1)).toFloat()
                var starty: Float = (hight - hight * 0.1 - datas[index - 1] * yItemValue).toFloat()
                var endy: Float = (hight - hight * 0.1 - datas[index] * yItemValue).toFloat()
                var controlx: Float = (w - startx) / 2 + startx
                var controly: Float = 0f;
                if (endy > starty) {
                    controly = (endy - starty) / 2 + starty-10
                } else {
                    controly = (starty - endy) / 2 + endy-10

                }
                path.moveTo(startx, starty)
                path.quadTo(controlx, controly, w, endy)
                paint.strokeWidth = 8f
                paint.alpha = 0
                canvas!!.drawPath(path, paint)
            }
        }
    }

    public fun setDatas(datas: ArrayList<Int>,times:ArrayList<String>) {
        this.datas.clear()
        this.datas.addAll(datas)
        invalidate()
    }
}