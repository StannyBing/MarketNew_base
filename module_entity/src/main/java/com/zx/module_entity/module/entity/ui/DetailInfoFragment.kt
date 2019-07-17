package com.zx.module_entity.module.entity.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zx.module_entity.R
import com.zx.module_entity.XAppEntity
import com.zx.module_entity.api.ApiParamUtil
import com.zx.module_entity.module.entity.bean.DetailInfoBean
import com.zx.module_entity.module.entity.bean.DicTypeBean
import com.zx.module_entity.module.entity.bean.EntityDetailBean
import com.zx.module_entity.module.entity.func.adapter.DetailInfoAdapter
import com.zx.module_entity.module.entity.mvp.contract.DetailInfoContract
import com.zx.module_entity.module.entity.mvp.model.DetailInfoModel
import com.zx.module_entity.module.entity.mvp.presenter.DetailInfoPresenter
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.func.tool.toJson
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import com.zx.zxutils.other.ZXInScrollRecylerManager
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.util.ZXSystemUtil
import com.zx.zxutils.util.ZXTimeUtil
import kotlinx.android.synthetic.main.fragment_entity_detail_info.*

/**
 * Create By admin On 2017/7/11
 * 功能：主体查询-信息
 */
@SuppressLint("NewApi")
class DetailInfoFragment : BaseFragment<DetailInfoPresenter, DetailInfoModel>(), DetailInfoContract.View {

    private val dataList = arrayListOf<DetailInfoBean>()
    private val mAdapter = DetailInfoAdapter(dataList)

    private var detailBean: EntityDetailBean? = null

    private var myTagsList = arrayListOf<DicTypeBean>()
    private var allTagList = arrayListOf<DicTypeBean>()

