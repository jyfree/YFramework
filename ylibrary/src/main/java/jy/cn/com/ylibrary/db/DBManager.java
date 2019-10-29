package jy.cn.com.ylibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.concurrent.atomic.AtomicInteger;

import jy.cn.com.ylibrary.util.YLogUtil;

/**
 * Administrator
 * created at 2018/11/7 15:09
 * TODO:数据库管理类
 * 数据库框架性能对比：https://android.ctolib.com/AlexeyZatsepin-Android-ORM-benchmark.html
 */
public class DBManager {

    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static DBManager instance;
    private static BaseOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(BaseOpenHelper helper) {
        if (instance == null) {
            instance = new DBManager();
            mDatabaseHelper = helper;
            //多线程读写
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mDatabaseHelper.setWriteAheadLoggingEnabled(true);
            }
        }
    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DBManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {

        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            try {
                mDatabase = mDatabaseHelper.getWritableDatabase();
            } catch (Exception e) {
                e.printStackTrace();
                YLogUtil.INSTANCE.e("打开数据库出错", e.getMessage());
                mDatabase = mDatabaseHelper.getReadableDatabase();
            }
        }
        return mDatabase;

    }


    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            try {
                mDatabase.close();
            } catch (Exception e) {
                e.printStackTrace();
                YLogUtil.INSTANCE.e("关闭数据库出错", e.getMessage());
            }

        }
    }
}
