//package com.zx.module_other.module.print.func.util
//
//import com.tencent.bugly.crashreport.common.info.AppInfo
//import com.xmwdkk.boothprint.BtService
//
//package com.xmwdkk.boothprint.print
//
//import android.bluetooth.BluetoothAdapter
//import android.bluetooth.BluetoothDevice
//import android.content.Context
//import android.text.TextUtils
//
//import com.xmwdkk.boothprint.base.AppInfo
//import com.xmwdkk.boothprint.bt.BtService
//
//import java.util.ArrayList
//
///**
// *
// *
// *
// * this is print queue.
// * you can simple add print bytes to queue. and this class will send those bytes to bluetooth device
// * 这是打印队列。
// *   你可以简单地添加打印字节来排队。 并且这个类将这些字节发送到蓝牙设备
// */
//class PrintQueue private constructor() {
//    /**
//     * print queue
//     */
//    private var mQueue: ArrayList<ByteArray>? = null
//    /**
//     * bluetooth adapter
//     */
//    private var mAdapter: BluetoothAdapter? = null
//    /**
//     * bluetooth service
//     */
//    private var mBtService: BtService? = null
//
//    /**
//     * add print bytes to queue. and call print
//     *
//     * @param bytes bytes
//     */
//    @Synchronized
//    fun add(bytes: ByteArray?) {
//        if (null == mQueue) {
//            mQueue = ArrayList()
//        }
//        if (null != bytes) {
//            mQueue!!.add(bytes)
//        }
//        print()
//    }
//
//    /**
//     * add print bytes to queue. and call print
//     *
//     */
//    @Synchronized
//    fun add(bytesList: ArrayList<ByteArray>?) {
//        if (null == mQueue) {
//            mQueue = ArrayList()
//        }
//        if (null != bytesList) {
//            mQueue!!.addAll(bytesList)
//        }
//        print()
//    }
//
//    /**
//     * print queue
//     */
//    @Synchronized
//    fun print() {
//        try {
//            if (null == mQueue || mQueue!!.size <= 0) {
//                return
//            }
//            if (null == mAdapter) {
//                mAdapter = BluetoothAdapter.getDefaultAdapter()
//            }
//            if (null == mBtService) {
//                mBtService = BtService(mContext)
//            }
//            if (mBtService!!.getState() !== BtService.STATE_CONNECTED) {
//                if (!TextUtils.isEmpty(AppInfo.btAddress)) {
//                    val device = mAdapter!!.getRemoteDevice(AppInfo.btAddress)
//                    mBtService!!.connect(device)
//                    return
//                }
//            }
//            while (mQueue!!.size > 0) {
//                mBtService!!.write(mQueue!![0])
//                mQueue!!.removeAt(0)
//            }
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//    /**
//     * disconnect remote device
//     */
//    fun disconnect() {
//        try {
//            if (null != mBtService) {
//                mBtService!!.stop()
//                mBtService = null
//            }
//            if (null != mAdapter) {
//                mAdapter = null
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//    /**
//     * when bluetooth status is changed, if the printer is in use,
//     * connect it,else do nothing
//     */
//    fun tryConnect() {
//        try {
//            if (TextUtils.isEmpty(AppInfo.btAddress)) {
//                return
//            }
//            if (null == mAdapter) {
//                mAdapter = BluetoothAdapter.getDefaultAdapter()
//            }
//            if (null == mAdapter) {
//                return
//            }
//            if (null == mBtService) {
//                mBtService = BtService(mContext)
//            }
//            if (mBtService!!.getState() !== BtService.STATE_CONNECTED) {
//                if (!TextUtils.isEmpty(AppInfo.btAddress)) {
//                    val device = mAdapter!!.getRemoteDevice(AppInfo.btAddress)
//                    mBtService!!.connect(device)
//                    return
//                }
//            } else {
//
//
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } catch (e: Error) {
//            e.printStackTrace()
//        }
//
//    }
//
//    /**
//     * 将打印命令发送给打印机
//     *
//     * @param bytes bytes
//     */
//    fun write(bytes: ByteArray?) {
//        try {
//            if (null == bytes || bytes.size <= 0) {
//                return
//            }
//            if (null == mAdapter) {
//                mAdapter = BluetoothAdapter.getDefaultAdapter()
//            }
//            if (null == mBtService) {
//                mBtService = BtService(mContext)
//            }
//            if (mBtService!!.getState() !== BtService.STATE_CONNECTED) {
//                if (!TextUtils.isEmpty(AppInfo.btAddress)) {
//                    val device = mAdapter!!.getRemoteDevice(AppInfo.btAddress)
//                    mBtService!!.connect(device)
//                    return
//                }
//            }
//            mBtService!!.write(bytes)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//    companion object {
//
//        /**
//         * instance
//         */
//        private var mInstance: PrintQueue? = null
//        /**
//         * context
//         */
//        private var mContext: Context? = null
//
//        fun getQueue(context: Context): PrintQueue {
//            if (null == mInstance) {
//                mInstance = PrintQueue()
//            }
//            if (null == mContext) {
//                mContext = context
//            }
//            return mInstance!!
//        }
//    }
//}
