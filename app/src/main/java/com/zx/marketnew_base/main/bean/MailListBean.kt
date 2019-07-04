package com.zx.marketnew_base.main.bean

/**
 * Created by Xiangb on 2019/6/17.
 * 功能：
 */
data class MailListBean(var imgUrl: String?,
                        var realName: String,
                        var departmentCode: String?,
                        var telephone: String?,
                        var remark: String?,
                        var id: String,
                        var department: String?,
                        var children: List<MailListBean>)