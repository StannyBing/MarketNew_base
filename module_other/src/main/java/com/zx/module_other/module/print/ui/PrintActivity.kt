package com.zx.module_other.module.print.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.module.documentmanage.bean.Children
import com.zx.module_other.module.documentmanage.func.adapter.DocumentAdpater
import com.zx.module_other.module.documentmanage.func.util.DBService
import com.zx.module_other.module.documentmanage.ui.DocumentActivity
import com.zx.module_other.module.documentmanage.ui.DocumentSeeActivity
import com.zx.module_other.module.print.bean.PrintBean
import com.zx.module_other.module.print.func.receiver.BluetoothReceive
import com.zx.module_other.module.print.func.util.PrintDataUtil
import com.zx.module_other.module.print.mvp.contract.PrintContract
import com.zx.module_other.module.print.mvp.model.PrintModel
import com.zx.module_other.module.print.mvp.presenter.PrintPresenter
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity
import kotlinx.android.synthetic.main.activity_document.*
import kotlinx.android.synthetic.main.activity_print.*
import rx.functions.Action1


/**
 * Create By admin On 2017/7/11
 * 功能：文件打印
 */
@Route(path = RoutePath.ROUTE_OTHER_PRINT)
class PrintActivity : BaseActivity<PrintPresenter, PrintModel>(), PrintContract.View {
    var bluetoothAdapter: BluetoothAdapter? = null
    val mReceiver = BluetoothReceive()
    var isOnline = false
    val devices = arrayListOf<BluetoothDevice>()

    private var adapterDatas = arrayListOf<MultiItemEntity>()
    private var documentAdapter = DocumentAdpater(adapterDatas)

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, child: Children?, filePath: String) {
            val intent = Intent(activity, PrintActivity::class.java)
            intent.putExtra("child", child)
            intent.putExtra("filePath", filePath)
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
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        toolbar_view.withXApp(XAppOther.PRINT)
        disOnline()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        registerReceiver(mReceiver, filter)
        getBluetoothData()
        searchDevices()
        getHistory()
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        rl_printer.setOnClickListener {
            if (devices.size == 0) {
                BluetoothActivity.startAction(this, false)
            } else {
                if (intent.getSerializableExtra("child") != null) {
                    PrintActivity.startAction(this, true, intent.getSerializableExtra("child") as Children, PrintDataUtil.IMAGE_PATH)
                } else {
                    DocumentActivity.startAction(this, true)
                }
            }
        }

        documentAdapter.setOnItemClickListener { adapter, view, position ->
            if (adapterDatas[position].itemType == DocumentAdpater.TYPE_LEVEL_1) {
                DocumentSeeActivity.startAction(this, false, adapterDatas[position] as Children, DocumentSeeActivity.TYPE_FILL, "")
            }
        }
    }


    @SuppressLint("ResourceAsColor")
    fun searchDevices() {
        if (bluetoothAdapter!!.isEnabled()) {
            for (device in bluetoothAdapter!!.bondedDevices) {
                if (device.bluetoothClass.majorDeviceClass == BluetoothClass.Device.Major.IMAGING) {
                    devices.add(device)
                }
            }
            if (devices.size > 0) {
                setOnline(devices[0].name)
            }
        } else {
            disOnline()
        }

    }


    @SuppressLint("ResourceAsColor")
    fun setOnline(printName: String) {
        isOnline = true
        iv_print_pic.setImageResource(R.drawable.printer_online)
        iv_printer_name.setText(printName)
        iv_printer_name.setTextColor(R.color.text_color_noraml)
        iv_print_state.setText("在线")
        iv_print_state.setTextColor(R.color.text_color_light)
        iv_print_state.textSize = R.dimen.text_size_small.toFloat()
    }

    @SuppressLint("ResourceAsColor")
    fun disOnline() {
        iv_print_pic.setImageResource(R.drawable.printer)
        iv_printer_name.setText("暂无可用打印设备")
        iv_printer_name.setTextColor(R.color.text_color_light)
        iv_print_state.setText("点击设置")
        iv_print_state.setTextColor(R.color.text_color_drak)
    }

    fun getBluetoothData() {
        mRxManager.on("bluetoothState", Action1<BluetoothDevice> {
            for (device in devices) {
                if (device.address == it.address) {
                    devices.remove(it)
                }
                if (it.bondState != BluetoothDevice.BOND_BONDED) {
                    devices.add(it)
                }
                if (devices.size == 0) {
                    disOnline()
                }
            }
        })
        mRxManager.on("bluetoothOpen", Action1<Int> {
            when (it) {
                BluetoothAdapter.STATE_OFF -> {
                    disOnline()
                }
                BluetoothAdapter.STATE_ON -> {
                    for (device in bluetoothAdapter!!.bondedDevices) {
                        devices.add(device)
                    }
                    if (devices.size > 0) {
                        setOnline(devices[0].name)
                    }
                }
            }
        })
    }

    fun getHistory() {
        rv_print_history.apply {
            layoutManager = LinearLayoutManager(this@PrintActivity)
            adapter = documentAdapter
        }
        val children = DBService.getDBService().getMessage()
        adapterDatas.clear()
        for (child in children) {
            adapterDatas.add(child)
        }
        documentAdapter.setNewData(adapterDatas)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
}
