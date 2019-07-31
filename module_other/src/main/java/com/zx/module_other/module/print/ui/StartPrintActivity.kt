package com.zx.module_other.module.print.ui

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.webkit.WebView
import android.widget.ArrayAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.lvrenyang.io.BTPrinting
import com.lvrenyang.io.Canvas
import com.lvrenyang.io.IOCallBack
import com.lvrenyang.io.Pos
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.module.print.func.util.GPrinterCommand
import com.zx.module_other.module.print.func.util.PrintDataService
import com.zx.module_other.module.print.func.util.PrintPic
import com.zx.module_other.module.print.mvp.contract.StartPrintContract
import com.zx.module_other.module.print.mvp.model.StartPrintModel
import com.zx.module_other.module.print.mvp.presenter.StartPrintPresenter
import com.zx.zxutils.util.ZXToastUtil
import kotlinx.android.synthetic.main.activity_start_print.*
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.util.concurrent.Executors


/**
 * Create By admin On 2017/7/11
 * 功能：
 */

@Route(path = RoutePath.ROUTE_OTHER__STRATPRINT)
class StartPrintActivity : BaseActivity<StartPrintPresenter, StartPrintModel>(), StartPrintContract.View, IOCallBack {

    //  var bluetoothAdapter: BluetoothAdapter? = null
    var devices = arrayListOf<BluetoothDevice>()
    var printerNames = arrayListOf<String>()
    var sAdpter: ArrayAdapter<String>? = null
    var printDataService: PrintDataService? = null
    var webView: WebView? = null
    var es = Executors.newScheduledThreadPool(30)
    var mCanvas = Canvas()
    var pos = Pos()
    var mBt = BTPrinting()

//    val mHandler = Handler()
//    var runnable: Runnable = object : Runnable {
//        override fun run() {
//            searchBluetooth()
//            mHandler.postDelayed(this, 5000)
//        }
//    }

