package com.zx.module_other.module.print.func.util

import android.graphics.Bitmap
import android.graphics.Picture
import android.webkit.WebView
import android.R.array
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.widget.Toast
import com.android.dx.stock.ProxyBuilder
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.nio.ByteBuffer


class PrintDataUtil {
    companion object {
        fun getWebViewImgData(webView: WebView): String {
            val snapShot: Picture = webView.capturePicture()
            val bmp: Bitmap = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888)
            val bytes = bmp.byteCount

            val buf = ByteBuffer.allocate(bytes)
            bmp.copyPixelsToBuffer(buf)

            return String(buf.array())
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