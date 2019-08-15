package com.zx.module_map.module.ui

import android.Manifest
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import com.esri.core.geometry.Point
import com.zx.module_library.BuildConfig
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.MapTaskBean
import com.zx.module_library.func.tool.toJson
import com.zx.module_map.R
import com.zx.module_map.XAppMap
import com.zx.module_map.module.bean.DeptBean
import com.zx.module_map.module.bean.DicTypeBean
import com.zx.module_map.module.bean.MapBtnBean
import com.zx.module_map.module.func.adapter.BtnAdapter
import com.zx.module_map.module.func.listener.MapListener
import com.zx.module_map.module.func.tool.NaviTool
import com.zx.module_map.module.mvp.contract.MapBtnContract
import com.zx.module_map.module.mvp.model.MapBtnModel
import com.zx.module_map.module.mvp.presenter.MapBtnPresenter
import com.zx.zxutils.entity.KeyValueEntity
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXLocationUtil
import com.zx.zxutils.views.BubbleView.ZXBubbleView
import com.zx.zxutils.views.ZXSpinner
import kotlinx.android.synthetic.main.fragment_map_btn.*
import java.util.*

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MapBtnFragment : BaseFragment<MapBtnPresenter, MapBtnModel>(), MapBtnContract.View {

    var mapListener: MapListener? = null
    private val btnList = arrayListOf<MapBtnBean>()
    private val btnAdapter = BtnAdapter(btnList)

    private var layerBubble: ZXBubbleView? = null

    private var taskBean: MapTaskBean? = null

    private var spStation: ZXSpinner? = null
    private var spGrid: ZXSpinner? = null
    private var stationKeyValues = arrayListOf<KeyValueEntity>()
    private var changePoint: Point? = null

    private var type = 0
    //0普通
    //1主体查看
    //2坐标选取

    companion object {
        /**
         * 启动器
         */
        fun newInstance(type: Int): MapBtnFragment {
            val fragment = MapBtnFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_map_btn
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        type = arguments!!.getInt("type")

        when (type) {
            1 -> {
                taskBean = activity!!.intent.getSerializableExtra("taskBean") as MapTaskBean?
            }
            2 -> {

            }
        }

        btnAdapter.setColor(ContextCompat.getColor(activity!!, XAppMap.MAP.moduleColor))
        rv_map_btn.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = btnAdapter
        }

        initBtn()

        initLayerBubble()
    }

    //初始化右侧按钮列表
    private fun initBtn() {
        btnAdapter.addData(MapBtnBean("图层", R.drawable.btn_layer))
        btnAdapter.addData(MapBtnBean("定位", R.drawable.btn_location))
        if (type == 1 && taskBean != null) {
            btnAdapter.addData(MapBtnBean("导航", R.drawable.btn_navi))
            if (taskBean!!.typeName == "主体查询") {
                btnAdapter.addData(MapBtnBean("坐标纠正", R.drawable.btn_correct))
            }
        }
    }

    //初始化图层弹窗
    private fun initLayerBubble() {
        val layerView = LayoutInflater.from(activity).inflate(R.layout.layout_layer_view, null, false)
        val ivVector = layerView.findViewById<ImageView>(R.id.iv_layer_vector)
        val ivImage = layerView.findViewById<ImageView>(R.id.iv_layer_image)
        ivVector.setOnClickListener { mapListener?.changeMap("vector") }
        ivImage.setOnClickListener { mapListener?.changeMap("image") }
        layerBubble = ZXBubbleView(activity).setBubbleView(layerView)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        view?.setOnTouchListener { _, _ ->
            layerBubble?.dismiss()
            return@setOnTouchListener false
        }

        btnAdapter.setOnItemClickListener { _, view, position ->
            when (btnList[position].name) {
                "图层" -> {
                    layerBubble?.show(view, Gravity.LEFT)
                }
                "定位" -> {
                    mapListener?.doLocation()
                }
                "导航" -> {
                    NaviTool.openNavi(activity!!, taskBean)
                }
                "坐标纠正" -> {
                    changePos()
                }
            }
        }
    }

    private fun changePos() {
        mapListener?.doLocation()
        getPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            val location = ZXLocationUtil.getLocation(activity)
            var longitude = mapListener?.getMap()?.locationDisplayManager?.location?.longitude
            var latitude = mapListener?.getMap()?.locationDisplayManager?.location?.latitude
            if (longitude == null || latitude == null) {
                longitude = location?.longitude
                latitude = location?.latitude
            }
            val changeView = LayoutInflater.from(activity).inflate(R.layout.layout_change_pos, null)
            val etLocation = changeView.findViewById<EditText>(R.id.et_change_locaton)
            spStation = changeView.findViewById<ZXSpinner>(R.id.sp_change_station)
            spGrid = changeView.findViewById<ZXSpinner>(R.id.sp_change_grid)
            if (longitude != null && latitude != null) {
                etLocation.setText("$longitude,$latitude")
            }
            spStation?.apply {
                showUnderineColor(false)
                setData(stationKeyValues)
                setItemHeightDp(40)
                setItemTextSizeSp(13)
                showSelectedTextColor(true, R.color.map_color)
                build()
            }
            spGrid?.apply {
                showUnderineColor(false)
                setData(arrayListOf())
                setItemHeightDp(40)
                setItemTextSizeSp(13)
                setDefaultItem("请选择..")
                showSelectedTextColor(true, R.color.map_color)
                build()
            }
            spStation?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    mPresenter.getGridList(hashMapOf("parentId" to spStation?.selectedValue.toString(), "type" to when (BuildConfig.APP_HEAD) {
                        "rc" -> "12"
                        "dx" -> "12"
                        else -> "10"
                    }))
                }
            }
            if (stationKeyValues.isEmpty()) {
                mPresenter.getStationList(hashMapOf("dicType" to "10", "parentId" to BuildConfig.AREA_ID))
            }
            ZXDialogUtil.showCustomViewDialog(activity, "", changeView, { _, _ ->
                if (etLocation.text.toString().isEmpty()) {
                    showToast("未获取到当前位置的坐标")
                } else if (spStation?.selectedValue.toString().isEmpty()) {
                    showToast("请选择所在分局")
                } else if (spGrid?.selectedValue.toString().isEmpty()) {
                    showToast("请选择所在片区")
                } else {
                    try {
                        val locationInfo = etLocation.text.toString().split(",")
                        changePoint = Point(locationInfo[0].toDouble(), locationInfo[1].toDouble())
                        mPresenter.changePos(hashMapOf("fEntityGuid" to taskBean?.id, "fLongitude" to locationInfo[0], "fLatitude" to locationInfo[1],
                                "fStation" to spStation?.selectedKey, "fStationCode" to spStation?.selectedValue.toString(),
                                "fGrid" to spGrid?.selectedKey, "fGridCode" to spGrid?.selectedValue.toString()).toJson())
                    } catch (e: Exception) {
                        showToast("坐标信息有误")
                    }
                }
            }, { _, _ -> })
        }
    }

    override fun onStationListResult(stationList: List<DicTypeBean>) {
        val keyValueEntities = ArrayList<KeyValueEntity>()
        if (stationList.isNotEmpty()) {
            stationList.forEach {
                keyValueEntities.add(KeyValueEntity(it.dicName, it.id))
            }
        }
        stationKeyValues.addAll(keyValueEntities)
        spStation?.dataList?.clear()
        spStation?.dataList?.addAll(keyValueEntities)
//        spStation?.setData(keyValueEntities)
        spStation?.notifyDataSetChanged()
    }

    override fun onGridListResult(gridList: List<DeptBean>) {
        val keyValueEntities = ArrayList<KeyValueEntity>()
        if (gridList.isNotEmpty()) {
            gridList.forEach {
                keyValueEntities.add(KeyValueEntity(it.name, it.id))
            }
        }
        spGrid?.dataList?.clear()
        spGrid?.dataList?.addAll(keyValueEntities)
        spGrid?.setDefaultItem("请选择...")
//        spGrid?.setData(keyValueEntities)
        spGrid?.notifyDataSetChanged()
    }

    override fun onChangePosResult() {
        showToast("位置纠正成功")
        mapListener?.addMarker(changePoint!!)
    }
}
