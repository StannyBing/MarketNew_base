package com.zx.module_other.module.print.func.receiver

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.frame.zxmvp.baserx.RxManager

class BluetoothReceive : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        // 把搜索的设置添加到集合中
        if (BluetoothDevice.ACTION_FOUND == action) {
            val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            RxManager().post("Bluetooth", device)
            //搜索完成
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {

        }else if(BluetoothAdapter.ACTION_STATE_CHANGED == action){
            val blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,0)
            when(blueState){
                BluetoothAdapter.STATE_TURNING_ON ->{
                }
                BluetoothAdapter.STATE_ON ->{
                    RxManager().post("bluetoothOpen",BluetoothAdapter.STATE_ON)
                }
                BluetoothAdapter.STATE_TURNING_OFF->{
                }
                BluetoothAdapter.STATE_OFF ->{
                    RxManager().post("bluetoothOpen",BluetoothAdapter.STATE_OFF)
                }
            }
        }
    }

}