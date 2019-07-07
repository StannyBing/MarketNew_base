package com.zx.module_map.module.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.esri.core.geometry.GeometryEngine
import com.esri.core.geometry.Point
import com.esri.core.geometry.SpatialReference
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.MapTaskBean
import com.zx.module_map.R
import com.zx.module_map.XAppMap
import com.zx.module_map.module.func.listener.MapListener
import com.zx.module_map.module.mvp.contract.MapTaskContract
import com.zx.module_map.module.mvp.model.MapTaskModel
import com.zx.module_map.module.mvp.presenter.MapTaskPresenter
import kotlinx.android.synthetic.main.fragment_map_task.*

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
@SuppressLint("NewApi")
class MapTaskFragment : BaseFragment<MapTaskPresenter, MapTaskModel>(), MapTaskContract.View {
    var mapListener : MapListener?=null
    private var taskBean : MapTaskBean?=null

    companion object {
        /**
         * 启动器
         */
        fun newInstance(taskBean : MapTaskBean?): MapTaskFragment {
            val fragment = MapTaskFragment()
            val bundle = Bundle()
            bundle.putSerializable("taskBean",taskBean)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_map_task
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        taskBean = arguments?.getSerializable("taskBean") as MapTaskBean?
        if (taskBean!=null){
            rl_task.visibility = View.VISIBLE
            tv_task_type.text = taskBean?.typeName
            val typeDrawable = ContextCompat.getDrawable(activity!!, taskBean!!.typeIcon)
            typeDrawable?.setTint(ContextCompat.getColor(activity!!,XAppMap.get("地图")!!.moduleColor))
            iv_task_type.setImageDrawable(typeDrawable)
            tv_task_name.text = taskBean?.name
            tv_task_name.setTextColor(ContextCompat.getColor(activity!!, XAppMap.get("地图")!!.moduleColor))
            tv_task_entity.text = "涉及主体：${taskBean?.entity}"
            tv_task_address.text = "主体地址：${taskBean?.address}"
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        rl_task.setOnClickListener {
            var point = Point(taskBean!!.longtitude!!,taskBean!!.latitude!!)
            point = GeometryEngine.project(point, SpatialReference.create(4326), mapListener?.getMap()?.spatialReference) as Point
            mapListener?.getMap()?.centerAt(point, true)
            mapListener?.getMap()?.scale = 70000.00
        }
    }
}
