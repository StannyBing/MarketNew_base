package com.zx.module_other.module.print.ui

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.widget.ArrayAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.lvrenyang.io.BTPrinting
import com.lvrenyang.io.IOCallBack
import com.lvrenyang.io.Pos
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.module.print.func.util.PrintDataService
import com.zx.module_other.module.print.mvp.contract.StartPrintContract
import com.zx.module_other.module.print.mvp.model.StartPrintModel
import com.zx.module_other.module.print.mvp.presenter.StartPrintPresenter
import com.zx.zxutils.util.ZXToastUtil
import kotlinx.android.synthetic.main.activity_start_print.*
import java.io.FileInputStream
import java.util.concurrent.Executors


/**
 * Create By admin On 2017/7/11
 * 功能：
 */

@Route(path = RoutePath.ROUTE_OTHER_PRINT_STRATPRINT)
class StartPrintActivity : BaseActivity<StartPrintPresenter, StartPrintModel>(), StartPrintContract.View, IOCallBack {
    var devices = arrayListOf<BluetoothDevice>()
    var printerNames = arrayListOf<String>()
    var sAdpter: ArrayAdapter<String>? = null
    var printDataService: PrintDataService? = null
    var es = Executors.newScheduledThreadPool(30)
    var pos = Pos()
    var mBt = BTPrinting()


    companion object {
        /**
         * 启动器
         */

        fun startAction(activity: Activity, isFinish: Boolean, docName: String, devices: ArrayList<BluetoothDevice>, filePath: String) {
            val intent = Intent(activity, StartPrintActivity::class.java)
            intent.putExtra("docName", docName)
            intent.putExtra("devices", devices)
            intent.putExtra("filePath", filePath)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_start_print
    }

    /**
     * 初始化
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        tv_print_name.setText(intent.getStringExtra("docName"))
        toolbar_view.withXApp(XAppOther.PRINT)
        sAdpter = ArrayAdapter(this, com.zx.module_other.R.layout.item_start_print, R.id.text, printerNames)
        printDataService = PrintDataService(this)
        btn_print.background.setTint(ContextCompat.getColor(this, XAppOther.PRINT.moduleColor))
        devices = intent.getParcelableArrayListExtra("devices")
        s_printer.adapter = sAdpter
        setSprinner()
        pos.Set(mBt)
        mBt.SetCallBack(this);
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        btn_print.setOnClickListener {
            if (mBt.IsOpened()) {
                es.submit(TaskPrint(pos))
            } else {
                es.submit(TaskOpen(devices.get(s_printer.selectedItemPosition).address, this))
            }
        }
    }


    fun setSprinner() {
        printerNames.clear()
        for (device in devices) {
            printerNames.add(device.name)
        }
        sAdpter!!.notifyDataSetChanged()
    }

    inner class TaskOpen(address: String, context: Context) : Runnable {
        internal var address: String? = null
        internal var context: Context? = null

        init {
            this.address = address
            this.context = context
        }

        override fun run() {
            // TODO Auto-generated method stub
            mBt!!.Open(address, context)
        }
    }

    inner class TaskPrint(pos: Pos) : Runnable {
        internal var pos: Pos? = null

        init {
            this.pos = pos
        }

        override fun run() {
            val fs = FileInputStream(intent.getStringExtra("filePath"))
            val bitmap = BitmapFactory.decodeStream(fs)
            pos!!.POS_PrintPicture(bitmap, 576, 1, 0)
            var suc = false
            suc = pos!!.GetIO().IsOpened()
            runOnUiThread {
                if (suc) {
                    ZXToastUtil.showToast("成功")
                } else {
                    ZXToastUtil.showToast("失败")
                }

            }
        }
    }


    override fun OnClose() {
    }

    override fun OnOpenFailed() {
        runOnUiThread {
            ZXToastUtil.showToast("连接失败")

        }
    }

    override fun OnOpen() {
        runOnUiThread {
            ZXToastUtil.showToast("连接成功")
            es.submit(TaskPrint(pos))
        }
    }
}
