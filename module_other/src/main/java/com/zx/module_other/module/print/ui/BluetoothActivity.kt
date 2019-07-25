package com.zx.module_other.module.print.ui

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import com.frame.zxmvp.baserx.RxManager
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.module.print.bean.PrintBean

import com.zx.module_other.module.print.mvp.contract.BluetoothContract
import com.zx.module_other.module.print.mvp.model.BluetoothModel
import com.zx.module_other.module.print.mvp.presenter.BluetoothPresenter
import com.zx.zxutils.util.ZXToastUtil
import kotlinx.android.synthetic.main.activity_bluetooth.*
import rx.functions.Action1


/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class BluetoothActivity : BaseActivity<BluetoothPresenter, BluetoothModel>(), BluetoothContract.View {
    var bluetoothAdapter: BluetoothAdapter? = null
    val bluetoothDevicesDatas: ArrayList<PrintBean>? = null
    val REQUEST_ENABLE_BT = 1

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
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        getBluetoothData()
        chechBluetooth()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        print_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                bluetoothAdapter!!.enable()
//                    openBluetooth()
                bluetoothAdapter!!.startDiscovery()
            } else {
                bluetoothAdapter!!.disable()
            }
        }
    }

    fun chechBluetooth() {
        if (!bluetoothAdapter!!.isEnabled()) {
            print_switch.isChecked = false
        } else {
            print_switch.isChecked = true
        }
    }


    fun openBluetooth() {
        var intent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 20)
        startActivityForResult(intent, REQUEST_ENABLE_BT)
    }

    fun getBluetoothData() {
        RxManager().on("Bluetooth", Action1<BluetoothDevice> {
            for (i in 0 until bluetoothDevicesDatas!!.size) {
                if (it.address == bluetoothDevicesDatas.get(i).address) {
                    bluetoothDevicesDatas.removeAt(i)
                }
            }
            if (it.bondState == BluetoothDevice.BOND_BONDED && it.bluetoothClass.deviceClass == PrintBean.PRINT_TYPE) {
                bluetoothDevicesDatas.add(PrintBean(it))
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_ENABLE_BT) {
            print_switch.isChecked = true
        } else if (resultCode == RESULT_CANCELED && requestCode == REQUEST_ENABLE_BT) {
            print_switch.isChecked = false
        }
    }
}
