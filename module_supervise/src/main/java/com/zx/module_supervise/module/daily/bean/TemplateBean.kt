package com.zx.module_supervise.module.daily.bean

/**
 * Created by dell on 2019-07-24
 */
data class TemplateBean(var id: String,
                        var templateName: String,
                        var updateUserId: String,
                        var updateUser: String,
                        var updateDate: Long,
                        var department: String,
                        var departmentCode: String,
                        var templateType: Int)