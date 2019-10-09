package jy.cn.com.ylibrary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator
 * @Date 2018/12/5-11:29
 * @TODO 数据库基类
 */
public abstract class BaseDao<T> {

    /**
     * 插入或更新单条数据
     *
     * @param item
     */
    public synchronized void insertOrUpdate(T item) {
        ArrayList<T> tmpList = getListInfo();
        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            if (db.isOpen()) {
                //db是否存在此数据
                boolean isExist = false;
                for (T tmpInfo : tmpList) {
                    if (compareItem(item, tmpInfo)) {
                        isExist = true;
                        break;
                    }
                }
                if (isExist) {
                    updateItem(db, item);
                } else {
                    db.insert(getTableName(), null, getContentValues(item));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    /**
     * 插入单条数据
     *
     * @param item
     */
    public synchronized void insert(T item) {
        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            if (db.isOpen()) {
                db.insert(getTableName(), null, getContentValues(item));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    /**
     * 批量插入（旧数据删除）
     *
     * @param dataList
     */
    public synchronized void insert(ArrayList<T> dataList) {
        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            if (db.isOpen()) {
                db.beginTransaction(); // 手动设置开始事务

                deleteAll(db);

                for (T item : dataList) {
                    db.insert(getTableName(), null, getContentValues(item));

                }
                db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
                db.endTransaction(); // 处理完成
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }


    /**
     * 批量插入或更新
     */
    public synchronized void insertOrUpdate(List<T> dataList) {
        ArrayList<T> tmpList = getListInfo();

        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            if (db.isOpen()) {
                db.beginTransaction(); // 手动设置开始事务


                for (T item : dataList) {

                    boolean isExist = false;//db是否存在此数据

                    for (T tmpInfo : tmpList) {

                        if (compareItem(item, tmpInfo)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (isExist) {
                        updateItem(db, item);
                    } else {
                        db.insert(getTableName(), null, getContentValues(item));
                    }
                }

                db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
                db.endTransaction(); // 处理完成

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    /**
     * 获取map集合
     *
     * @return
     */
    public Map<Object, T> getMapInfo(String key) {
        Map<Object, T> map = new HashMap<>();

        Cursor cursor = null;

        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            cursor = db.query(getTableName(), null, null, null, null, null, null);

            if (db.isOpen()) {
                while (cursor.moveToNext()) {

                    String id = cursor.getString(cursor.getColumnIndex(key));
                    map.put(id, getItemInfo(cursor));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return map;

    }

    /**
     * 获取list集合
     *
     * @return
     */
    public ArrayList<T> getListInfo() {
        ArrayList<T> msgList = new ArrayList<>();

        Cursor cursor = null;

        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            cursor = db.query(getTableName(), null, null, null, null, null, null);
            if (db.isOpen()) {
                while (cursor.moveToNext()) {
                    msgList.add(getItemInfo(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return msgList;
    }


    /**
     * 获取list集合（自定义db和cursor）
     *
     * @return
     */
    public ArrayList<T> getListInfo(SQLiteDatabase db, Cursor cursor) {
        ArrayList<T> msgList = new ArrayList<>();

        try {
            if (db.isOpen()) {
                while (cursor.moveToNext()) {
                    msgList.add(getItemInfo(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return msgList;
    }

    /**
     * 获取表名
     *
     * @return
     */
    public abstract String getTableName();

    /**
     * item转ContentValues
     *
     * @param item
     * @return
     */
    public abstract ContentValues getContentValues(T item);

    /**
     * 获取item
     *
     * @param cursor
     * @return
     */
    public abstract T getItemInfo(Cursor cursor);

    /**
     * 对比两个item是否相同
     *
     * @param item1
     * @param item2
     * @return
     */
    public abstract boolean compareItem(T item1, T item2);

    /**
     * 更新item
     *
     * @param db
     * @param item
     */
    public abstract void updateItem(SQLiteDatabase db, T item);

    /**
     * 删除所有信息
     *
     * @param db
     */
    public void deleteAll(SQLiteDatabase db) {
        try {
            db.delete(getTableName(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有信息
     */
    public void deleteAll() {
        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            if (db.isOpen()) {
                db.delete(getTableName(), null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }


    public String getString(Cursor cursor, String name) {
        return cursor.getString(cursor.getColumnIndex(name));
    }

    public int getInt(Cursor cursor, String name) {
        return cursor.getInt(cursor.getColumnIndex(name));
    }

    public long getLong(Cursor cursor, String name) {
        return cursor.getLong(cursor.getColumnIndex(name));
    }

    public float getFloat(Cursor cursor, String name) {
        return cursor.getFloat(cursor.getColumnIndex(name));
    }

    public boolean getBool(Cursor cursor, String name) {
        return cursor.getInt(cursor.getColumnIndex(name)) == 1;
    }
}
