package com.zx.module_other.module.print.ui

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.module.print.bean.PrintBean
import com.zx.module_other.module.print.func.adapter.BluetoothDeviceAdapter
import com.zx.module_other.module.print.mvp.contract.BluetoothContract
import com.zx.module_other.module.print.mvp.model.BluetoothModel
import com.zx.module_other.module.print.mvp.presenter.BluetoothPresenter
import kotlinx.android.synthetic.main.activity_bluetooth.*
import rx.functions.Action1


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
@Route(path = RoutePath.ROUTE_OTHER_BLUETOOTH)
class BluetoothActivity : BaseActivity<BluetoothPresenter, BluetoothModel>(), BluetoothContract.View {
    var bluetoothAdapter: BluetoothAdapter? = null
    val bluetoothDevicesDatas: ArrayList<PrintBean> = arrayListOf()
    val deviceAdapter = BluetoothDeviceAdapter(bluetoothDevicesDatas)
    val bluetoothConnectDatas: ArrayList<PrintBean> = ArrayList(5)
    val deviceConnectAdapter = BluetoothDeviceAdapter(bluetoothConnectDatas)
    val mHandler = Handler()


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
        rv_bluetooth_connected.apply {
            adapter = deviceConnectAdapter
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
            for (device in bluetoothAdapter!!.bondedDevices) {
                bluetoothConnectDatas.add(0, PrintBean(device))
            }
            if (bluetoothConnectDatas.size > 0) {
                rv_bluetooth_connected.visibility = View.VISIBLE
                tv_bluetooth_connected.visibility = View.VISIBLE
            }
        }
    }

    fun openBluetooth() {
        print_switch.isChecked = true
        ll_bluetooth_list.visibility = View.VISIBLE
        bluetoothAdapter!!.enable()
        searchBluetooth()
    }

    fun closeBluetooth() {
        print_switch.isChecked = false
        ll_bluetooth_list.visibility = View.INVISIBLE
        if (bluetoothAdapter!!.isDiscovering()) {
            bluetoothAdapter!!.cancelDiscovery();
        }
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
        for (device in bluetoothDevicesDatas) {
            if (it.address == device.address) {
                bluetoothDevicesDatas.remove(device)
                deviceAdapter.setNewData(bluetoothDevicesDatas)
            }
        }
        for (device in bluetoothConnectDatas) {
            if (it.address == device.address) {
                bluetoothConnectDatas.remove(device)
                deviceConnectAdapter.setNewData(bluetoothConnectDatas)
            }
        }
        if (it.bondState == BluetoothDevice.BOND_BONDED) {
            bluetoothConnectDatas.add(0, PrintBean(it))
            deviceConnectAdapter.setNewData(bluetoothConnectDatas)
        } else {
            bluetoothDevicesDatas.add(PrintBean(it))
            deviceAdapter.setNewData(bluetoothDevicesDatas)
        }
        if (bluetoothConnectDatas.size > 0) {
            rv_bluetooth_connected.visibility = View.VISIBLE
            tv_bluetooth_connected.visibility = View.VISIBLE
        } else {
            rv_bluetooth_connected.visibility = View.GONE
            tv_bluetooth_connected.visibility = View.GONE
        }
    }


    private fun setConnect(device: BluetoothDevice, position: Int) {
        try {
            val createBondMethod = BluetoothDevice::class.java.getMethod("createBond")
            createBondMethod.invoke(device)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disSearchBluetooth()
    }
}
