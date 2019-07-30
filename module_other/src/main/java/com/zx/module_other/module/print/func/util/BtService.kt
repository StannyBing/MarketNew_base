//package com.xmwdkk.boothprint;
//
//import android.app.IntentService
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import com.zx.module_other.module.print.func.util.GPrinterCommand
//import com.zx.module_other.module.print.func.util.PrintPic
//import com.zx.module_other.module.print.func.util.PrintQueue
//import com.zx.module_other.module.print.func.util.PrintUtil
//import java.io.BufferedInputStream
//import java.io.UnsupportedEncodingException
//
//class BtService : IntentService {
//
//    constructor() : super("BtService") {}
//
//    /**
//     * Creates an IntentService.  Invoked by your subclass's constructor.
//     *
//     * @param name Used to name the worker thread, important only for debugging.
//     */
//    constructor(name: String) : super(name) {}
//
//    protected override fun onHandleIntent(intent: Intent?) {
//        if (intent == null || intent!!.getAction() == null) {
//            return
//        }
//        if (intent!!.getAction() == PrintUtil.ACTION_PRINT_TEST) {
//            //printTest()
//        } else if (intent!!.getAction() == PrintUtil.ACTION_PRINT_TEST_TWO) {
//            printTesttwo(3)
//        } else if (intent!!.getAction() == PrintUtil.ACTION_PRINT_BITMAP) {
//            printBitmapTest()
//        }
//
//    }
//
////    private fun printTest() {
////        val printOrderDataMaker = PrintOrderDataMaker(this, "", PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT)
////        val printData = printOrderDataMaker.getPrintData(PrinterWriter58mm.TYPE_58) as ArrayList<ByteArray>
////        PrintQueue.getQueue(getApplicationContext()).add(printData)
////
////    }
//
//    /**
//     * 打印几遍
//     * @param num
//     */
//    private fun printTesttwo(num: Int) {
//        try {
//            val bytes = ArrayList<ByteArray>()
//            for (i in 0 until num) {
//                val message = "蓝牙打印测试\n蓝牙打印测试\n蓝牙打印测试\n\n"
//                bytes.add(GPrinterCommand.reset)
//                bytes.add(message.toByteArray(charset("gbk")))
//                bytes.add(GPrinterCommand
//                        .print)
//                bytes.add(GPrinterCommand.print)
//                bytes.add(GPrinterCommand.print)
//            }
//            PrintQueue.getQueue(getApplicationContext()).add(bytes)
//        } catch (e: UnsupportedEncodingException) {
//            e.printStackTrace()
//        }
//
//    }
//
//    private fun print(byteArrayExtra: ByteArray?) {
//        if (null == byteArrayExtra || byteArrayExtra.size <= 0) {
//            return
//        }
//        PrintQueue.getQueue(getApplicationContext()).add(byteArrayExtra)
//    }
//
//    private fun printBitmapTest() {
//        val bis: BufferedInputStream
//        try {
//            bis = BufferedInputStream(getAssets().open(
//                    "icon_empty_bg.bmp"))
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return
//        }
//
//        var bitmap: Bitmap? = BitmapFactory.decodeStream(bis)
//        val printPic = PrintPic.instance
//        printPic.init(bitmap)
//        if (null != bitmap) {
//            if (bitmap!!.isRecycled()) {
//                bitmap = null
//            } else {
//                bitmap!!.recycle()
//                bitmap = null
//            }
//        }
//        val bytes = printPic.printDraw()
//        val printBytes = ArrayList<ByteArray>()
//        printBytes.add(GPrinterCommand.reset)
//        printBytes.add(GPrinterCommand.print)
//        printBytes.add(bytes)
//        printBytes.add(GPrinterCommand.print)
//        PrintQueue.getQueue(getApplicationContext()).add(bytes)
//    }
//    //
//    //    private void printPainting() {
//    //        byte[] bytes = PrintPic.getInstance().printDraw();
//    //        ArrayList<byte[]> printBytes = new ArrayList<byte[]>();
//    //        printBytes.add(GPrinterCommand.reset);
//    //        printBytes.add(GPrinterCommand.print);
//    //        printBytes.add(bytes);
//    //        Log.e("BtService", "image bytes size is :" + bytes.length);
//    //        printBytes.add(GPrinterCommand.print);
//    //        PrintQueue.getQueue(getApplicationContext()).add(bytes);
//    //    }
//}