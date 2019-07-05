package com.zx.module_map.module.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.esri.android.map.GraphicsLayer
import com.esri.android.map.LocationDisplayManager
import com.esri.android.map.event.OnSingleTapListener
import com.esri.android.map.event.OnStatusChangedListener
import com.esri.android.runtime.ArcGISRuntime
import com.esri.core.symbol.MarkerSymbol
import com.esri.core.symbol.PictureMarkerSymbol
import com.zx.module_library.base.BaseFragment
import com.zx.module_map.R
import com.zx.module_map.module.func.listener.MapListener
import com.zx.module_map.module.func.tianditu.TianDiTuLayer
import com.zx.module_map.module.func.tianditu.TianDiTuLayerTypes
import com.zx.module_map.module.func.tool.OnlineTileLayer
import com.zx.module_map.module.mvp.contract.MapContract
import com.zx.module_map.module.mvp.model.MapModel
import com.zx.module_map.module.mvp.presenter.MapPresenter
import com.zx.zxutils.util.ZXLocationUtil
import kotlinx.android.synthetic.main.fragment_map.*

/**
 * Create By admin On 2017/7/11
 * 功能：地图
 */
class MapFragment : BaseFragment<MapPresenter, MapModel>(), MapContract.View, OnStatusChangedListener, OnSingleTapListener {

    private var vectorLayer: OnlineTileLayer? = null

    private var vectorGlobalLayer: TianDiTuLayer? = null
    private var vectorGlobalLable: TianDiTuLayer? = null
    private var imageGlobalLayer: TianDiTuLayer? = null
    private var imageGlobalLable: TianDiTuLayer? = null

    private var mVecLayer: TianDiTuLayer? = null
    private var mImgLayer: TianDiTuLayer? = null
    private var mLabLayer: TianDiTuLayer? = null
    private var mGridLayer: TianDiTuLayer? = null

    private var mGLayer: GraphicsLayer? = null
    private var mMarkersGLayer: GraphicsLayer? = null// 用于展示主体或任务结果注记

    val mapListener = MyListener()

    private var locationDisplayManager: LocationDisplayManager? = null

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): MapFragment {
            val fragment = MapFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_map
    }

    /**
     * 初始化
     */
    @SuppressLint("MissingPermission")
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ArcGISRuntime.setClientId("5SKIXc21JlankElJ")
        ArcGISRuntime.License.setLicense("runtimestandard,101,rux00000,none,XXXXXXX")
        map_view.setMapBackground(-1, -1, 0.0f, 0.0f)

//        map_view.addLayer(ArcGISTiledMapServiceLayer("http://www.tianditucq.com/RemoteRest/services/CQMap_VEC/MapServer"))

        initBaseLayer()
    }

    //初始化基础底图
    private fun initBaseLayer() {
        vectorGlobalLayer = TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_VECTOR_2000)
        vectorGlobalLable = TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000)
        imageGlobalLayer = TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_IMAGE_2000)
        imageGlobalLable = TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000)
        map_view.addLayer(vectorGlobalLayer)
        map_view.addLayer(vectorGlobalLable)
        map_view.addLayer(imageGlobalLayer)
        map_view.addLayer(imageGlobalLable)
        imageGlobalLayer!!.isVisible = false
        imageGlobalLable!!.isVisible = false

        map_view.setOnStatusChangedListener { any, status ->
            if (status == OnStatusChangedListener.STATUS.INITIALIZED) {
                mapListener.doLocation()
            }
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    //状态改变事件
    override fun onStatusChanged(p0: Any?, status: OnStatusChangedListener.STATUS?) {
        if (status == OnStatusChangedListener.STATUS.INITIALIZED) {
            mapListener.doLocation()
        }
    }

    //地图点击事件
    override fun onSingleTap(p0: Float, p1: Float) {

    }

    inner class MyListener : MapListener {
        override fun doLocation() {
            getPermission(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) {
                var location: Location? = null
                if (locationDisplayManager != null) {
                    locationDisplayManager!!.isShowLocation = !locationDisplayManager!!.isShowLocation
                    locationDisplayManager!!.isShowPings = !locationDisplayManager!!.isShowPings
                    if (locationDisplayManager!!.isShowLocation) {
                        location = locationDisplayManager!!.location
                    }
                } else {
                    locationDisplayManager = map_view.locationDisplayManager.apply {
                        isAllowNetworkLocation = true
                        isShowLocation = true
                        isUseCourseSymbolOnMovement = false
                        isAccuracyCircleOn = false
                        locationAcquiringSymbol = PictureMarkerSymbol(activity!!, ContextCompat.getDrawable(activity!!, R.drawable.location_symbol)) as MarkerSymbol?
                        defaultSymbol = PictureMarkerSymbol(activity!!, ContextCompat.getDrawable(activity!!, R.drawable.location_symbol))
                        autoPanMode = LocationDisplayManager.AutoPanMode.LOCATION
                        isShowPings = true
                        start()
                        location = this.location
                    }
                }
                if (location != null) {
                    map_view.centerAt(location!!.latitude, location!!.longitude, true)
                    map_view.scale = 70000.00
                } else if (ZXLocationUtil.getLocation(activity!!) != null) {
                    map_view.centerAt(ZXLocationUtil.getLocation(activity!!)!!.latitude, ZXLocationUtil.getLocation(activity!!)!!.longitude, true)
                    map_view.scale = 70000.00
                } else {
                    showToast("当前GPS信号弱，未获取到位置信息，请稍后再试")
                }
            }
        }

        override fun changeMap() {

        }

    }

}
