package com.zx.module_other.module.print.ui

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tbruyelle.rxpermissions.RxPermissions
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.module.print.bean.PrintBean
import com.zx.module_other.module.print.func.adapter.BluetoothDeviceAdapter

import com.zx.module_other.module.print.mvp.contract.BluetoothContract
import com.zx.module_other.module.print.mvp.model.BluetoothModel
import com.zx.module_other.module.print.mvp.presenter.BluetoothPresenter
import com.zx.zxutils.util.ZXPermissionUtil
import kotlinx.android.synthetic.main.activity_bluetooth.*
import rx.functions.Action1


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class BluetoothActivity : BaseActivity<BluetoothPresenter, BluetoothModel>(), BluetoothContract.View {
    var bluetoothAdapter: BluetoothAdapter? = null
    val bluetoothDevicesDatas: ArrayList<PrintBean> = arrayListOf()
    val REQUEST_ENABLE_BT = 1
    val deviceAdapter = BluetoothDeviceAdapter(bluetoothDevicesDatas)
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
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, BluetoothActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_bluetooth
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        rv_bluetooth_search.apply {
            adapter = deviceAdapter
            layoutManager = LinearLayoutManager(this@BluetoothActivity)
        }
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        getBluetoothData()
        chechBluetooth()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        print_view.setOnClickListener {
            if (!print_switch.isChecked) {
                bluetoothAdapter!!.enable()
            } else {
                bluetoothAdapter!!.disable()
            }
        }

        deviceAdapter.setOnItemClickListener { adapter, view, position ->
            setConnect(bluetoothAdapter!!.getRemoteDevice(bluetoothDevicesDatas[position].address), position)
        }
    }

    fun chechBluetooth() {
        if (!bluetoothAdapter!!.isEnabled()) {
            closeBluetooth()
        } else {
            openBluetooth()
        }
    }

    fun openBluetooth() {
        print_switch.isChecked = true
        ll_bluetooth_list.visibility = View.VISIBLE
        bluetoothAdapter!!.enable()
        mHandler.postDelayed(runnable, 1)
    }

    fun closeBluetooth() {
        print_switch.isChecked = false
        ll_bluetooth_list.visibility = View.INVISIBLE
        if (bluetoothAdapter!!.isDiscovering()) {
            bluetoothAdapter!!.cancelDiscovery();
        }
        mHandler.removeCallbacks(runnable)
    }

    fun searchBluetooth() {
        if (bluetoothAdapter!!.isDiscovering()) {
            bluetoothAdapter!!.cancelDiscovery();
        }
        bluetoothAdapter!!.startDiscovery()
    }

    fun getBluetoothData() {
        mRxManager.on("Bluetooth", Action1<BluetoothDevice> {
            for (i in 0 until bluetoothDevicesDatas.size) {
                if (it.address == bluetoothDevicesDatas.get(i).address) {
                    bluetoothDevicesDatas.removeAt(i)
                }
            }
            if (it.bondState == BluetoothDevice.BOND_BONDED) {
                bluetoothDevicesDatas.add(0, PrintBean(it))
                deviceAdapter.setNewData(bluetoothDevicesDatas)
            } else {
                bluetoothDevicesDatas.add(PrintBean(it))
                deviceAdapter.setNewData(bluetoothDevicesDatas)
            }
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
    }

    private fun setConnect(device: BluetoothDevice, position: Int) {
        try {
            val createBondMethod = BluetoothDevice::class.java.getMethod("createBond")
            createBondMethod.invoke(device)
            bluetoothDevicesDatas.get(position).isConnect = true
            deviceAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(runnable)
    }
}
