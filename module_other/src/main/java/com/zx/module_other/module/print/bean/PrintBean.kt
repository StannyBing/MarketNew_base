package com.zx.module_other.module.print.bean

import android.bluetooth.BluetoothDevice
import android.text.TextUtils
import android.view.View
import com.zx.module_other.R

/**
 * 类说明:蓝牙设备的实体类
 */
class PrintBean
/**
 * @param devicee 蓝牙设备对象
 */
(devicee: BluetoothDevice) {
    //蓝牙-名称
    var name: String
    //蓝牙-地址
    var address: String
    //蓝牙-设备类型
    var type: Int = 0
    //蓝牙-是否已经匹配
    var state: Int = 0 // 0 :未配对 1：配对：2 正在配对


    /**
     * 260-电脑
     * 1664-打印机
     * 524-智能手机
     *
     * @return
     */


    init {
        this.name = if (TextUtils.isEmpty(devicee.name)) "未知" else devicee.name
        this.address = devicee.address
        if (devicee.bondState == BluetoothDevice.BOND_BONDED) {
            this.state = 1
        } else if (devicee.bondState == BluetoothDevice.BOND_BONDING) {
            this.state = 2
        }
        this.type = devicee.bluetoothClass.deviceClass
    }


    companion object {
        val PRINT_TYPE = 1664
    }
}