    companion object {
        /**
         * 启动器
         */

        fun startAction(activity: Activity, isFinish: Boolean, docName: String, devices: ArrayList<BluetoothDevice>, filePath: String) {
            val intent = Intent(activity, StartPrintActivity::class.java)
            intent.putExtra("docName", docName)
            intent.putExtra("devices", devices)
            intent.putExtra("filePath", filePath)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_start_print
    }

    /**
     * 初始化
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        tv_print_name.setText(intent.getStringExtra("docName"))
        toolbar_view.withXApp(XAppOther.PRINT)
        sAdpter = ArrayAdapter(this, com.zx.module_other.R.layout.item_start_print, R.id.text, printerNames)
        printDataService = PrintDataService(this)
        btn_print.background.setTint(ContextCompat.getColor(this, XAppOther.PRINT.moduleColor))
        //     bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        devices = intent.getParcelableArrayListExtra("devices")
        s_printer.adapter = sAdpter
        setSprinner()
        mCanvas.Set(mBt);
        pos.Set(mBt)
        mBt.SetCallBack(this);
        webView = WebView(this)
//        if (bluetoothAdapter!!.isEnabled()) {
//            openBluetooth()
//        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        btn_print.setOnClickListener {
             es.submit(TaskOpen(mBt,devices.get(s_printer.selectedItemPosition).address,this));

//            if (printDataService!!.connect(devices.get(s_printer.selectedItemPosition))) {
//                val photoPrinter = PrintHelper(this)
//                photoPrinter.scaleMode = PrintHelper.SCALE_MODE_FIT
//                val imageBitmap = BitmapFactory.decodeFile(intent.getStringExtra("filePath"))
//                photoPrinter.printBitmap("droids.jpg - test print", imageBitmap)

//                val fs = FileInputStream(intent.getStringExtra("filePath"))
//                val bitmap = BitmapFactory.decodeStream(fs)
//                pos.POS_PrintPicture(bitmap, 576, 0, 0)
//                printBitmapTest(bitmap)

//                val canvas:Canvas = Canvas()
//                canvas.IO.

//                val file = File(intent.getStringExtra("filePath"))
//                if (file.exists()) {
////                        printDataService!!.send(file.readText().toByteArray(charset("gbk")))
//                    printDataService!!.send(file.readText(charset("gbk")).toByteArray())
//                }
//                webView!!.getSettings().setDefaultTextEncodingName("utf-8")
//                webView!!.loadDataWithBaseURL("", intent.getStringExtra("data"), "text/html; charset=UTF-8", "UTF-8", null)
//                PrintDataUtil.webViewToPdf(webView!!, Environment.getExternalStorageDirectory().getPath()+"/webview.pdf", this@StartPrintActivity)
//            }
        }
    }


    fun setSprinner() {
        printerNames.clear()
        for (device in devices) {
            printerNames.add(device.name)
        }
        sAdpter!!.notifyDataSetChanged()
    }

//    fun searchBluetooth() {
//        disSearchBluetooth()
//        bluetoothAdapter!!.startDiscovery()
//    }
//
//    fun disSearchBluetooth() {
//        if (bluetoothAdapter!!.isDiscovering()) {
//            bluetoothAdapter!!.cancelDiscovery();
//        }
//    }

//    fun openBluetooth() {
//        bluetoothAdapter!!.enable()
//        mHandler.postDelayed(runnable, 1)
//    }
//
//    fun closeBluetooth() {
//        if (bluetoothAdapter!!.isDiscovering()) {
//            bluetoothAdapter!!.cancelDiscovery();
//        }
//        mHandler.removeCallbacks(runnable)
//    }
//
//    fun getBluetoothData() {
//        mRxManager.on("Bluetooth", Action1<BluetoothDevice> {
//            handleData(it)
//        })
//        mRxManager.on("bluetoothOpen", Action1<Int> {
//            when (it) {
//                BluetoothAdapter.STATE_ON -> {
//                    openBluetooth()
//                }
//                BluetoothAdapter.STATE_OFF -> {
//                    closeBluetooth()
//                }
//            }
//        })
//
//        mRxManager.on("bluetoothState", Action1<BluetoothDevice> {
//            handleData(it)
//        })
//    }
//
//    fun handleData(it: BluetoothDevice) {
//        for (i in 0 until devices.size) {
//            if (it.address == devices.get(i).address) {
//                devices.removeAt(i)
//            }
//        }
//
//        if (it.bondState == BluetoothDevice.BOND_BONDED) {
//            devices.add(it)
//        }
//        setSprinner()
//    }

    fun printBitmapTest(bitmap: Bitmap? = null) {
        val bis: BufferedInputStream
        try {
            bis = BufferedInputStream(assets.open(
                    "icon_empty_bg.bmp"))
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        val printPic = PrintPic.instance
        printPic.init(bitmap)
        val bytes = printPic.printDraw()
        val printBytes = ArrayList<ByteArray>()
        printBytes.add(GPrinterCommand.reset)
        printBytes.add(GPrinterCommand.print)
        printBytes.add(bytes)
        printBytes.add(GPrinterCommand.print)
        printDataService!!.send(bytes)
    }

    inner class TaskOpen(bt: BTPrinting, address: String, context: Context) : Runnable {
        internal var bt: BTPrinting? = null
        internal var address: String? = null
        internal var context: Context? = null

        init {
            this.bt = bt
            this.address = address
            this.context = context
        }

        override fun run() {
            // TODO Auto-generated method stub
            bt!!.Open(address, context)
            val fs = FileInputStream(intent.getStringExtra("filePath"))
            val bitmap = BitmapFactory.decodeStream(fs)
       //     pos.POS_PrintPicture(bitmap, 576, 0, 0)
            var suc = false
     //       suc = pos.GetIO().IsOpened()
            mCanvas.CanvasBegin(576, 600);
            mCanvas.SetPrintDirection(0);

            mCanvas.DrawBox(0f, 0f, 575f, 599f);

            mCanvas.DrawBitmap(bitmap, 1f, 10f, 0f);
            mCanvas.CanvasEnd();
            mCanvas.CanvasPrint(1, 1);
            suc = mCanvas.IO.IsOpened()
            runOnUiThread {
                if (suc) {
                    ZXToastUtil.showToast("成功")
                } else {
                    ZXToastUtil.showToast("失败")
                }

            }
        }
    }

//    inner class TaskPrint(canvas: Canvas) : Runnable {
//        internal var canvas: Canvas? = null
//
//        init {
//            this.canvas = canvas
//        }
//
//        override fun run() {
//            // TODO Auto-generated method stub
//            //final boolean bPrintResult = Prints.PrintTicket(getApplicationContext(), canvas, AppStart.nPrintWidth, AppStart.nPrintHeight);
//            //final boolean bPrintResult = Prints.PrintTicketForMemoryTest(getApplicationContext(), canvas, AppStart.nPrintWidth, AppStart.nPrintHeight, 6);
//            val bPrintResult = Prints.PrintTicketAutoNewLine(applicationContext, canvas, AppStart.nPrintWidth, AppStart.nPrintHeight)
//            val bIsOpened = canvas!!.GetIO().IsOpened()
//
//            mActivity.runOnUiThread(Runnable {
//                // TODO Auto-generated method stub
//                Toast.makeText(
//                        mActivity.getApplicationContext(),
//                        if (bPrintResult)
//                            resources.getString(
//                                    R.string.printsuccess)
//                        else
//                            resources
//                                    .getString(R.string.printfailed),
//                        Toast.LENGTH_SHORT).show()
//                mActivity.btnPrint.setEnabled(bIsOpened)
//            })
//
//        }
//    }

    override fun OnClose() {
    }

    override fun OnOpenFailed() {
        ZXToastUtil.showToast("连接失败")

    }

    override fun OnOpen() {
        ZXToastUtil.showToast("连接成功")
    }
}
