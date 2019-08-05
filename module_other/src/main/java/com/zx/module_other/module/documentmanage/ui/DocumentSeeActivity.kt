package com.zx.module_other.module.documentmanage.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.BaseConfigModule.Companion.BASE_IP
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_other.R
import com.zx.module_other.XAppOther
import com.zx.module_other.api.ApiConfigModule
import com.zx.module_other.api.ApiParamUtil
import com.zx.module_other.module.documentmanage.bean.Children
import com.zx.module_other.module.print.bean.PrintBean
import com.zx.module_other.module.print.func.util.PrintDataUtil
import com.zx.module_other.module.print.ui.PrintActivity
import com.zx.module_other.module.print.ui.StartPrintActivity
import com.zx.module_other.module.workplan.mvp.contract.DocumentSeeContract
import com.zx.module_other.module.workplan.mvp.model.DocumentSeeModel
import com.zx.module_other.module.workplan.mvp.presenter.DocumentSeePresenter
import com.zx.zxutils.util.ZXPermissionUtil
import com.zx.zxutils.util.ZXToastUtil
import kotlinx.android.synthetic.main.activity_document_see.*

@Route(path = RoutePath.ROUTE_OTHER_DOCUMENTSEE)
class DocumentSeeActivity : BaseActivity<DocumentSeePresenter, DocumentSeeModel>(), DocumentSeeContract.View {

    var bluetoothAdapter: BluetoothAdapter? = null
    //    val mReceiver = BluetoothReceive()
    val devices = arrayListOf<BluetoothDevice>()
    var data: Bitmap? = null;

    companion object {
        val TYPE_FILL = 0
        val TYPE_CHANGE = 1
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, children: Children, type: Int, printUrl: String) {
            val intent = Intent(activity, DocumentSeeActivity::class.java)
            intent.putExtra("children", children)
            intent.putExtra("type", type)
            intent.putExtra("printUrl", printUrl)
            activity.startActivity(intent)
            if (isFinish) activity.finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewListener() {
        btn_fill_document.setOnClickListener {
            DocumentFillActivity.startAction(this, true, intent.getSerializableExtra("children") as Children)
        }
        btn_print_document.setOnClickListener {

            if (PrintDataUtil.getWebViewImgData(wv_documentsee) == PrintDataUtil.SUC) {
                if (devices.size == 0) {
                    PrintActivity.startAction(this, true, (intent.getSerializableExtra("children") as Children).name, PrintDataUtil.IMAGE_PATH)
                } else {
                    StartPrintActivity.startAction(this, true, (intent.getSerializableExtra("children") as Children).name, devices, PrintDataUtil.IMAGE_PATH)
                }
            } else {
                ZXToastUtil.showToast("获取打印数据失败请重试！")
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_document_see
    }

    @SuppressLint("ResourceAsColor", "NewApi")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toobar_view.withXApp(XAppOther.DOCUMENT)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!ZXPermissionUtil.checkPermissionsByArray(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            ZXPermissionUtil.requestPermissionsByArray(this)
        }
        btn_fill_document.background.setTint(ContextCompat.getColor(this, XAppOther.get("文书管理")!!.moduleColor))
        btn_print_document.background.setTint(ContextCompat.getColor(this, XAppOther.get("文书管理")!!.moduleColor))

        wv_documentsee.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.supportZoom()
//            settings.displayZoomControls = false
//            settings.builtInZoomControls = true
            settings.textZoom = 150
            // settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            webViewClient = WebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
                    return true
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)

                }
            }
        }
        when (intent.getIntExtra("type", TYPE_FILL)) {
            TYPE_FILL -> {
                toobar_view.setMidText(resources.getString(R.string.see_document))
                btn_fill_document.setText(resources.getString(R.string.fill_document))
//                var url = BASE_IP + ApiConfigModule.URL_DOCUMENT + "queryDetailHtml.do?"
//                for ((key, value) in ApiParamUtil.getDocumentMoldeParam((intent.getSerializableExtra("children") as Children).id)) {
//                    url += key + "=" + value + "&"
//                }
//                url = url.substring(0, url.length - 1)
//                wv_documentsee.loadUrl(url)
                mPresenter.getDocumentWeb(ApiParamUtil.getDocumentMoldeParam((intent.getSerializableExtra("children") as Children).id))
            }
            TYPE_CHANGE -> {
                btn_fill_document.setText(resources.getString(R.string.goon_fill))
                toobar_view.setMidText(resources.getString(R.string.preview_document))
                setHtml(intent.getStringExtra("printUrl"))
            }
        }
        if (bluetoothAdapter!!.isEnabled()) {
            for (device in bluetoothAdapter!!.bondedDevices) {
                if (device.type == PrintBean.PRINT_TYPE) {
                    devices.add(device)
                }
            }
        }

//        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
//        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
//        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
//        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
//        registerReceiver(mReceiver, filter)
//        searchDevices()
//        getBluetoothData()
    }

    override fun getDocumentWebSeeResult(weburl: String) {
        setHtml(weburl)
    }

    /**
     * 设置需展示的Html片段
     */
    private fun setHtml(protocol: String?) {
        if (protocol != null) {
            wv_documentsee.getSettings().setDefaultTextEncodingName("utf-8")
            wv_documentsee.loadDataWithBaseURL("", protocol, "text/html; charset=UTF-8", "UTF-8", null)
        }
    }

//    @SuppressLint("ResourceAsColor")
//    fun searchDevices() {
//        if (bluetoothAdapter!!.isEnabled()) {
//            bluetoothAdapter!!.startDiscovery()
//        }
//    }
//
//    fun getBluetoothData() {
//        mRxManager.on("Bluetooth", Action1<BluetoothDevice> {
//            for (device in devices) {
//                if (device.address == it.address) {
//                    devices.remove(device)
//                }
//            }
//            if (it.bondState == BluetoothDevice.BOND_BONDED) {
//                devices.add(it)
//            }
//        })
//        mRxManager.on("bluetoothState", Action1<BluetoothDevice> {
//            for (device in devices) {
//                if (device.address == it.address) {
//                    devices.remove(it)
//                }
//                if (it.bondState != BluetoothDevice.BOND_BONDED) {
//                    devices.add(it)
//                }
//            }
//        })
//    }
}