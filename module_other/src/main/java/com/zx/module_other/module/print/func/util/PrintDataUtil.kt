package com.zx.module_other.module.print.func.util

import android.webkit.WebView
import android.content.Context
import android.graphics.*
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.widget.Toast
import com.android.dx.stock.ProxyBuilder
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import android.os.*
import android.support.annotation.RequiresApi
import java.io.*
import android.opengl.ETC1.getHeight
import android.graphics.Bitmap
import android.support.annotation.NonNull
import android.view.View


class PrintDataUtil {
    companion object {
        val SUC = 0
        val DEF = -1
        val IMAGE_PATH = Environment.getExternalStorageDirectory().absolutePath + "/webview.jpg"


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun getWebViewImgData(webView: WebView): Int {
            webView.zoomBy(1 / webView.scale)
//            webView.setDrawingCacheEnabled(true)
//            webView.buildDrawingCache()
//            val bmp = webView.getDrawingCache()
            webView.measure(0, 0);
            val contentHeight = webView.getMeasuredHeight()
            val height = webView.getHeight()
            val totalScrollCount = contentHeight / height
            val surplusScrollHeight = contentHeight - totalScrollCount * height
            val cacheBitmaps = ArrayList<Bitmap>()
            webView.scrollY = 0
            val bitmap = getScreenshot(webView)
            cacheBitmaps.add(bitmap!!);
            for (i in 0..totalScrollCount) {
                if (i > 1) {
                    webView.scrollY = i * height
                    val bitmap = getScreenshot(webView)
                    cacheBitmaps.add(bitmap!!);
                }
            }

            if (surplusScrollHeight > 0) {
                webView.scrollY = contentHeight
                var bitmap = getScreenshot(webView)
                cacheBitmaps.add(bitmap!!);
            }
            var bmp = mergeBitmap(cacheBitmaps, contentHeight, surplusScrollHeight)
            return saveImageToGallery(bmp!!)
        }


        fun saveImageToGallery(bmp: Bitmap): Int {

            //获取文件
            val file = File(IMAGE_PATH)
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(file)
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos!!.flush()
                fos.close()
                return SUC
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    if (fos != null) {
                        fos!!.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return DEF
        }

        fun mergeBitmap(datas: List<Bitmap>, totalBitmapHeight: Int, remainScrollHeight: Int): Bitmap? {
            if (datas == null || datas.size <= 0) {
                return null
            }
            //图纸宽度(因为是截图,图片宽度大小都是一样的)
            val bitmapWidth = datas[0].width
            //图纸高度
            //1:创建图纸
            val bimap = Bitmap.createBitmap(bitmapWidth, totalBitmapHeight, Bitmap.Config.RGB_565)
            //2:创建画布,并绑定图纸
            val canvas = Canvas(bimap)
            //3:创建画笔
            val paint = Paint()
            val count = datas.size
            var i = 0
            while (i < count) {
                val data = datas[i]
                val left = 0f
                val top = (i * data.height).toFloat()
                var src: Rect? = null
                var des: RectF? = null
                /**
                 * Rect src = new Rect(); 代表图片矩形范围
                 * RectF des = new RectF(); 代表Canvas的矩形范围(显示位置)
                 */
                if (i == count - 1 && remainScrollHeight > 0) {
                    val srcRectTop = data.height - remainScrollHeight
                    src = Rect(0, srcRectTop, data.width, data.height)
                    des = RectF(left, top, data.width.toFloat(), top + remainScrollHeight)
                } else {
                    src = Rect(0, 0, data.width, data.height)
                    des = RectF(left, top, data.width.toFloat(), top + data.height)
                }
                canvas.drawBitmap(data, src, des, paint)
                i++
            }
            return bimap
        }

        fun getScreenshot(view: View): Bitmap? {
            if (view == null) {
                return null
            }
            //1:打开缓存开关
            view.setDrawingCacheEnabled(true)
            //2:获取缓存  此方法需要在主线程调用
            val drawingCache = view.getDrawingCache()
            //3:拷贝图片
            val newBitmap = Bitmap.createBitmap(drawingCache)
            //4:关闭缓存开关
            view.setDrawingCacheEnabled(false)
            return newBitmap
        }

        var descriptor: ParcelFileDescriptor? = null
        var ranges: Array<PageRange>? = null
        var printAdapter: PrintDocumentAdapter? = null
        var context: Context? = null
        fun webViewToPdf(webView: WebView, pdfFilePath: String, context: Context) {
            this.context = context
            //创建DexMaker缓存目录
            try {
                val pdfFile = File(pdfFilePath)
                if (pdfFile.exists()) {
                    pdfFile.delete()
                }
                pdfFile.createNewFile()
                descriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_WRITE)
                // 设置打印参数
                val isoA4 = PrintAttributes.MediaSize.ISO_A4
                val attributes = PrintAttributes.Builder()
                        .setMediaSize(isoA4)
                        .setResolution(PrintAttributes.Resolution("id", Context.PRINT_SERVICE, 240, 240))
                        .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                        .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                        .build()
                // 计算webview打印需要的页数
                val numberOfPages = webView.contentHeight * 240 / isoA4.heightMils
                ranges = arrayOf(PageRange(0, numberOfPages))
                // 创建pdf文件缓存目录
                // 获取需要打印的webview适配器
                printAdapter = webView.createPrintDocumentAdapter()
                // 开始打印
                printAdapter!!.onStart()
                printAdapter!!.onLayout(attributes, attributes, CancellationSignal(),
                        getLayoutResultCallback(object : InvocationHandler {
                            @Throws(Throwable::class)
                            override operator fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
                                if (method.getName().equals("onLayoutFinished")) {
                                    // 监听到内部调用了onLayoutFinished()方法,即打印成功
                                    onLayoutSuccess(getWriteResultCallback(object : InvocationHandler {
                                        @Throws(Throwable::class)
                                        override operator fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
                                            return null
                                        }
                                    }))
                                } else {
                                    // 监听到打印失败或者取消了打印
                                    Toast.makeText(context, "导出失败,请重试", Toast.LENGTH_SHORT).show()
                                }
                                return null
                            }
                        }), Bundle())
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        private fun onLayoutSuccess(callback: PrintDocumentAdapter.WriteResultCallback) {
            printAdapter!!.onWrite(ranges, descriptor, CancellationSignal(), callback)
        }

        @Throws(IOException::class)
        private fun onLayoutSuccess() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val callback = getWriteResultCallback(object : InvocationHandler {
                    override operator fun invoke(o: Any, method: Method, objects: Array<Any>): Any? {
                        if (method.getName().equals("onWriteFinished")) {
                            Toast.makeText(context, "导出成功", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "导出失败", Toast.LENGTH_SHORT).show()
                        }
                        return null
                    }
                })
                printAdapter!!.onWrite(ranges, descriptor, CancellationSignal(), callback)
            }
        }

        @Throws(IOException::class)
        fun getLayoutResultCallback(invocationHandler: InvocationHandler): PrintDocumentAdapter.LayoutResultCallback {
            return ProxyBuilder.forClass(PrintDocumentAdapter.LayoutResultCallback::class.java)
                    .handler(invocationHandler)
                    .build()
        }

        @Throws(IOException::class)
        fun getWriteResultCallback(invocationHandler: InvocationHandler): PrintDocumentAdapter.WriteResultCallback {
            return ProxyBuilder.forClass(PrintDocumentAdapter.WriteResultCallback::class.java)
                    .handler(invocationHandler)
                    .build()
        }
    }
}