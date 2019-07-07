package com.zx.module_map.module.func.tool

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import com.zx.module_library.bean.MapTaskBean
import com.zx.module_map.R
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXToastUtil
import java.net.URISyntaxException

/**
 * Created by Xiangb on 2019/7/7.
 * 功能：导航工具
 */
object NaviTool {

    fun openNavi(context: Context, taskBean: MapTaskBean?){
        if (taskBean?.longtitude != null &&taskBean.latitude!=null){
            val naviView = LayoutInflater.from(context).inflate(R.layout.layout_map_navi, null,false)
            val naviBaidu = naviView.findViewById<LinearLayout>(R.id.ll_navi_baidu)
            val naviGaode = naviView.findViewById<LinearLayout>(R.id.ll_navi_gaode)
            val naviTengxun = naviView.findViewById<LinearLayout>(R.id.ll_navi_tengxun)
            //百度
            naviBaidu.setOnClickListener {
                if (isAvilible(context, "com.baidu.BaiduMap")) {//传入指定应用包名
                    try {
                        //坐标转换
                        val bd09 = PointConversionTool.wgs84tobd09(taskBean.longtitude!!, taskBean.latitude!!)
                        //                          intent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                        val intent = Intent.getIntent("intent://map/direction?" +
                                //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                                "destination=latlng:" + bd09 + "|name:" + taskBean.address +        //终点

                                "&mode=driving&" +          //导航路线方式

                                "&src=市场监管#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end")
                        context.startActivity(intent)
                    } catch (e: URISyntaxException) {
                    }

                } else {//未安装
                    //market为路径，id为包名
                    //显示手机上所有的market商店
                    Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG).show()
                    val uri = Uri.parse("market://details?id=com.baidu.BaiduMap")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
                ZXDialogUtil.dismissDialog()
            }
            //高德
            naviGaode.setOnClickListener {
                if (isAvilible(context, "com.autonavi.minimap")) {
                    try {
                        val gcj02 = PointConversionTool.wgs84togcj02(taskBean.longtitude!!, taskBean.latitude!!).split(",")
                        val intent = Intent.getIntent("amapuri://route/plan/?" +
                                "sourceApplication=市场监管" +
                                "&dname=" + taskBean.address + "" +
                                "&dlat=" + gcj02[0] + "&dlon=" + gcj02[1] + "" +
                                "&dev=0")
                        context.startActivity(intent)
                    } catch (e: URISyntaxException) {
                        e.printStackTrace()
                    }

                } else {
                    Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show()
                    val uri = Uri.parse("market://details?id=com.autonavi.minimap")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
                ZXDialogUtil.dismissDialog()
            }
            //腾讯
            naviTengxun.setOnClickListener {
                if (isAvilible(context, "com.tencent.map")) {//传入指定应用包名
                    //double[] gotoLang=AMAPUtils.getInstance().bdToGaoDe(Double.parseDouble(gotoLatitude),Double.parseDouble(gotoLongitude));
                    //gotoLatitude=String.valueOf(gotoLang[0]);gotoLongitude=String.valueOf(gotoLang[1]);

                    val gcj02 = PointConversionTool.wgs84togcj02(taskBean.longtitude!!, taskBean.latitude!!)
                    val url1 = "qqmap://map/routeplan?" +
                            "type=drive" +
                            "&to=" + taskBean.address + "" +
                            "&tocoord=" + gcj02 +
                            "&policy=2&referer=市场监管"
                    val intent = Intent("android.intent.action.VIEW", Uri.parse(url1))
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "您尚未安装腾讯地图", Toast.LENGTH_LONG).show()
                    val uri = Uri.parse("market://details?id=com.tencent.map")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
                ZXDialogUtil.dismissDialog()
            }
            ZXDialogUtil.showCustomViewDialog(context, "",naviView, null,null, true)
        }else{
            ZXToastUtil.showToast("当前主体暂无坐标")
        }
    }

    /* 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    fun isAvilible(context: Context, packageName: String): Boolean {
        //获取packagemanager
        val packageManager = context.packageManager
        //获取所有已安装程序的包信息
        val packageInfos = packageManager.getInstalledPackages(0)
        //用于存储所有已安装程序的包名
        val packageNames = ArrayList<String>()
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (i in packageInfos.indices) {
                val packName = packageInfos[i].packageName
                packageNames.add(packName)
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName)
    }

}