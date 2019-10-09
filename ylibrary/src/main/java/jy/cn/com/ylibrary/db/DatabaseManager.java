package jy.cn.com.ylibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Administrator
 * created at 2018/11/7 15:09
 * TODO:数据库管理类
 * 数据库框架性能对比：https://android.ctolib.com/AlexeyZatsepin-Android-ORM-benchmark.html
 */
public class DatabaseManager {

    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static DatabaseManager instance;
    private static DbOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(DbOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
            //多线程读写
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mDatabaseHelper.setWriteAheadLoggingEnabled(true);
            }
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
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
                mDatabase = mDatabaseHelper.getReadableDatabase();
            }
        }
        return mDatabase;

    }


    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();

        }
    }

}
