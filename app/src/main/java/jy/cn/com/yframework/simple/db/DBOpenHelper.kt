package jy.cn.com.yframework.simple.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import jy.cn.com.yframework.simple.db.bean.TestInfo
import jy.cn.com.ylibrary.db.BaseOpenHelper
import jy.cn.com.ylibrary.db.DBFieldManager
import jy.cn.com.ylibrary.util.YLogUtil

/**
 * @Author Administrator
 * @Date 2019/10/23-18:12
 * @TODO 扩展数据库、创建|更新表
 */
class DBOpenHelper internal constructor(context: Context) : BaseOpenHelper(context, VERSION) {


    override fun onCreateDB(db: SQLiteDatabase) {
        YLogUtil.i("创建数据库")
        db.execSQL(DBFieldManager.createTable(TestInfo::class.java))
    }

    override fun onUpgradeDB(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        YLogUtil.iFormat("更新数据库--oldVersion：%s--newVersion：%s", oldVersion, newVersion)
        addField(db, oldVersion, TestInfo::class.java)
    }

    companion object {

        private const val VERSION = 3

        private var instance: DBOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): DBOpenHelper {
            if (instance == null) {
                instance = DBOpenHelper(context.applicationContext)
            }
            return instance!!
        }
    }

}
