package com.zx.module_supervise.module.daily.bean

/**
 * Created by Xiangb on 2019/9/2.
 * 功能：
 */
data class DailyQueryBean(var enterpriseId: String,
                          var checkDate: Long?,
                          var enterpriseName: String,
                          var fAddress: String?,
                          var yearNum: Int,
                          var allNum: Int) {
}