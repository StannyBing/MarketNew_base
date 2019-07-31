package com.zx.module_complain.module.info.ui

import android.content.Intent
import android.os.Bundle
import com.zx.module_complain.R
import com.zx.module_complain.XAppComplain
import com.zx.module_complain.module.info.bean.DetailBean
import com.zx.module_complain.module.info.mvp.contract.FileInfoContract
import com.zx.module_complain.module.info.mvp.model.FileInfoModel
import com.zx.module_complain.module.info.mvp.presenter.FileInfoPresenter
import com.zx.module_library.app.BaseConfigModule
import com.zx.module_library.base.BaseFragment
import com.zx.module_library.bean.FileBean
import com.zx.module_library.view.AddFileView
import kotlinx.android.synthetic.main.fragment_complain_file.*

/**
 * Create By admin On 2017/7/11
 * 功能：投诉举报-附件
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
        return R.layout.fragment_complain_file
    }

    /**
     * 初始化
     */
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        file_view.withXApp(XAppComplain.LIST)
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
