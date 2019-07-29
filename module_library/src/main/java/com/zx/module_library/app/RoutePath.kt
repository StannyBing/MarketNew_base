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
    const val ROUTE_APP_MYTASK = "/app/mytask"//我的任务

    //投诉举报
    const val ROUTE_COMPLAIN_QUERY = "/complain/query"//投诉举报任务列表
    const val ROUTE_COMPLAIN_DETAIL = "/complain/detail"//投诉举报任务详情
    const val ROUTE_COMPLAIN_DISPOSE = "/complain/dispose"//投诉举报任务处置

    //综合执法
    const val ROUTE_LEGALCASE_QUERY = "/legalcase/query"//综合执法
    const val ROUTE_LEGALCASE_DETAIL = "/legalcase/detail"//综合执法-详情
    const val ROUTE_LEGALCASE_DISPOSENORMAL = "/legalcase/disposenormal"//综合执法-普通处置
    const val ROUTE_LEGALCASE_DISPOSEFORCE = "/legalcase/disposeforce"//综合执法-强制措施
    const val ROUTE_LEGALCASE_DISPOSETRANS = "/legalcase/disposetrans"//综合执法-案件移送
    const val ROUTE_LEGALCASE_DISPOSESIMPLE = "/legalcase/disposesimple"//综合执法-简易流程

    //主体查询
    const val ROUTE_ENTITY_QUERY = "/entity/query"//主体查询
    const val ROUTE_ENTITY_DETAIL = "/entity/detail"//主体查询-详情
    const val ROUTE_ENTITY_SPECIALADD = "/entity/specialAdd"//无证无照监管-上报
    const val ROUTE_ENTITY_SPECIALLIST = "/entity/specialList"//无证无照监管-上报记录

    //专项检查
    const val ROUTE_SUPERVISE_QUERY = "/supervise/query"//专项检查查询
    const val ROUTE_SUPERVISE_DETAIL = "/supervise/detail"//专项检查详情
    const val ROUTE_SUPERVISE_DISPOSE = "/supervise/dispose"//专项检查处置
    const val ROUTE_DAILY_QUERY = "/daily/query"//现场检查
    const val ROUTE_DAILY_ADD = "/daily/add"//现场检查-新增
    const val ROUTE_DAILY_TEMPLATE = "/daily/template"//现场检查-模板
    const val ROUTE_DAILY_CHECKLIST = "/daily/checklist"//现场检查-检查指标

    //统计分析
    const val ROUTE_STATISTICS_COMPLAIN = "/statistics/complain"//统计-投诉举报

    //地图
    const val ROUTE_MAP_MAP = "/map/map"//地图

    //其他模块
    const val ROUTE_OTHER_LAW = "/other/law"//其他-法律法规
    const val ROUTE_OTHER_PRINT = "/other/print"//其他-文件打印
    const val ROUTE_OTHER__BLUETOOTH = "/other/bluetooth"//其他-文件打印
    const val ROUTE_OTHER_WEB = "/other/web"//其他-网页
    const val ROUTE_OTHER_CAMERA = "/other/camera"//其他-图片
    const val ROUTE_OTHER_PREVIEW = "/other/filepreview"//其他-文件预览
    const val ROUTE_OTHER_PLAN = "/other/workplan"//其他-个人工作计划
    const val ROUTE_OTHER_STATISICSACTIVITY = "/other/workstatisics"//其他-工作成果
    const val ROUTE_OTHER_DOCUMENT = "/other/documentmanage"//其他-文书管理
    const val ROUTE_OTHER_DOCUMENT_SEE = "/other/documentsee"//其他-文书管理


}