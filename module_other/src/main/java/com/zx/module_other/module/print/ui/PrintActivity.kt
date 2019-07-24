package com.zx.module_other.module.print.ui

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.module.print.bean.PrintBean
import com.zx.module_other.module.print.bean.PrintBean.Companion.PRINT_TYPE
import com.zx.module_other.module.print.mvp.contract.PrintContract
import com.zx.module_other.module.print.mvp.model.PrintModel
import com.zx.module_other.module.print.mvp.presenter.PrintPresenter
import kotlinx.android.synthetic.main.activity_print.*


/**
 * Create By admin On 2017/7/11
 * 功能：文件打印
 */
@Route(path = RoutePath.ROUTE_OTHER_PRINT)
class PrintActivity : BaseActivity<PrintPresenter, PrintModel>(), PrintContract.View {
    var bluetoothAdapter: BluetoothAdapter? = null
    val bluetoothDevicesDatas: ArrayList<PrintBean>? = null
    val REQUEST_ENABLE_BT = 1
    val mReceiver = MyReceiver()

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean) {
            val intent = Intent(activity, PrintActivity::class.java)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_print
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(mReceiver, filter)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //print_switch.
    }

    fun chechBluetooth() {
        if (bluetoothAdapter!!.isEnabled()) {
            openBluetooth()
        } else {
            print_switch.setChecked(true);
        }
    }

    fun openBluetooth() {
        var intent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 20)
        startActivityForResult(intent, REQUEST_ENABLE_BT)
    }

    fun searchDevices() {
        bluetoothAdapter!!.startDiscovery()
    }

    inner class MyReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            // 把搜索的设置添加到集合中
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                //已经匹配的设备
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    addBluetoothDevice(device)

                    //没有匹配的设备
                } else {
                    addBluetoothDevice(device)
                }
                //搜索完成
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {

            }
        }

        private fun addBluetoothDevice(device: BluetoothDevice) {
            for (i in 0 until bluetoothDevicesDatas!!.size) {
                if (device.address == bluetoothDevicesDatas.get(i).address) {
                    bluetoothDevicesDatas.removeAt(i)
                }
            }
            if (device.bondState == BluetoothDevice.BOND_BONDED && device.bluetoothClass.deviceClass == PRINT_TYPE) {
                bluetoothDevicesDatas.add(PrintBean(device))
            }
        }
    }



}
