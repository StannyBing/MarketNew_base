package com.zx.module_other.module.documentmanage.func.util

import android.content.Context
import android.database.Cursor
import com.zx.module_other.module.documentmanage.bean.Children
import com.zx.zxutils.util.ZXDataBaseUtil

class DBService {

    companion object {
        val createSqls = listOf<String>("create table printhistory (id integer primary key autoincrement," + "doc_id text,"
                + "doc_name," + "doc_pid," + "doc_status text," + "doc_type text)")
        val tableName = "doc"
        val dbVersion = 1
        var zxDataBaseUtil: ZXDataBaseUtil? = null
        var dbService: DBService? = null

        fun getDBService(): DBService {
            if (dbService == null) {
                zxDataBaseUtil = ZXDataBaseUtil.getInstance(tableName, dbVersion, createSqls)
                dbService = DBService();
            }
            return dbService!!;
        }
    }

    fun insert(children: Children) {
        if (zxDataBaseUtil == null) {
            return
        }
        zxDataBaseUtil!!.execSQL(
                "INSERT INTO printhistory(doc_id,doc_name,doc_pid,doc_status,doc_type)" + "VALUES(?,?,?,?,?)",
                arrayOf<String>(children.id, children.name, children.pId, children.status, children.type))
    }

    fun getMessage(): List<Children> {
        val children = ArrayList<Children>()
        var cursor: Cursor? = null
        cursor = zxDataBaseUtil!!.rawQuery("SELECT * FROM printhistory order by id desc limit 0,30", arrayOf())
        while (cursor!!.moveToNext()) {
            children.add(Children(null,
                    cursor!!.getString(cursor!!.getColumnIndex("doc_id")),
                    cursor!!.getString(cursor!!.getColumnIndex("doc_name")),
                    cursor!!.getString(cursor!!.getColumnIndex("doc_pid")), null,
                    cursor!!.getString(cursor!!.getColumnIndex("doc_status")),
                    cursor!!.getString(cursor!!.getColumnIndex("doc_type")), null))
        }
        return children
    }
}