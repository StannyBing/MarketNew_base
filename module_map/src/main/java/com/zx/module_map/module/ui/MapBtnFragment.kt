package com.zx.module_map.module.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.MapTaskBean
import com.zx.module_map.R
import com.zx.module_map.XAppMap
import com.zx.module_map.module.bean.MapBtnBean
import com.zx.module_map.module.func.adapter.BtnAdapter
import com.zx.module_map.module.func.listener.MapListener
import com.zx.module_map.module.func.tool.NaviTool
import com.zx.module_map.module.mvp.contract.MapBtnContract
import com.zx.module_map.module.mvp.model.MapBtnModel
import com.zx.module_map.module.mvp.presenter.MapBtnPresenter
import com.zx.zxutils.views.BubbleView.ZXBubbleView
import kotlinx.android.synthetic.main.fragment_map_btn.*

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class MapBtnFragment : BaseFragment<MapBtnPresenter, MapBtnModel>(), MapBtnContract.View {

    var mapListener : MapListener?=null
    private val btnList = arrayListOf<MapBtnBean>()
    private val btnAdapter = BtnAdapter(btnList)

    private var layerBubble : ZXBubbleView?=null

    private var taskBean : MapTaskBean?=null

    companion object {
        /**
         * 启动器
         */
        fun newInstance(taskBean : MapTaskBean?): MapBtnFragment {
            val fragment = MapBtnFragment()
            val bundle = Bundle()
            bundle.putSerializable("taskBean", taskBean)
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

        taskBean = arguments?.getSerializable("taskBean") as MapTaskBean?

        btnAdapter.setColor(ContextCompat.getColor(activity!!,XAppMap.get("地图")!!.moduleColor))
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
        if (taskBean!=null){
            btnAdapter.addData(MapBtnBean("导航", R.drawable.btn_navi))
        }
    }

    //初始化图层弹窗
    private fun initLayerBubble() {
        val layerView = LayoutInflater.from(activity).inflate(R.layout.layout_layer_view, null,false)
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
            when(btnList[position].name){
                "图层"->{
                    layerBubble?.show(view, Gravity.LEFT)
                }
                "定位"->{
                    mapListener?.doLocation()
                }
                "导航"->{
                    NaviTool.openNavi(activity!!,taskBean)
                }
            }
        }
    }
}
