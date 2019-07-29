package com.zx.module_other.module.print.ui

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.module.print.mvp.contract.StartPrintContract
import com.zx.module_other.module.print.mvp.model.StartPrintModel
import com.zx.module_other.module.print.mvp.presenter.StartPrintPresenter
import kotlinx.android.synthetic.main.activity_start_print.*
import rx.functions.Action1


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class StartPrintActivity : BaseActivity<StartPrintPresenter, StartPrintModel>(), StartPrintContract.View {

    var bluetoothAdapter: BluetoothAdapter? = null
    var devices = arrayListOf<BluetoothDevice>()
    var printerNames = arrayListOf<String>()
    val sAdpter = ArrayAdapter(this, com.zx.module_other.R.layout.item_start_print, R.id.text, printerNames)


    val mHandler = Handler()
    var runnable: Runnable = object : Runnable {
        override fun run() {
            searchBluetooth()
            mHandler.postDelayed(this, 5000)
        }
    }

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, docName: String, devices: ArrayList<BluetoothDevice>) {
            val intent = Intent(activity, StartPrintActivity::class.java)
            intent.putExtra("docName", docName)
            intent.putExtra("devices", devices)
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
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        tv_print_name.setText(intent.getStringExtra("docName"))
        toolbar_view.withXApp(XAppOther.get("文件打印"))
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        devices = intent.getParcelableArrayListExtra("devices")
        s_printer.adapter = sAdpter
        setSprinner()
        if (bluetoothAdapter!!.isEnabled()) {
            openBluetooth()
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }


    fun setSprinner() {
        printerNames.clear()
        for (device in devices) {
            printerNames.add(device.name)
        }
        sAdpter.notifyDataSetChanged()
    }

    fun searchBluetooth() {
        disSearchBluetooth()
        bluetoothAdapter!!.startDiscovery()
    }

    fun disSearchBluetooth() {
        if (bluetoothAdapter!!.isDiscovering()) {
            bluetoothAdapter!!.cancelDiscovery();
        }
    }

    fun openBluetooth() {
        bluetoothAdapter!!.enable()
        mHandler.postDelayed(runnable, 1)
    }

    fun closeBluetooth() {
        if (bluetoothAdapter!!.isDiscovering()) {
            bluetoothAdapter!!.cancelDiscovery();
        }
        mHandler.removeCallbacks(runnable)
    }

    fun getBluetoothData() {
        mRxManager.on("Bluetooth", Action1<BluetoothDevice> {
            handleData(it)
        })
        mRxManager.on("bluetoothOpen", Action1<Int> {
            when (it) {
                BluetoothAdapter.STATE_ON -> {
                    openBluetooth()
                }
                BluetoothAdapter.STATE_OFF -> {
                    closeBluetooth()
                }
            }
        })

        mRxManager.on("bluetoothState", Action1<BluetoothDevice> {
            handleData(it)
        })
    }

    fun handleData(it: BluetoothDevice) {
        for (i in 0 until devices.size) {
            if (it.address == devices.get(i).address) {
                devices.removeAt(i)
            }
        }

        if (it.bondState == BluetoothDevice.BOND_BONDED) {
            devices.add(it)
        }
        setSprinner()
    }

}