    private var myTagsAdapter: ZXQuickAdapter<*, *>? = null

    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DetailInfoFragment {
            val fragment = DetailInfoFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_entity_detail_info
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        rv_entity_detailInfo.apply {
            layoutManager = ZXInScrollRecylerManager(activity!!)
            adapter = mAdapter
        }

        rv_entity_mark.apply {
            layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
            adapter = object : ZXQuickAdapter<DicTypeBean, ZXBaseHolder>(R.layout.item_info_tags, myTagsList) {
                override fun convert(helper: ZXBaseHolder?, item: DicTypeBean?) {
                    try {
                        helper?.setBackgroundColor(R.id.ll_mark_icon, if (item?.dicNameAlias == null) Color.WHITE else Color.parseColor(item.dicNameAlias.toString()))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    Glide.with(mContext).load(BaseConfigModule.BASE_IP + if (item?.dicRemark == null) "" else item.dicRemark.toString()).into(helper?.getView<View>(R.id.iv_mark_icon) as ImageView)
                }
            }.apply { myTagsAdapter = this }
        }

        mPresenter.getDicTypeList(hashMapOf("dicType" to "16"))
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        mAdapter.setOnItemClickListener { _, _, position ->
            if (dataList[position].canEdit) {
                modifyInfo(position)
            }
        }
        //主体标识编辑
        ll_entity_mark.setOnClickListener {
            modifyMark()
        }
    }

    //主体标识修改
    private fun modifyMark() {
        val markView = LayoutInflater.from(activity).inflate(R.layout.layout_info_mark, null, false)
        val rvMark = markView.findViewById<RecyclerView>(R.id.rv_dialog_mark)
        rvMark.apply {
            layoutManager = GridLayoutManager(activity!!, 3)
            adapter = object : ZXQuickAdapter<DicTypeBean, ZXBaseHolder>(R.layout.item_info_mark, allTagList) {
                override fun convert(helper: ZXBaseHolder?, item: DicTypeBean?) {
                    helper?.setText(R.id.tv_mark_name, item?.dicName)
                    helper?.setBackgroundColor(R.id.ll_mark_icon, if (item?.dicNameAlias == null) Color.WHITE else Color.parseColor(item.dicNameAlias.toString()))
                    Glide.with(mContext).load(BaseConfigModule.BASE_IP + if (item?.dicRemark == null) "" else item.dicRemark.toString()).into(helper?.getView<View>(R.id.iv_mark_icon) as ImageView)
                    if (item!!.select) {
                        helper.setBackgroundRes(R.id.iv_mark_select, R.mipmap.select)
                    } else {
                        helper.setBackgroundRes(R.id.iv_mark_select, R.mipmap.not_select)
                    }
                }
            }.apply {
                setOnItemClickListener { _, _, position ->
                    allTagList.get(position).select = !allTagList.get(position).select
                    this.notifyItemChanged(position)
                }
            }
        }
        val dialog = ZXDialogUtil.showCustomViewDialog(activity, "", markView, { _, _ ->
            var tags = ""
            if (allTagList.size > 0) {
                allTagList.forEach {
                    if (it.select) {
                        tags += "," + it.dicName
                    }
                }
            }
            if (tags.isNotEmpty()) {
                tags = tags.substring(1)
            }
            mPresenter.modifyInfo(hashMapOf("fEntityGuid" to detailBean?.fEntityGuid, "fTags" to tags).toJson())
        }, { _, _ -> })
        val params = dialog.window.attributes
        params.width = ZXSystemUtil.dp2px(350f)
        dialog.window.attributes = params
    }

    //信息修改
    private fun modifyInfo(position: Int) {
        val modifyView = LayoutInflater.from(activity).inflate(R.layout.layout_entity_modify, null, false)
        val modifyName = modifyView.findViewById<TextView>(R.id.tv_modify_name)
        val modifyValue = modifyView.findViewById<EditText>(R.id.et_modify_value)
        try {
            val cursorFiled = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            cursorFiled.isAccessible = true
            val drawable = ContextCompat.getDrawable(mContext, R.drawable.shape_search_cursor)
            drawable!!.setTint(ContextCompat.getColor(activity!!, XAppEntity.get("主体查询")!!.moduleColor))
            cursorFiled.set(modifyValue, drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        modifyName.text = dataList[position].name
        when (dataList[position].name) {
            "联系姓名" -> {
                modifyValue.setText(detailBean?.fContactPeople)
            }
            "联系电话" -> {
                modifyValue.setText(detailBean?.fContactPhone)
            }
            "联系地址" -> {
                modifyValue.setText(detailBean?.fContactAddress)
            }
        }
        modifyValue.setSelection(modifyValue.text.length)
        ZXDialogUtil.showCustomViewDialog(activity, "主体信息修改", modifyView, { _, _ ->
            dataList[position].value = modifyValue.text.toString()
            mAdapter.notifyItemChanged(position)
            mPresenter.modifyInfo(ApiParamUtil.infoModifyParam(detailBean!!.fEntityGuid!!,
                    if (dataList[position].name == "联系姓名") modifyValue.text.toString() else "",
                    if (dataList[position].name == "联系电话") modifyValue.text.toString() else "",
                    if (dataList[position].name == "联系地址") modifyValue.text.toString() else ""))
        }, { _, _ -> }, true)
    }

    //信息修改完成
    override fun onInfoModifyResult() {
        showToast("修改成功")
    }

    override fun onDicListResult(dicTypeBeans: List<DicTypeBean>) {
        allTagList.addAll(dicTypeBeans)
    }

    fun resetInfo(entityDetail: EntityDetailBean) {
        detailBean = entityDetail
        if (entityDetail.fType != null) {
            dataList.add(DetailInfoBean("主体名称", entityDetail.fEntityName))
            dataList.add(DetailInfoBean("特殊主体类型", entityDetail.fType))
            dataList.add(DetailInfoBean("营业执照号", entityDetail.fBizlicNum))
            dataList.add(DetailInfoBean("注册时间", if (entityDetail.fInsertDate == 0L || entityDetail.fInsertDate == null) "" else ZXTimeUtil.getTime(entityDetail.fInsertDate!!)))
            dataList.add(DetailInfoBean("法定代表人（负责人）", entityDetail.fLegalPerson))
            dataList.add(DetailInfoBean("所属区域", (entityDetail.fStation ?: "") + "-" + (entityDetail.fGrid ?: "")))
            dataList.add(DetailInfoBean("主体地址", entityDetail.fAddress))
            dataList.add(DetailInfoBean("联系信息", entityDetail.fContactPhone))
            dataList.add(DetailInfoBean("企业标识", entityDetail.fTags))
        } else {
            dataList.add(DetailInfoBean("主体名称", entityDetail.fEntityName))
            dataList.add(DetailInfoBean("营业执照号", entityDetail.fBizlicNum))
            dataList.add(DetailInfoBean("统一社会信用代码", entityDetail.fUniscid))
            dataList.add(DetailInfoBean("行业类型", entityDetail.fIndustry))
            dataList.add(DetailInfoBean("经营范围", entityDetail.fBizScope))
            dataList.add(DetailInfoBean("法定代表人（负责人）", entityDetail.fLegalPerson))
            dataList.add(DetailInfoBean("所属分局/片区", (entityDetail.fStation ?: "") + "-" + (entityDetail.fGrid ?: "")))
            dataList.add(DetailInfoBean("注册地址", entityDetail.fAddress))
            if (entityDetail.fCreditLevel != "D" && entityDetail.fCreditLevel != "Z") {
                dataList.add(DetailInfoBean("联系姓名", entityDetail.fContactPeople, true))
                dataList.add(DetailInfoBean("联系电话", entityDetail.fContactPhone, true))
                dataList.add(DetailInfoBean("联系地址", entityDetail.fContactAddress, true))
            }
        }
        mAdapter.notifyDataSetChanged()

        myTagsList.clear()
        if (allTagList.isNotEmpty() && detailBean?.fTags != null) {
            allTagList.forEach {
                if (detailBean?.fTags!!.contains(it.dicName)) {
                    it.select = true
                    myTagsList.add(it)
                } else {
                    it.select = false
                }
            }
        }
        myTagsAdapter?.notifyDataSetChanged()
    }
}
