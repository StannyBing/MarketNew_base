package com.zx.module_other.module.print.func.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.zx.zxutils.util.ZXToastUtil
import rx.functions.Action1
import java.io.IOException
import java.io.OutputStream
import java.nio.ByteBuffer
import java.util.*


class PrintDataService(vcontext: Context) {
    var context: Context? = null
    var bluetoothAdapter = BluetoothAdapter
            .getDefaultAdapter()
    var isConnection = false
    internal val items = arrayOf("复位打印机", "标准ASCII字体", "压缩ASCII字体", "字体不放大", "宽高加倍", "取消加粗模式", "选择加粗模式", "取消倒置打印", "选择倒置打印", "取消黑白反显", "选择黑白反显", "取消顺时针旋转90°", "选择顺时针旋转90°")
    internal val byteCommands = arrayOf(byteArrayOf(0x1b, 0x40), // 复位打印机
            byteArrayOf(0x1b, 0x4d, 0x00), // 标准ASCII字体
            byteArrayOf(0x1b, 0x4d, 0x01), // 压缩ASCII字体
            byteArrayOf(0x1d, 0x21, 0x00), // 字体不放大
            byteArrayOf(0x1d, 0x21, 0x11), // 宽高加倍
            byteArrayOf(0x1b, 0x45, 0x00), // 取消加粗模式
            byteArrayOf(0x1b, 0x45, 0x01), // 选择加粗模式
            byteArrayOf(0x1b, 0x7b, 0x00), // 取消倒置打印
            byteArrayOf(0x1b, 0x7b, 0x01), // 选择倒置打印
            byteArrayOf(0x1d, 0x42, 0x00), // 取消黑白反显
            byteArrayOf(0x1d, 0x42, 0x01), // 选择黑白反显
            byteArrayOf(0x1b, 0x56, 0x00), // 取消顺时针旋转90°
            byteArrayOf(0x1b, 0x56, 0x01))// 选择顺时针旋转90°

    /**
     * 获取设备名称
     *
     * @return String
     */


    init {
        this.context = context
    }

    /**
     * 连接蓝牙设备
     */
    fun connect(device: BluetoothDevice): Boolean {
        if (!this.isConnection) {
            try {
                bluetoothSocket = device!!
                        .createRfcommSocketToServiceRecord(uuid)
                bluetoothSocket!!.connect()
                outputStream = bluetoothSocket!!.outputStream
                this.isConnection = true
                if (this.bluetoothAdapter.isDiscovering) {
                    println("关闭适配器！")
                    this.bluetoothAdapter.isDiscovering
                }
            } catch (e: Exception) {
                ZXToastUtil.showToast("连接失败！")
                return false
            }

            ZXToastUtil.showToast(device!!.name + "连接成功！")
            return true
        } else {
            return true
        }
    }

    /**
     * 发送数据
     */
    fun send(data: ByteArray) {
        if (this.isConnection) {
            println("开始打印！！")
            try {
                outputStream!!.write(data, 0, data.size)
                outputStream!!.flush()
            } catch (e: IOException) {
                ZXToastUtil.showToast("发送失败！")
            }

        } else {
            ZXToastUtil.showToast("设备未连接，请重新连接！")
        }
    }

    companion object {
        private var bluetoothSocket: BluetoothSocket? = null
        private var outputStream: OutputStream? = null
        private val uuid = UUID
                .fromString("00001101-0000-1000-8000-00805F9B34FB")

        /**
         * 断开蓝牙设备连接
         */
        fun disconnect() {
            println("断开蓝牙设备连接")
            try {
                bluetoothSocket!!.close()
                outputStream!!.close()
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }
    }

}