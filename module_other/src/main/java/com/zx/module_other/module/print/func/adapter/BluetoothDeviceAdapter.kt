package com.zx.module_other.module.print.func.adapter

import com.zx.module_other.R
import com.zx.module_other.module.print.bean.PrintBean
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter

class BluetoothDeviceAdapter<T>(dataBeans: List<T>) : ZXQuickAdapter<T, ZXBaseHolder>(R.layout.item_bluetooth_info, dataBeans) {

    override fun convert(helper: ZXBaseHolder?, item: T) {
        if (item is PrintBean) {
            if (helper != null) {
                helper.setText(R.id.tv_bluetooth_name, item.name)
                when (item.state) {
                    0 -> helper.setText(R.id.tv_bluetooth_connect, "")
                    1 -> helper.setText(R.id.tv_bluetooth_connect, "已配对")
                    2 -> helper.setText(R.id.tv_bluetooth_connect, "正在配对...")
                }
            }
        }
    }
}