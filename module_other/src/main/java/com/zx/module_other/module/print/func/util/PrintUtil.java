package com.zx.module_other.module.print.func.util;

import android.graphics.Bitmap;

import java.io.IOException;

//package com.zx.module_other.module.print.func.util;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.text.TextUtils;
//
//import com.tencent.bugly.crashreport.common.info.AppInfo;
//
//
//import java.util.Set;
//
//
///**
// * Created by liuguirong on 2017/8/3.
// *   printer util
// */
public class PrintUtil {
//
//    private static final String FILENAME = "bt";
//    private static final String DEFAULT_BLUETOOTH_DEVICE_ADDRESS = "default_bluetooth_device_address";//蓝牙设备地址
//    private static final String DEFAULT_BLUETOOTH_DEVICE_NAME = "default_bluetooth_device_name";//蓝牙设备名称
//
//    public static final String ACTION_PRINT_TEST = "action_print_test";
//    public static final String ACTION_PRINT_TEST_TWO = "action_print_test_two";
//    public static final String ACTION_PRINT = "action_print";
//    public static final String ACTION_PRINT_TICKET = "action_print_ticket";
//    public static final String ACTION_PRINT_BITMAP = "action_print_bitmap";
//    public static final String ACTION_PRINT_PAINTING = "action_print_painting";
//
//    public static final String PRINT_EXTRA = "print_extra";
//
//    public static void setDefaultBluetoothDeviceAddress(Context mContext, String value) {
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(DEFAULT_BLUETOOTH_DEVICE_ADDRESS, value);
//        editor.apply();
//        PrintInfo.btAddress = value;
//    }
//
//    public static void setDefaultBluetoothDeviceName(Context mContext, String value) {
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(DEFAULT_BLUETOOTH_DEVICE_NAME, value);
//        editor.apply();
//        PrintInfo.btName = value;
//    }
//    //是否绑定了打印机设备
//    public static boolean isBondPrinter(Context mContext, BluetoothAdapter bluetoothAdapter) {
//        if (!BtUtil.isOpen(bluetoothAdapter)) {
//            return false;
//        }
//        String defaultBluetoothDeviceAddress = getDefaultBluethoothDeviceAddress(mContext);
//        if (TextUtils.isEmpty(defaultBluetoothDeviceAddress)) {
//            return false;
//        }
//        Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices();
//        if (deviceSet == null || deviceSet.isEmpty()) {
//            return false;
//        }
//        for (BluetoothDevice bluetoothDevice : deviceSet) {
//            if (bluetoothDevice.getAddress().equals(defaultBluetoothDeviceAddress)) {
//                return true;
//            }
//        }
//        return false;
//
//    }
//
//    public static String getDefaultBluethoothDeviceAddress(Context mContext) {
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(DEFAULT_BLUETOOTH_DEVICE_ADDRESS, "");
//    }
//
//    public static boolean isBondPrinterIgnoreBluetooth(Context mContext) {
//        String defaultBluetoothDeviceAddress = getDefaultBluethoothDeviceAddress(mContext);
//        return !(TextUtils.isEmpty(defaultBluetoothDeviceAddress)
//                || TextUtils.isEmpty(getDefaultBluetoothDeviceName(mContext)));
//    }
//    //绑定设备的蓝牙名称
//    public static String getDefaultBluetoothDeviceName(Context mContext) {
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(DEFAULT_BLUETOOTH_DEVICE_NAME, "");
//    }
//
//    /**
//     * use new api to reduce file operate
//     *
//     * @param editor editor
//     */
//    public static void apply(SharedPreferences.Editor editor) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//            editor.apply();
//        } else {
//            editor.commit();
//        }
//    }
public byte[] draw2PxPoint(Bitmap bmp) throws IOException {

    //用来存储转换后的 bitmap 数据。为什么要再加1000，这是为了应对当图片高度无法
    //整除24时的情况。比如bitmap 分辨率为 240 * 250，占用 7500 byte，
    //但是实际上要存储11行数据，每一行需要 24 * 240 / 8 =720byte 的空间。再加上一些指令存储的开销，
    //所以多申请 1000byte 的空间是稳妥的，不然运行时会抛出数组访问越界的异常。
    int size = bmp.getWidth() * bmp.getHeight() / 8 + 1000;
    byte[] data = new byte[size];
    int k = 0;
    //设置行距为0的指令
    data[k++] = 0x1B;
    data[k++] = 0x33;
    data[k++] = 0x00;
    // 逐行打印
    for (int j = 0; j < bmp.getHeight() / 24f; j++) {
        //打印图片的指令
        data[k++] = 0x1B;
        data[k++] = 0x2A;
        data[k++] = 33;
        data[k++] = (byte) (bmp.getWidth() % 256); //nL
        data[k++] = (byte) (bmp.getWidth() / 256); //nH
        //对于每一行，逐列打印
        for (int i = 0; i < bmp.getWidth(); i++) {
            //每一列24个像素点，分为3个字节存储
            for (int m = 0; m < 3; m++) {
                //每个字节表示8个像素点，0表示白色，1表示黑色
                for (int n = 0; n < 8; n++) {
                    byte b = px2Byte(i, j * 24 + m * 8 + n, bmp);
                    data[k] += data[k] + b;
                }
                k++;
            }
        }
        data[k++] = 10;//换行
    }
    return data;
}
    /**
     *      * 灰度图片黑白化，黑色是1，白色是0
     *      *
     *      * @param x   横坐标
     *      * @param y   纵坐标
     *      * @param bit 位图
     *      * @return
     *
     */
    public static byte px2Byte(int x, int y, Bitmap bit) {
        if (x < bit.getWidth() && y < bit.getHeight()) {
            byte b;
            int pixel = bit.getPixel(x, y);
            int red = (pixel & 0x00ff0000) >> 16; // 取高两位
            int green = (pixel & 0x0000ff00) >> 8; // 取中两位
            int blue = pixel & 0x000000ff; // 取低两位
            int gray = RGB2Gray(red, green, blue);
            if (gray < 128) {
                b = 1;
            } else {
                b = 0;
            }
            return b;
        }
        return 0;
    }

    /**
     * 图片灰度的转化
     *
     */
    private static int RGB2Gray(int r, int g, int b) {
        int gray = (int) (0.29900 * r + 0.58700 * g + 0.11400 * b); //灰度转化公式
        return gray;
    }


}
