package jy.cn.com.yframework.simple.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import jy.cn.com.yframework.simple.db.bean.TestInfo;
import jy.cn.com.ylibrary.db.BaseOpenHelper;
import jy.cn.com.ylibrary.db.DBFieldManager;
import jy.cn.com.ylibrary.util.YLogUtil;

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
        YLogUtil.INSTANCE.i("创建数据库");
        db.execSQL(DBFieldManager.createTable(TestInfo.class));
    }

    @Override
    public void onUpgradeDB(SQLiteDatabase db, int oldVersion, int newVersion) {
        YLogUtil.INSTANCE.iFormat("更新数据库--oldVersion：%s--newVersion：%s", oldVersion, newVersion);
        addField(db, oldVersion, TestInfo.class);
    }

}
