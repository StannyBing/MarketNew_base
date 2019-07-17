package com.zx.module_entity.module.entity.ui

import android.content.Intent
import android.os.Bundle
import com.zx.module_entity.R
import com.zx.module_entity.XAppEntity
import com.zx.module_entity.module.entity.bean.EntityDetailBean
import com.zx.module_entity.module.entity.mvp.contract.DetailImageContract
import com.zx.module_entity.module.entity.mvp.model.DetailImageModel
import com.zx.module_entity.module.entity.mvp.presenter.DetailImagePresenter
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.FileBean
import com.zx.module_library.func.listener.FileFuncListener
import com.zx.module_library.view.AddFileView
import kotlinx.android.synthetic.main.fragment_entity_detail_image.*

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class DetailImageFragment : BaseFragment<DetailImagePresenter, DetailImageModel>(), DetailImageContract.View {
    companion object {
        /**
         * 启动器
         */
        fun newInstance(): DetailImageFragment {
            val fragment = DetailImageFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_entity_detail_image
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        file_view.withXApp(XAppEntity.get("主体查询"))
                .setModifiable()
                .setAddType(AddFileView.AddType.NORMAL)
                .setFileListener(object : FileFuncListener {
                    override fun onFileDelete(fileBean: FileBean, position: Int) {

                    }
                })
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }

    fun resetInfo(entityDetail: EntityDetailBean) {
        if (entityDetail.fFile != null && entityDetail.fFile!!.isNotEmpty()) {
            val files = arrayListOf<FileBean>()
            entityDetail.fFile!!.forEach {
                files.add(FileBean(it.id, it.realName, BaseConfigModule.BASE_IP + it.savePath, dateLong = it.saveDate))
            }
            file_view.setDatas(files)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        file_view.onActivityResult(requestCode, resultCode, data)
    }
}
