package jy.cn.com.yframework.simple.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import jy.cn.com.ylibrary.db.BaseOpenHelper;
import jy.cn.com.ylibrary.db.DBFieldManager;

/**
 * @Author Administrator
 * @Date 2019/10/23-18:12
 * @TODO 扩展数据库、创建|更新表
 */
public class DBOpenHelper extends BaseOpenHelper {

    private static final int VERSION = 3;

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
        db.execSQL(DBFieldManager.createTable(TestDao.class));
    }

    @Override
    public void onUpgradeDB(SQLiteDatabase db, int oldVersion, int newVersion) {
        addField(db, oldVersion, TestDao.class);
    }

}
