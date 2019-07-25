package com.zx.module_other.module.print.func.Receiver

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.frame.zxmvp.baserx.RxManager
import com.zx.module_other.module.print.bean.PrintBean

class BluetoothReceive : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        // 把搜索的设置添加到集合中
        if (BluetoothDevice.ACTION_FOUND == action) {
            val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            //已经匹配的设备
            if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                //没有匹配的设备
                RxManager().post("Bluetooth", device)
            } else {
                RxManager().post("Bluetooth", device)
            }
            //搜索完成
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {

        }
    }

}