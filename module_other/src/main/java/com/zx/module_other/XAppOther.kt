package com.zx.module_other

import com.zx.module_library.XApp
import com.zx.module_library.app.RoutePath
import com.zx.module_library.bean.XAppBean

/**
 * Created by Xiangb on 2019/3/13.
 * 功能：
 */
object XAppOther : XApp() {

    val LAW = XAppBean("法律法规", R.color.law_color, R.drawable.icon_law, RoutePath.ROUTE_OTHER_LAW)
    val PRINT = XAppBean("文件打印", R.color.print_color, R.drawable.icon_print, RoutePath.ROUTE_OTHER_PRINT)
    val DOCUMENT = XAppBean("文书管理", R.color.document_color, R.drawable.icon_docment, RoutePath.ROUTE_OTHER_DOCUMENT)
    val INFOMATION = XAppBean("政务资讯", R.color.infomation_color, R.drawable.icon_infomation, RoutePath.ROUTE_OTHER_INFOMATION)
    val APPROVAL = XAppBean("审批", R.color.approval_color, R.drawable.icon_approval, RoutePath.ROUTE_OTHER_APPROVAL)
    val CHECKIN = XAppBean("考勤打卡", R.color.checkin_color, R.drawable.icon_checkin, RoutePath.ROUTE_OTHER_CHECKIN)
    val PAPERS = XAppBean("公文流转", R.color.papers_color, R.drawable.icon_papers, RoutePath.ROUTE_OTHER_PAPERS)

    val RESULTS = XAppBean("工作成果", R.color.results_color, R.drawable.icon_results, RoutePath.ROUTE_OTHER_RESULTS)
    val PLAN = XAppBean("个人工作计划", R.color.plan_color, R.drawable.icon_plan, RoutePath.ROUTE_OTHER_PLAN)
    override fun all() = arrayListOf(LAW, PRINT, DOCUMENT, INFOMATION, APPROVAL, CHECKIN, PAPERS,RESULTS,PLAN)
}