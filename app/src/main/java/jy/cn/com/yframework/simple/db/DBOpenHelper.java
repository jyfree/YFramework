package jy.cn.com.yframework.simple.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import jy.cn.com.ylibrary.db.BaseOpenHelper;

/**
 * @Author Administrator
 * @Date 2019/10/23-18:12
 * @TODO
 */
public class DBOpenHelper extends BaseOpenHelper {

    private static final int VERSION = 1;

    private static DBOpenHelper instance;

    DBOpenHelper(Context context) {
        super(context, VERSION);
    }

    public synchronized static DBOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBOpenHelper(context.getApplicationContext());
        }
        return instance;
    }


    @Override
    public void onCreateDB(SQLiteDatabase db) {

    }

    @Override
    public void onUpgradeDB(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
