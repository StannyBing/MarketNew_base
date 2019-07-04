package com.zx.module_library.base

import android.os.Bundle
import android.os.Handler
import com.frame.zxmvp.base.BaseModel
import com.frame.zxmvp.base.BasePresenter
import com.frame.zxmvp.base.RxBaseFragment
import com.zx.zxutils.util.ZXSharedPrefUtil
import com.zx.zxutils.util.ZXToastUtil

/**
 * Created by Xiangb on 2019/2/26.
 * 功能：
 */
abstract class BaseFragment<T : BasePresenter<*, *>, E : BaseModel> : RxBaseFragment<T, E>() {

    var mSharedPrefUtil = ZXSharedPrefUtil()
    var handler = Handler()

    override fun handleError(code: String?, message: String) {
        (activity as BaseActivity<*, *>).handleError(code, message)
    }

    override fun showLoading(message: String) {
        (activity as BaseActivity<*, *>).showLoading(message)
        //        ZXDialogUtil.showLoadingDialog(getActivity(), message);
    }

    override fun dismissLoading() {
        (activity as BaseActivity<*, *>).dismissLoading()
        //        ZXDialogUtil.dismissLoadingDialog();
    }

    override fun showLoading(message: String, progress: Int) {
        (activity as BaseActivity<*, *>).showLoading(message, progress)
    }

    override fun showToast(message: String) {
        ZXToastUtil.showToast(message)
    }

    fun getPermission(permissionArray: Array<String>, permessionBack: () -> Unit) {
        (activity as BaseActivity<*, *>).getPermission(permissionArray, permessionBack)
    }

    override fun initView(savedInstanceState: Bundle?) {
        onViewListener()
    }

    abstract fun onViewListener()

}