package jy.cn.com.ylibrary.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.util.*

/**
 * @Author Administrator
 * @Date 2018/12/5-11:29
 * @TODO 数据库基类
 */
abstract class BaseDao<T> {

    private val hashMap = HashMap<String, Int>()

    /**
     * 获取表名
     *
     * @return
     */
    abstract val tableName: String

    /**
     * 插入或更新单条数据
     *
     * @param item
     */
    @Synchronized
    fun insertOrUpdate(item: T) {
        val tmpList = getListInfo()
        try {
            val db = DatabaseManager.getInstance().openDatabase()
            if (db.isOpen) {
                //db是否存在此数据
                var isExist = false
                for (tmpInfo in tmpList) {
                    if (compareItem(item, tmpInfo)) {
                        isExist = true
                        break
                    }
                }
                if (isExist) {
                    updateItem(db, item)
                } else {
                    db.insert(tableName, null, getContentValues(item))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            DatabaseManager.getInstance().closeDatabase()
        }
    }

    /**
     * 插入单条数据
     *
     * @param item
     */
    @Synchronized
    fun insert(item: T) {
        try {
            val db = DatabaseManager.getInstance().openDatabase()
            if (db.isOpen) {
                db.insert(tableName, null, getContentValues(item))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            DatabaseManager.getInstance().closeDatabase()
        }
    }

    /**
     * 批量插入（旧数据删除）
     *
     * @param dataList
     */
    @Synchronized
    fun insert(dataList: ArrayList<T>) {
        try {
            val db = DatabaseManager.getInstance().openDatabase()
            if (db.isOpen) {
                db.beginTransaction() // 手动设置开始事务

                deleteAll(db)

                for (item in dataList) {
                    db.insert(tableName, null, getContentValues(item))

                }
                db.setTransactionSuccessful() // 设置事务处理成功，不设置会自动回滚不提交
                db.endTransaction() // 处理完成
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            DatabaseManager.getInstance().closeDatabase()
        }
    }


    /**
     * 批量插入或更新
     */
    @Synchronized
    fun insertOrUpdate(dataList: List<T>) {
        val tmpList = getListInfo()

        try {
            val db = DatabaseManager.getInstance().openDatabase()
            if (db.isOpen) {
                db.beginTransaction() // 手动设置开始事务


                for (item in dataList) {

                    var isExist = false//db是否存在此数据

                    for (tmpInfo in tmpList) {

                        if (compareItem(item, tmpInfo)) {
                            isExist = true
                            break
                        }
                    }
                    if (isExist) {
                        updateItem(db, item)
                    } else {
                        db.insert(tableName, null, getContentValues(item))
                    }
                }

                db.setTransactionSuccessful() // 设置事务处理成功，不设置会自动回滚不提交
                db.endTransaction() // 处理完成

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            DatabaseManager.getInstance().closeDatabase()
        }
    }

    /**
     * 获取map集合
     *
     * @return
     */
    fun getMapInfo(key: String): Map<Any, T> {
        val map = HashMap<Any, T>()

        var cursor: Cursor? = null

        try {
            val db = DatabaseManager.getInstance().openDatabase()
            cursor = db.query(tableName, null, null, null, null, null, null)

            if (db.isOpen) {
                while (cursor?.moveToNext() == true) {

                    val id = cursor.getString(getColumnIndex(cursor, key))
                    map[id] = getItemInfo(cursor)

                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            DatabaseManager.getInstance().closeDatabase()
        }
        return map

    }

    /**
     * 获取list集合
     *
     * @return
     */
    fun getListInfo(): ArrayList<T> {
        val db = DatabaseManager.getInstance().openDatabase()
        val cursor = db.query(tableName, null, null, null, null, null, null)
        return queryList(db, cursor)
    }

    /**
     * 获取list集合（自定义db和cursor）
     *
     * @return
     */
    fun queryList(db: SQLiteDatabase, cursor: Cursor?): ArrayList<T> {

        val msgList = ArrayList<T>()
        try {
            if (db.isOpen) {
                while (cursor?.moveToNext() == true) {
                    msgList.add(getItemInfo(cursor))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            DatabaseManager.getInstance().closeDatabase()
        }
        return msgList

    }

    fun queryItem(db: SQLiteDatabase, cursor: Cursor?): T? {

        var t: T? = null
        try {
            if (db.isOpen) {
                if (cursor?.moveToFirst() == true) {
                    t = getItemInfo(cursor)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            DatabaseManager.getInstance().closeDatabase()
        }
        return t
    }

    /**
     * 删除所有信息
     *
     * @param db
     */
    fun deleteAll(db: SQLiteDatabase) {

        try {
            db.delete(tableName, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 删除所有信息
     */
    fun deleteAll() {

        try {
            val db = DatabaseManager.getInstance().openDatabase()
            if (db.isOpen) {
                db.delete(tableName, null, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            DatabaseManager.getInstance().closeDatabase()
        }

    }


    /**
     * item转ContentValues
     *
     * @param item
     * @return
     */
    abstract fun getContentValues(item: T): ContentValues

    /**
     * 获取item
     *
     * @param cursor
     * @return
     */
    abstract fun getItemInfo(cursor: Cursor): T

    /**
     * 对比两个item是否相同
     *
     * @param item1
     * @param item2
     * @return
     */
    abstract fun compareItem(item1: T, item2: T): Boolean

    /**
     * 更新item
     *
     * @param db
     * @param item
     */
    abstract fun updateItem(db: SQLiteDatabase, item: T)


    fun getString(cursor: Cursor, name: String): String {
        return cursor.getString(getColumnIndex(cursor, name))
    }

    fun getInt(cursor: Cursor, name: String): Int {
        return cursor.getInt(getColumnIndex(cursor, name))
    }

    fun getLong(cursor: Cursor, name: String): Long {
        return cursor.getLong(getColumnIndex(cursor, name))
    }

    fun getFloat(cursor: Cursor, name: String): Float {
        return cursor.getFloat(getColumnIndex(cursor, name))
    }

    fun getBool(cursor: Cursor, name: String): Boolean {
        return cursor.getInt(getColumnIndex(cursor, name)) == 1
    }

    private fun getColumnIndex(cursor: Cursor, name: String): Int {
        if (hashMap.containsKey(name)) {
            return hashMap[name] ?: 0
        }
        val index = cursor.getColumnIndex(name)
        hashMap[name] = index
        return index
    }
}
