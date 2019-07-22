package com.zx.module_supervise.module.supervise.func.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.support.v4.view.InputDeviceCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zx.zxutils.util.ZXBitmapUtil;

import java.util.ArrayList;

/**
 * 手势绘制view
 */
public class SignatureView extends View {
    private Bitmap m_Bitmap;
    private Canvas m_Canvas;
    private Point m_CropBotRight;
    private Point m_CropTopLeft;
    private float m_CurrentX;
    private float m_CurrentY;
    private boolean m_Empty;
    private float m_LastWidth;
    private Paint m_PenPaint;
    private int m_PointIndex;
    private ArrayList<Point> m_Points;
    public static final int BezierPointCount = 4;

    @SuppressLint({"Recycle"})
    public SignatureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.m_Points = new ArrayList();
        this.m_PointIndex = 0;
        this.m_LastWidth = 6.5f;
        setFocusable(true);
        this.m_PenPaint = new Paint();
        this.m_PenPaint.setAntiAlias(true);
        this.m_PenPaint.setColor(Color.BLACK);
        this.m_PenPaint.setStrokeWidth(5.0f);
        this.m_PenPaint.setStrokeJoin(Paint.Join.ROUND);
        this.m_PenPaint.setStrokeCap(Paint.Cap.ROUND);
        this.m_CurrentY = Float.NaN;
        this.m_CurrentX = Float.NaN;
    }

    public SignatureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignatureView(Context context) {
        this(context, null);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        this.m_Canvas = new Canvas(newBitmap);
        clear();
        if (this.m_Bitmap != null) {
            this.m_Canvas.drawBitmap(this.m_Bitmap, null, new Rect(0, 0, this.m_Canvas.getWidth(), this.m_Canvas.getHeight()), null);
            this.m_Empty = false;
        }
        this.m_Bitmap = newBitmap;
    }

    @SuppressLint({"DrawAllocation"})
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width > 0) {
            //高版本手机不调用问题
            this.m_CropTopLeft = new Point((float) width, (float) height);
            this.m_CropBotRight = new Point(0.0f, 0.0f);
        }
        setMeasuredDimension(width, height);
    }

    public Bitmap buildBitmap() {
        int mWidth = m_Bitmap.getWidth();
        int mHeight = m_Bitmap.getHeight();
        int buildWidth = 600;
        int buildHeight = 600;
        int minSize = mWidth > mHeight ? mHeight : mWidth;
        Matrix matrix = new Matrix();
        Bitmap bitmap = Bitmap.createBitmap(m_Bitmap, 0, 0, minSize, minSize);

        Bitmap bgBitmap = Bitmap.createBitmap(m_Bitmap.getWidth(), m_Bitmap.getHeight(), Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Canvas canvas = new Canvas(bgBitmap);
        canvas.drawRect(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight(), paint);
        canvas.drawBitmap(bgBitmap, 0, 0, paint);

        Bitmap outBitmap = ZXBitmapUtil.combineBitmap(bgBitmap, bitmap);
        bgBitmap.recycle();
        bitmap.recycle();

        float scaleWidht = (float)buildWidth / (float)minSize;
        float scaleHeight = (float)buildHeight / (float)minSize;
        matrix.postScale(scaleWidht, scaleHeight);
        outBitmap = Bitmap.createBitmap(outBitmap, 0, 0, minSize, minSize, matrix, true);
        return outBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.m_Bitmap != null) {
            canvas.drawBitmap(this.m_Bitmap, 0.0f, 0.0f, null);
        }
    }

    private void addPoint(Point newPoint) {
        if (newPoint != null && this.m_CropTopLeft != null && this.m_CropBotRight != null) {
            if ((isEmpty() || newPoint.getX() < this.m_CropTopLeft.getX()) && newPoint.getX() >= 0.0f) {
                this.m_CropTopLeft.setX(newPoint.getX());
            }
            if ((isEmpty() || newPoint.getY() < this.m_CropTopLeft.getY()) && newPoint.getY() >= 0.0f) {
                this.m_CropTopLeft.setY(newPoint.getY());
            }
            if ((isEmpty() || newPoint.getX() > this.m_CropBotRight.getX()) && newPoint.getX() <= ((float) this.m_Canvas.getWidth())) {
                this.m_CropBotRight.setX(newPoint.getX());
            }
            if ((isEmpty() || newPoint.getY() > this.m_CropBotRight.getY()) && newPoint.getY() <= ((float) this.m_Canvas.getHeight())) {
                this.m_CropBotRight.setY(newPoint.getY());
            }
            this.m_Points.add(newPoint);
            drawPoints();
        }
    }

    private void drawPoints() {
        if (this.m_Points.size() >= BezierPointCount && this.m_PointIndex + BezierPointCount <= this.m_Points.size()) {
            Point startPoint = this.m_Points.get(this.m_PointIndex);
            Point endPoint = this.m_Points.get(this.m_PointIndex + 3);
            Bezier bezier = new Bezier(startPoint, this.m_Points.get(this.m_PointIndex + 1), this.m_Points.get(this.m_PointIndex + 2), endPoint);
            float newWidth = strokeWidth(8.0f / endPoint.velocityFrom(startPoint));
            addBezier(bezier, this.m_LastWidth, newWidth);
            invalidate();
            this.m_LastWidth = newWidth;
            this.m_PointIndex += 3;
            this.m_Empty = false;
        }
    }

    private void addBezier(Bezier curve, float startWidth, float endWidth) {
        if (this.m_Bitmap == null) {
            this.m_Bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
            this.m_Canvas = new Canvas(this.m_Bitmap);
        }
        curve.draw(this.m_Canvas, this.m_PenPaint, startWidth, endWidth);
    }

    private float strokeWidth(float velocity) {
        if (velocity > 11.0f) {
            return 10.0f;
        }
        if (velocity < 5.0f) {
            return 6.0f;
        }
        return velocity;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & InputDeviceCompat.SOURCE_CLASS_MASK;
        if (action == MotionEvent.ACTION_DOWN) {
            this.m_CurrentX = event.getX();
            this.m_CurrentY = event.getY();
            addPoint(new Point(this.m_CurrentX, this.m_CurrentY, event.getEventTime()));
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_UP) {
            for (int i = 0; i < event.getHistorySize(); i++) {
                addPoint(new Point(event.getHistoricalX(i), event.getHistoricalY(i), event.getHistoricalEventTime(i)));
            }
            addPoint(new Point(event.getX(), event.getY(), event.getEventTime()));
        }
        if (action == MotionEvent.ACTION_UP) {
            this.m_Canvas.drawPoint(event.getX(), event.getY(), this.m_PenPaint);
            invalidate();
            this.m_CurrentY = Float.NaN;
            this.m_CurrentX = Float.NaN;
            this.m_Points.clear();
            this.m_PointIndex = 0;
            getParent().requestDisallowInterceptTouchEvent(false);
        } else if (action == MotionEvent.ACTION_CANCEL) {
            this.m_CurrentY = Float.NaN;
            this.m_CurrentX = Float.NaN;
            this.m_Points.clear();
            this.m_PointIndex = 0;
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        if (this.m_Points.size() >= BezierPointCount && this.m_PointIndex + BezierPointCount <= this.m_Points.size()) {
            while (this.m_PointIndex + 1 <= this.m_Points.size()) {
                drawPoints();
            }
        }
        return true;
    }

    public void clear() {
        if (this.m_Canvas != null) {
            this.m_Canvas.drawColor(0, Mode.CLEAR);
            this.m_Empty = true;
            invalidate();
        }
    }

    public boolean isEmpty() {
        return this.m_Empty;
    }

    private class Bezier {
        private Point controlPointOne;
        private Point controlPointTwo;
        private int drawSteps;
        private Point endPoint;
        private Point startPoint;

        public Bezier(Point startPoint, Point controlPointOne, Point controlPointTwo, Point endPoint) {
            this.startPoint = startPoint;
            this.controlPointOne = controlPointOne;
            this.controlPointTwo = controlPointTwo;
            this.endPoint = endPoint;
            this.drawSteps = (int) ((startPoint.distanceTo(controlPointOne) + controlPointOne.distanceTo(controlPointTwo)) + controlPointTwo.distanceTo(endPoint));
        }

        public void draw(Canvas canvas, Paint paint, float startWidth, float endWidth) {
            float widthDelta = endWidth - startWidth;
            for (int i = 0; i < this.drawSteps; i++) {
                float t = ((float) i) / ((float) this.drawSteps);
                float tt = t * t;
                float ttt = tt * t;
                float u = 1.0f - t;
                float uu = u * u;
                float uuu = uu * u;
                float x = (((uuu * this.startPoint.getX()) + (((3.0f * uu) * t) * this.controlPointOne.getX())) + (((3.0f * u) * tt) * this.controlPointTwo.getX())) + (this.endPoint.getX() * ttt);
                float y = (((uuu * this.startPoint.getY()) + (((3.0f * uu) * t) * this.controlPointOne.getY())) + (((3.0f * u) * tt) * this.controlPointTwo.getY())) + (this.endPoint.getY() * ttt);
                paint.setStrokeWidth((ttt * widthDelta) + startWidth);
                canvas.drawPoint(x, y, paint);
            }
        }
    }

    private class Point {
        private long time;
        private float x;
        private float y;

        public Point(float x, float y, long time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        /**
         * 当前点相对于起始点的 速度=距离/时间
         * @param start
         * @return
         */
        public float velocityFrom(Point start) {
            return distanceTo(start) / ((float) (this.time == start.time ? 1 : this.time - start.time));
        }

        /**
         * 当前点到start点的距离
         * @param start
         * @return
         */
        protected float distanceTo(Point start) {
            float dX = this.x - start.getX();
            float dY = this.y - start.getY();
            return (float) Math.sqrt((double) ((dX * dX) + (dY * dY)));
        }

        public float getX() {
            return this.x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return this.y;
        }

        public void setY(float y) {
            this.y = y;
        }


        public String toString() {
            return "(" + this.x + ", " + this.y + ") @ " + this.time;
        }
    }
}
