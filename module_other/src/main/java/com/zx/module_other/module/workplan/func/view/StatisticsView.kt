package com.zx.module_other.module.workplan.func.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zx.module_other.R


class StatisticsView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var hight: Int = 0 //View 高度
    private var widths: Int = 0 //View 宽度
    private var paint: Paint = Paint()//画笔
    private var TextPaint: Paint = Paint()//文字画笔
    private var datas = arrayListOf<Int>()//数据list
    private var times = arrayListOf<String>()//文字list
    private var interval: Int = 5 //虚线空白长度
    private var yItemValue: Float = 0f//每个工作量占据的高度
    private var xItemValue: Float = 0f//数据点间隔宽度
    private val mBound: Rect = Rect() //文字矩形
    private var valuePoint = arrayListOf<PointF>()//数据点坐标
    private var SMOOTHNESS = 0.16f


    init {
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        TextPaint.isAntiAlias = true
        TextPaint.style = Paint.Style.FILL
        TextPaint.textSize = resources.getDimension(R.dimen.text_size_normal)
        TextPaint.color = Color.WHITE
        TextPaint.setShadowLayer(1f, 0f, 5f, Color.BLACK)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.hight = h
        this.widths = w
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        valuePoint.clear()
        canvas!!.drawText("近四个月完成数", (widths * 0.1).toFloat(), (hight * 0.1).toFloat(), TextPaint);
        for (index in 0..datas.size - 1) {
            var w: Float = (index + 1) * xItemValue
            var h: Float = 0f
            paint.strokeWidth = 1f
            paint.alpha = 120
            paint.color = Color.WHITE
            paint.style = Paint.Style.STROKE
            while (h < hight) {
                canvas!!.drawLine(w, h, w, h + 10, paint)
                h = h + 10 + interval
            }
            var startx: Float = 0f
            var starty: Float = 0f
            if (index == 0) {
                startx = 0f
                starty = (hight - hight * 0.1 - 1 * yItemValue).toFloat()
                valuePoint.add(PointF(startx, starty))
            } else {
                startx = (index * width / (datas.size + 1)).toFloat()
                starty = (hight - hight * 0.1 - datas[index - 1] * yItemValue).toFloat()
            }
            var endy: Float = (hight - hight * 0.2 - datas[index] * yItemValue).toFloat()
            valuePoint.add(PointF(w, endy))

            //文字绘制
            var text: String = times.get(index)
            TextPaint.getTextBounds(text, 0, text.length, mBound);
            canvas!!.drawText(text, w - mBound.width() / 2, (hight - hight * 0.1).toFloat(), TextPaint)

            val bashH = (hight - hight * 0.1 - datas[index] * yItemValue).toFloat()
            var numtext: String = datas.get(index).toString()
            TextPaint.getTextBounds(numtext, 0, numtext.length, mBound);
            canvas!!.drawText(numtext, w - mBound.width() / 2, (bashH - hight * 0.05).toFloat(), TextPaint)

            paint.color = Color.GRAY
            paint.style = Paint.Style.FILL
            //三角形绘制
            val tpath: Path = Path()
            tpath.moveTo(w, (bashH - hight * 0.04).toFloat())// 此点为多边形的起点
            tpath.lineTo((w - widths * 0.015).toFloat(), (bashH - hight * 0.02).toFloat())
            tpath.lineTo((w + widths * 0.015).toFloat(), (bashH - hight * 0.02).toFloat())
            tpath.close() // 使这些点构成封闭的多边形
            canvas.drawPath(tpath, paint)

        }
        //曲线绘制
        paint.strokeWidth = 8f
        paint.alpha = 255
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        canvas.drawPath(measurePath(), paint)
    }


    private fun measurePath(): Path {
        //保存曲线路径
        var mPath = Path()
        //保存辅助线路径
        //mAssistPath = Path()
        var prePreviousPointX = java.lang.Float.NaN
        var prePreviousPointY = java.lang.Float.NaN
        var previousPointX = java.lang.Float.NaN
        var previousPointY = java.lang.Float.NaN
        var currentPointX = java.lang.Float.NaN
        var currentPointY = java.lang.Float.NaN
        var nextPointX: Float
        var nextPointY: Float

        val lineSize = valuePoint.size
        for (valueIndex in 0 until lineSize) {
            if (java.lang.Float.isNaN(currentPointX)) {
                val point = valuePoint.get(valueIndex)
                currentPointX = point.x
                currentPointY = point.y
            }
            if (java.lang.Float.isNaN(previousPointX)) {
                //是否是第一个点
                if (valueIndex > 0) {
                    val point = valuePoint.get(valueIndex - 1)
                    previousPointX = point.x
                    previousPointY = point.y
                } else {
                    //是的话就用当前点表示上一个点
                    previousPointX = currentPointX
                    previousPointY = currentPointY
                }
            }

            if (java.lang.Float.isNaN(prePreviousPointX)) {
                //是否是前两个点
                if (valueIndex > 1) {
                    val point = valuePoint.get(valueIndex - 2)
                    prePreviousPointX = point.x
                    prePreviousPointY = point.y
                } else {
                    //是的话就用当前点表示上上个点
                    prePreviousPointX = previousPointX
                    prePreviousPointY = previousPointY
                }
            }

            // 判断是不是最后一个点了
            if (valueIndex < lineSize - 1) {
                val point = valuePoint.get(valueIndex + 1)
                nextPointX = point.x
                nextPointY = point.y
            } else {
                //是的话就用当前点表示下一个点
                nextPointX = currentPointX
                nextPointY = currentPointY
            }

            if (valueIndex == 0) {
                // 将Path移动到开始点
                mPath.moveTo(currentPointX, currentPointY)
            } else {
                // 求出控制点坐标
                val firstDiffX = currentPointX - prePreviousPointX
                val firstDiffY = currentPointY - prePreviousPointY
                val secondDiffX = nextPointX - previousPointX
                val secondDiffY = nextPointY - previousPointY
                val firstControlPointX = previousPointX + SMOOTHNESS * firstDiffX
                val firstControlPointY = previousPointY + SMOOTHNESS * firstDiffY
                val secondControlPointX = currentPointX - SMOOTHNESS * secondDiffX
                val secondControlPointY = currentPointY - SMOOTHNESS * secondDiffY
                //画出曲线
                mPath.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                        currentPointX, currentPointY)
                //将控制点保存到辅助路径上
            }

            // 更新值,
            prePreviousPointX = previousPointX
            prePreviousPointY = previousPointY
            previousPointX = currentPointX
            previousPointY = currentPointY
            currentPointX = nextPointX
            currentPointY = nextPointY
        }
        var mPathMeasure = PathMeasure(mPath, false)
        return mPath
    }

    fun setDatas(datas: ArrayList<Int>, times: ArrayList<String>) {
        this.datas.clear()
        this.datas.addAll(datas)
        this.times.addAll(times)
        var big: Int = 0
        for (i in datas) {
            if (i > big) {
                big = i
            }
        }
        yItemValue = ((hight - hight * 0.3) / big).toFloat()
        xItemValue = (widths / (datas.size + 1)).toFloat()
        invalidate()
    }
}