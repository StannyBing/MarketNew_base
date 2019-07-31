package com.zx.module_other.module.print.func.util

import android.graphics.Bitmap
import android.webkit.WebView
import android.content.Context
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.widget.Toast
import com.android.dx.stock.ProxyBuilder
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import android.os.*
import java.io.*


class PrintDataUtil {
    companion object {
        val SUC = 0
        val DEF = -1
        val IMAGE_PATH = Environment.getExternalStorageDirectory().absolutePath + "/webview.jpg"



        fun getWebViewImgData(webView: WebView): Int {
            webView.setDrawingCacheEnabled(true)
            webView.buildDrawingCache()
            val bmp = webView.getDrawingCache()
            return saveImageToGallery(bmp)
            webView.destroyDrawingCache()
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