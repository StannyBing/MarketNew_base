package com.zx.module_supervise.module.daily.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.module_library.app.RoutePath
import com.zx.module_library.base.BaseActivity
import com.zx.module_library.bean.KeyValueBean
import com.zx.module_library.func.tool.UserManager
import com.zx.module_library.func.tool.toJson
import com.zx.module_supervise.R
import com.zx.module_supervise.XAppSupervise
import com.zx.module_supervise.module.daily.func.view.ExpandableView.CheckExpandBean
import com.zx.module_supervise.module.daily.func.view.ExpandableView.CheckExpandItemListener
import com.zx.module_supervise.module.daily.func.view.ExpandableView.CheckRecyclerHelper
import com.zx.module_supervise.module.daily.mvp.contract.CheckListContract
import com.zx.module_supervise.module.daily.mvp.model.CheckListModel
import com.zx.module_supervise.module.daily.mvp.presenter.CheckListPresenter
import com.zx.module_supervise.module.supervise.bean.SuperviseCheckBean
import com.zx.zxutils.entity.KeyValueEntity
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter
import com.zx.zxutils.util.ZXDialogUtil
import com.zx.zxutils.views.ZXSpinner
import kotlinx.android.synthetic.main.activity_check_list.*
import java.util.*


/**
 * Create By admin On 2017/7/11
 * 功能：检查模板-检查项
 */
@Route(path = RoutePath.ROUTE_DAILY_CHECKLIST)
class CheckListActivity : BaseActivity<CheckListPresenter, CheckListModel>(), CheckListContract.View {

    private val checkList = ArrayList<CheckExpandBean>()
    private lateinit var checkRecyclerHelper: CheckRecyclerHelper

    private val selectCheckMap = linkedMapOf<String, String>()

