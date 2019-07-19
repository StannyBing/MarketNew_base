package com.zx.module_legalcase.module.query.ui

import android.content.Intent
import android.os.Bundle
import com.zx.module_legalcase.R
import com.zx.module_legalcase.XAppLegalcase
import com.zx.module_legalcase.module.query.bean.DetailBean
import com.zx.module_legalcase.module.query.mvp.contract.FileInfoContract
import com.zx.module_legalcase.module.query.mvp.model.FileInfoModel
import com.zx.module_legalcase.module.query.mvp.presenter.FileInfoPresenter
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.FileBean
import com.zx.module_library.view.AddFileView
import kotlinx.android.synthetic.main.fragment_file_info.*

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
class FileInfoFragment : BaseFragment<FileInfoPresenter, FileInfoModel>(), FileInfoContract.View {
    companion object {
        /**
         * 启动器
         */
        fun newInstance(): FileInfoFragment {
            val fragment = FileInfoFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * layout配置
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_file_info
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        file_view.withXApp(XAppLegalcase.get("综合执法"))
                .setAddType(AddFileView.AddType.NORMAL)
    }

    /**
     * View事件设置
     */
    override fun onViewListener() {

    }


    fun reSetInfo(filesList: List<DetailBean.FileBean>?) {
        if (filesList != null && filesList.isNotEmpty()) {
            val files = arrayListOf<FileBean>()
            filesList.forEach {
                files.add(FileBean(it.id, it.name, BaseConfigModule.BASE_IP + it.url, dateLong = it.updateDate))
            }
            file_view.setDatas(files)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        file_view.onActivityResult(requestCode, resultCode, data)
    }
}
