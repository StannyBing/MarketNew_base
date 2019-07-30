package com.zx.module_other.module.print.ui

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.webkit.WebView
import android.widget.ArrayAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.module.print.func.util.PrintDataService
import com.zx.module_other.module.print.func.util.PrintDataUtil
import com.zx.module_other.module.print.mvp.contract.StartPrintContract
import com.zx.module_other.module.print.mvp.model.StartPrintModel
import com.zx.module_other.module.print.mvp.presenter.StartPrintPresenter
import kotlinx.android.synthetic.main.activity_start_print.*
import java.io.File
import java.nio.ByteBuffer


/**
 * Create By admin On 2017/7/11
 * 功能：
 */

@Route(path = RoutePath.ROUTE_OTHER__STRATPRINT)
class StartPrintActivity : BaseActivity<StartPrintPresenter, StartPrintModel>(), StartPrintContract.View {
    //  var bluetoothAdapter: BluetoothAdapter? = null
    var devices = arrayListOf<BluetoothDevice>()
    var printerNames = arrayListOf<String>()
    var sAdpter: ArrayAdapter<String>? = null
    var printDataService: PrintDataService? = null
    var webView: WebView? = null

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
        toolbar_view.withXApp(XAppOther.get("文件打印"))
        sAdpter = ArrayAdapter(this, com.zx.module_other.R.layout.item_start_print, R.id.text, printerNames)
        printDataService = PrintDataService(this)
        btn_print.background.setTint(ContextCompat.getColor(this, XAppOther.get("文件打印")!!.moduleColor))
        //     bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        devices = intent.getParcelableArrayListExtra("devices")
        s_printer.adapter = sAdpter
        setSprinner()
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
            if (printDataService!!.connect(devices.get(s_printer.selectedItemPosition))) {
                    val file = File(intent.getStringExtra("filePath"))
                    if (file.exists()){
                        printDataService!!.send(file.readBytes())
                    }
//                webView!!.getSettings().setDefaultTextEncodingName("utf-8")
//                webView!!.loadDataWithBaseURL("", intent.getStringExtra("data"), "text/html; charset=UTF-8", "UTF-8", null)
//                PrintDataUtil.webViewToPdf(webView!!, Environment.getExternalStorageDirectory().getPath()+"/webview.pdf", this@StartPrintActivity)
            }
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

}
