package com.zx.module_other.module.print.func.receiver

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.frame.zxmvp.baserx.RxManager

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

        } else if (BluetoothAdapter.ACTION_STATE_CHANGED == action) {
            val blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)
            when (blueState) {
                BluetoothAdapter.STATE_TURNING_ON -> {
                }
                BluetoothAdapter.STATE_ON -> {
                    RxManager().post("bluetoothOpen", BluetoothAdapter.STATE_ON)
                }
                BluetoothAdapter.STATE_TURNING_OFF -> {
                }
                BluetoothAdapter.STATE_OFF -> {
                    RxManager().post("bluetoothOpen", BluetoothAdapter.STATE_OFF)
                }
            }
        } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED == action) {
            val state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1)
            val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            when (state) {
                BluetoothDevice.BOND_NONE -> RxManager().post("bluetoothState", device)
                BluetoothDevice.BOND_BONDING -> RxManager().post("bluetoothState", device)
                BluetoothDevice.BOND_BONDED -> RxManager().post("bluetoothState", device)
            }
        }

    }

}