    companion object {
        /**
         * 启动器
         */
        fun startAction(activity: Activity, isFinish: Boolean, templateId: String, templateName: String) {
            val intent = Intent(activity, CheckListActivity::class.java)
            intent.putExtra("templateId", templateId)
            intent.putExtra("templateName", templateName)
            activity.startActivityForResult(intent, 0x01)
            if (isFinish) activity.finish()
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_check_list
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        toolBar_view.withXApp(XAppSupervise.DAILY)
        search_view.withXApp(XAppSupervise.DAILY)

        checkRecyclerHelper = CheckRecyclerHelper.getInstance(this)
        checkRecyclerHelper.apply {
            withRecycler(rv_check_list)
            showSelect(true, true)
            setData(checkList)
            setItemClickListener(object : CheckExpandItemListener {
                override fun onItemClick(checkExpandBean: CheckExpandBean, position: Int) {
                    checkRecyclerHelper.changeOpenStatus(position)
                    val item = checkExpandBean.getCustomData() as SuperviseCheckBean
                    if (checkExpandBean.childList != null && checkExpandBean.childList.isEmpty() && checkExpandBean.isShowChild) {
                        mPresenter.getCheckList(hashMapOf("pId" to item.fId), item.fId)
                    }
                }

                override fun onItemSelect(checkExpandBean: CheckExpandBean, position: Int) {
                    if (checkExpandBean.childList == null) {
                        getSelectItemMap(checkExpandBean)
                    } else if (!checkExpandBean.childList.isEmpty()) {
                        for (bean in checkExpandBean.childList) {
                            getSelectItemMap(bean)
                        }
                    } else {
                        checkExpandBean.isSelected = false
                        onItemClick(checkExpandBean, position)
                    }
                }

            })
            build()
        }

        if (intent.getStringExtra("templateId").isNotEmpty()) {
            mPresenter.getTempletCheckList(hashMapOf("id" to intent.getStringExtra("templateId")))
        } else {
            mPresenter.getCheckList(hashMapOf("pId" to ""), "")
        }
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {
        //搜索事件
        search_view.setSearchListener {
            if (it.isNotEmpty()) {
                mPresenter.queryItemList(hashMapOf("fName" to it, "fIsLeaf" to "0"))
            } else {
                mPresenter.getCheckList(hashMapOf("pId" to it), "")
            }
        }
        //保存事件
        toolBar_view.setRightClickListener {
            val saveView = LayoutInflater.from(this).inflate(R.layout.layout_check_save, null, false)
            val rvSelect = saveView.findViewById<RecyclerView>(R.id.rv_check_select)
            val llTemplateType = saveView.findViewById<LinearLayout>(R.id.ll_template_type)
            val spTemplateType = saveView.findViewById<ZXSpinner>(R.id.sp_template_type)
            val etTempalteName = saveView.findViewById<EditText>(R.id.et_template_name)
            if (!UserManager.getUser().role.contains("0000")) {
                llTemplateType.visibility = View.GONE
            } else {
                llTemplateType.visibility = View.VISIBLE
                spTemplateType.apply {
                    showUnderineColor(false)
                    setData(arrayListOf<KeyValueEntity>().apply {
                        add(KeyValueEntity("个人", "2"))
                        add(KeyValueEntity("公共", "1"))
                    })
                    setSelection(0)
                    setItemHeightDp(40)
                    setItemTextSizeSp(15)
                    showSelectedTextColor(true, XAppSupervise.DAILY.moduleColor)
                    build()
                }
            }
            val datas = ArrayList<KeyValueBean>()
            if (selectCheckMap.size > 0) {
                for (key in selectCheckMap.keys) {
                    datas.add(KeyValueBean(key, selectCheckMap[key]))
                }
            }
            etTempalteName.setText(intent.getStringExtra("templateName"))
            rvSelect.apply {
                layoutManager = LinearLayoutManager(this@CheckListActivity)
                adapter = object : ZXQuickAdapter<KeyValueBean, ZXBaseHolder>(R.layout.item_daily_check_select, datas) {
                    override fun convert(helper: ZXBaseHolder?, item: KeyValueBean?) {
                        if (helper != null && item != null) {
                            helper.setText(R.id.tv_daily_select_name, item.value.toString())
                            helper.getView<View>(R.id.iv_daily_select_delete).setOnClickListener {
                                datas.removeAt(helper.adapterPosition)
                                notifyDataSetChanged()
                                selectCheckMap.remove(item.key)
                                setSelect(checkList)
                                checkRecyclerHelper.setData(checkList).notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
            ZXDialogUtil.showCustomViewDialog(this, "模板保存", saveView, { _, _ ->
                if (etTempalteName.text.toString().isEmpty()) {
                    showToast("模板名称不能为空")
                } else {
                    mPresenter.saveTemplet(hashMapOf("templateName" to etTempalteName.text.toString(),
                            "checkItemIdList" to getSelectIds(),
                            "templateType" to if (llTemplateType.visibility == View.VISIBLE) {
                                spTemplateType.selectedValue.toString()
                            } else {
                                "2"
                            }).apply {
                        if (intent.getStringExtra("templateId").isNotEmpty()) {
                            put("templateId", intent.getStringExtra("templateId"))
                        }
                    }.toJson(), etTempalteName.text.toString())
                }
            }, { _, _ -> })
        }
    }

    //普通检查列表查询
    override fun onCheckListResult(checkDetailBeans: List<SuperviseCheckBean>, pid: String) {
        if (pid.isEmpty() || checkList.isEmpty()) {
            checkList.clear()
            checkList.addAll(getExpandBean(checkDetailBeans, false))
        } else {
            putChildData(checkList, checkDetailBeans, pid)
        }
        setSelect(checkList)
        checkRecyclerHelper.setData(checkList).notifyDataSetChanged()
    }

    //模板的检查列表
    override fun onTempletCheckListResult(checkDetailBeans: List<SuperviseCheckBean>) {
        reSetTempletCheckList(checkDetailBeans, "")
        checkRecyclerHelper.setData(checkList).notifyDataSetChanged()
    }

    //模板保存成功
    override fun onSaveTempletResult(id: String, templateName: String) {
        val intent = Intent()
        intent.putExtra("templateId", id)
        intent.putExtra("templateName", templateName)
        setResult(0x01, intent)
        finish()
    }

    private fun getSelectIds(): List<String> {
        val ids = ArrayList<String>()
        if (selectCheckMap.size > 0) {
            ids.addAll(selectCheckMap.keys)
        }
        return ids
    }

    private fun reSetTempletCheckList(checkDetailBeans: List<SuperviseCheckBean>, id: String) {
        if (id.isEmpty()) {
            checkList.addAll(getExpandBean(checkDetailBeans, true))
        } else {
            putChildData(checkList, checkDetailBeans, id)
        }
        checkDetailBeans.forEach {
            if (it.children != null && it.children!!.isNotEmpty()) {
                reSetTempletCheckList(it.children!!, it.fId)
            }
        }
    }

    /**
     * 将List<SuperviseCheckDetailBean>转化为List<CheckExpandBean>
     *
     * @param putList
     * @param selected
     * @return </CheckExpandBean></SuperviseCheckDetailBean>
     */
    private fun getExpandBean(putList: List<SuperviseCheckBean>, selected: Boolean): List<CheckExpandBean> {
        val checkList = ArrayList<CheckExpandBean>()
        putList.forEach {
            val checkExpandBean = CheckExpandBean(it, it.fName)
            if ("0" == it.fIsLeaf) {//叶子结点
                checkExpandBean.childList = null
            } else {
                //                checkExpandBean.setChildList(getExpandBean(putList.get(i).getChildren(), selected));
                checkExpandBean.childList = ArrayList<CheckExpandBean>()
            }
            checkExpandBean.isSelected = selected
            if (selected && checkExpandBean.childList == null) {
                getSelectItemMap(checkExpandBean)
            }
            checkList.add(checkExpandBean)
        }
        return checkList
    }

    /**
     * 循环查询获取id
     */
    private fun getSelectItemMap(checkExpandBean: CheckExpandBean) {
        if (checkExpandBean.isSelected) {
            selectCheckMap.put((checkExpandBean.customData as SuperviseCheckBean).fId, checkExpandBean.itemText)
        } else if (selectCheckMap.containsKey((checkExpandBean.customData as SuperviseCheckBean).fId)) {
            selectCheckMap.remove((checkExpandBean.customData as SuperviseCheckBean).fId)
        }
    }

    /**
     * 循环查找id并设置子集
     *
     * @param checkList
     * @param putList
     * @param pid
     */
    private fun putChildData(checkList: List<CheckExpandBean>, putList: List<SuperviseCheckBean>, pid: String) {
        checkList.forEach {
            if (it.childList == null) {

            } else if (it.childList.isNotEmpty()) {
                putChildData(it.childList, putList, pid)
            } else {
                val item = it.customData as SuperviseCheckBean
                if (pid == item.fId) {
                    val tempList = getExpandBean(putList, it.isSelected)
                    it.childList = tempList
                    return
                }
            }
        }
    }

    private fun setSelect(checkList: List<CheckExpandBean>) {
        if (checkList.isNotEmpty()) {
            checkList.forEach {
                if (it.childList == null) {
                    it.isSelected = selectCheckMap.containsKey((it.customData as SuperviseCheckBean).fId)
                } else {
                    setSelect(it.childList)
                }
            }
        }
    }

}
