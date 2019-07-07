package com.zx.module_map.module.func.tool

/**
 * Created by Xiangb on 2019/7/7.
 * 功能：
 */
object PointConversionTool {
    var x_PI = 3.14159265358979324 * 3000.0 / 180.0
    var PI = 3.1415926535897932384626
    var a = 6378245.0
    var ee = 0.00669342162296594323

    /**
     * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
     * 即 百度 转 谷歌、高德
     *
     * @param bd_lon
     * @param bd_lat
     * @returns {*[]}
     */
    fun bd09togcj02(bd_lon: Double, bd_lat: Double): String {
        val x = bd_lon - 0.0065
        val y = bd_lat - 0.006
        val z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_PI)
        val theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_PI)
        val gg_lng = z * Math.cos(theta)
        val gg_lat = z * Math.sin(theta)
        // Point point=new Point(gg_lng, gg_lat);
        // return point;
        return "$gg_lng,$gg_lat"
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
     * 即谷歌、高德 转 百度
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    fun gcj02tobd09(lng: Double, lat: Double): String {
        val z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * x_PI)
        val theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * x_PI)
        val bd_lng = z * Math.cos(theta) + 0.0065
        val bd_lat = z * Math.sin(theta) + 0.006
        //Point point=new Point(bd_lng, bd_lat);
        // return point;
        return "$bd_lng,$bd_lat"
    }

    ;

    /**
     * WGS84转GCj02
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    fun wgs84togcj02(lng: Double, lat: Double): String {
        var dlat = transformlat(lng - 105.0, lat - 35.0)
        var dlng = transformlng(lng - 105.0, lat - 35.0)
        val radlat = lat / 180.0 * PI
        var magic = Math.sin(radlat)
        magic = 1 - ee * magic * magic
        val sqrtmagic = Math.sqrt(magic)
        dlat = dlat * 180.0 / (a * (1 - ee) / (magic * sqrtmagic) * PI)
        dlng = dlng * 180.0 / (a / sqrtmagic * Math.cos(radlat) * PI)
        val mglat = lat + dlat
        val mglng = lng + dlng
        //Point point=new Point(mglng, mglat);
        // return point;
        return "$mglat,$mglng"
    }

    ;

    /**
     * GCJ02 转换为 WGS84
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    fun gcj02towgs84(lng: Double, lat: Double): String {
        var dlat = transformlat(lng - 105.0, lat - 35.0)
        var dlng = transformlng(lng - 105.0, lat - 35.0)
        val radlat = lat / 180.0 * PI
        var magic = Math.sin(radlat)
        magic = 1 - ee * magic * magic
        val sqrtmagic = Math.sqrt(magic)
        dlat = dlat * 180.0 / (a * (1 - ee) / (magic * sqrtmagic) * PI)
        dlng = dlng * 180.0 / (a / sqrtmagic * Math.cos(radlat) * PI)
        val mglat = lat + dlat
        val mglng = lng + dlng
        // Point point=new Point(mglng, mglat);
        // return point;
        return "$mglat,$mglng"
    }

    ;

    /**
     * WGS84 转换为 BD-09
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     * 返回纬度+经度
     */
    fun wgs84tobd09(lng: Double, lat: Double): String {
        //第一次转换
        var dlat = transformlat(lng - 105.0, lat - 35.0)
        var dlng = transformlng(lng - 105.0, lat - 35.0)
        val radlat = lat / 180.0 * PI
        var magic = Math.sin(radlat)
        magic = 1 - ee * magic * magic
        val sqrtmagic = Math.sqrt(magic)
        dlat = dlat * 180.0 / (a * (1 - ee) / (magic * sqrtmagic) * PI)
        dlng = dlng * 180.0 / (a / sqrtmagic * Math.cos(radlat) * PI)
        val mglat = lat + dlat
        val mglng = lng + dlng

        //第二次转换
        val z = Math.sqrt(mglng * mglng + mglat * mglat) + 0.00002 * Math.sin(mglat * x_PI)
        val theta = Math.atan2(mglat, mglng) + 0.000003 * Math.cos(mglng * x_PI)
        val bd_lng = z * Math.cos(theta) + 0.0065
        val bd_lat = z * Math.sin(theta) + 0.006
        return "$bd_lat,$bd_lng"
    }

    private fun transformlat(lng: Double, lat: Double): Double {
        var ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng))
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0
        return ret
    }

    private fun transformlng(lng: Double, lat: Double): Double {
        var ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng))
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0
        return ret
    }
}