package com.zx.module_supervise.module.daily.bean

/**
 * Created by Xiangb on 2019/7/23.
 * 功能：
 */
data class DailyListBean(var id: String,
                         var enterpriseId: String,
                         var enterpriseName: String,
                         var name: String,
                         var checkDate: Long?,
                         var checkPlace: String,
                         var checkPerson: String,
                         var enterpriseSign: String,
                         var enterpriseSignDate: String,
                         var checkSign: String,
                         var checkSignDate: String,
                         var remark: String,
                         var insertDate: Long?,
                         var insertPerson: String,
                         var status: Int,
                         var illegal: String,
                         var result: String) {

}