package com.zx.module_library.app

/**
 * Created by Xiangb on 2019/3/5.
 * 功能：route路径
 */
object RoutePath {

    //主应用
    const val ROUTE_APP_SPLASH = "/app/splash"//欢迎页
    const val ROUTE_APP_LOGIN = "/app/login"//登录页
    const val ROUTE_APP_FORGETPWD = "/app/forgetpwd"//忘记密码
    const val ROUTE_APP_CHANGEPWD = "/app/changepwd"//修改密码
    const val ROUTE_APP_MAIN = "/app/main"//主页
    const val ROUTE_APP_SETTING = "/app/setting"//设置
    const val ROUTE_APP_SETTING_VIDEO = "/app/setting/video"//设置-录像
    const val ROUTE_APP_SETTING_FEEDBACK = "app/setting/feedback"//设置-意见反馈
    const val ROUTE_APP_MYTASK = "/app/mytask"//我的任务



    //投诉举报
    const val ROUTE_COMPLAIN_TASK = "/complain/task"//投诉举报任务列表
    const val ROUTE_COMPLAIN_TASK_DETAIL = "/complain/task/detail"//投诉举报任务详情
    const val ROUTE_COMPLAIN_TASK_DISPOSE = "/complain/task/dispose"//投诉举报任务处置



    //综合执法
    const val ROUTE_LEGALCASE_TASK = "/legalcase/query"//综合执法
    const val ROUTE_LEGALCASE_TASK_DETAIL = "/legalcase/task/detail"//综合执法-详情
    const val ROUTE_LEGALCASE_TASK_DISPOSENORMAL = "/legalcase/task/disposenormal"//综合执法-普通处置
    const val ROUTE_LEGALCASE_TASK_DISPOSEFORCE = "/legalcase/task/disposeforce"//综合执法-强制措施
    const val ROUTE_LEGALCASE_TASK_DISPOSETRANS = "/legalcase/task/disposetrans"//综合执法-案件移送
    const val ROUTE_LEGALCASE_TASK_DISPOSESIMPLE = "/legalcase/task/disposesimple"//综合执法-简易流程



    //主体查询
    const val ROUTE_ENTITY_QUERY = "/entity/query"//主体查询
    const val ROUTE_ENTITY_QUERY_DETAIL = "/entity/query/detail"//主体查询-详情
    const val ROUTE_ENTITY_SPECIAL_ADD = "/entity/special/add"//无证无照监管-上报
    const val ROUTE_ENTITY_SPECIAL_LIST = "/entity/special/list"//无证无照监管-上报记录



    //专项检查
    const val ROUTE_SUPERVISE_TASK = "/supervise/task"//专项检查查询
    const val ROUTE_SUPERVISE_TASK_DETAIL = "/supervise/task/detail"//专项检查详情
    const val ROUTE_SUPERVISE_TASK_DISPOSE = "/supervise/task/dispose"//专项检查处置
    const val ROUTE_SUPERVISE_DAILY = "/supervise/daily"//现场检查
    const val ROUTE_SUPERVISE_DAILY_ADD = "/supervise/daily/add"//现场检查-新增
    const val ROUTE_SUPERVISE_DAILY_TEMPLATE = "/supervise/daily/template"//现场检查-模板
    const val ROUTE_SUPERVISE_DAILY_CHECKLIST = "/supervise/daily/checklist"//现场检查-检查指标
    const val ROUTE_SUPERVISE_MANAGE_EQUIPMENT = "/supervise/manager/equipment"//特种设备监管
    const val ROUTE_SUPERVISE_MANAGE_DRUGS = "/supervise/manager/drugs"//药品安全监管
    const val ROUTE_SUPERVISE_MANAGE_FOODS = "/supervise/manager/foods"//食品安全监管



    //统计分析
    const val ROUTE_STATISTICS_COMPLAIN = "/statistics/complain"//统计-投诉举报



    //地图
    const val ROUTE_MAP_MAP = "/map/map"//地图



    //其他模块
    const val ROUTE_OTHER_LAW = "/other/law"//其他-法律法规
    const val ROUTE_OTHER_LAW_COLLECT = "/other/law/collect"//其他-法律法规
    const val ROUTE_OTHER_LAW_DETAIL = "/other/law/detail"//其他-法律法规
    const val ROUTE_OTHER_LAW_STANDARDQUERY = "/other/law/standardquery"//其他-法律法规
    const val ROUTE_OTHER_LAW_STANDARDDETAIL = "/other/law/standarddetail"//其他-法律法规

    const val ROUTE_OTHER_PRINT = "/other/print"//其他-文件打印
    const val ROUTE_OTHER_PRINT_BLUETOOTH = "/other/print/bluetooth"//其他-蓝牙管理
    const val ROUTE_OTHER_PRINT_CHOICEFILE = "/other/print/choiceFile"//其他-打印文件选择
    const val ROUTE_OTHER_PRINT_STRATPRINT = "/other/print/startPrint"//其他-打印页面

    const val ROUTE_OTHER_PLAN = "/other/workplan"//其他-个人工作计划
    const val ROUTE_OTHER_PLAN_CREATE = "/other/workplan/create"//其他-新增个人工作计划

    const val ROUTE_OTHER_RESULTS = "/other/results"//其他-工作成果

    const val ROUTE_OTHER_DOCUMENT = "/other/documentmanage"//其他-文书管理
    const val ROUTE_OTHER_DOCUMENT_FIll = "/other/document/fill"//其他-文书填写
    const val ROUTE_OTHER_DOCUMENT_PREVIEW = "/other/document/preview"//其他-文书预览

    const val ROUTE_OTHER_INFOMATION = "/other/infomation"//其他-政务资讯

    const val ROUTE_OTHER_APPROVAL = "/other/approval"//其他-审批

    const val ROUTE_OTHER_CHECKIN = "/other/checkin"//其他-考勤打卡

    const val ROUTE_OTHER_PAPERS = "/other/papers"//其他-公文流转



    //基础功能
    const val ROUTE_LIBRARY_WEB = "/library/web"//其他-网页
    const val ROUTE_LIBRARY_CAMERA = "/library/camera"//其他-图片
    const val ROUTE_LIBRARY_PREVIEW = "/library/filepreview"//其他-文件预览

}