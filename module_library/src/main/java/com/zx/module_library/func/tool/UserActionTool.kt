package com.zx.module_library.func.tool

import android.app.Activity
import com.frame.zxmvp.baserx.RxHelper
import com.frame.zxmvp.baserx.RxSubscriber
import com.zx.module_library.app.ApiParamUtil
import com.zx.module_library.app.ApiService
import com.zx.module_library.app.MyApplication
import com.zx.zxutils.util.ZXLocationUtil

/**
 * Created by Xiangb on 2019/7/2.
 * 功能：
 */
object UserActionTool {

    enum class ActionType(var type: String, var content: String) {
        Normal("签到", "签到"),
        Supervise_Daily("事件", "日常检查"),
        Supervise_Task("事件", "监管任务"),
        Complain("事件", "投诉举报"),
        CaseLegal("事件", "案件执法");
    }

    fun addUserAction(activity: Activity, actionType: ActionType, id: String) {
        try {
            val location = ZXLocationUtil.getLocation(activity)
            if (location != null && location.longitude > 0 && location.latitude > 0) {
                MyApplication.instance.component.repositoryManager()
                        .obtainRetrofitService(ApiService::class.java)
                        .addUserPath(ApiParamUtil.addUserPath(actionType.type, actionType.content, id, location.longitude.toString(), location.latitude.toString()))
                        .compose(RxHelper.handleResult())
                        .subscribe(object : RxSubscriber<String>() {
                            override fun _onNext(t: String?) {
                            }

                            override fun _onError(code: String?, message: String?) {
                            }

                        })
            }
        } catch (e: Exception) {
        }
    }

}