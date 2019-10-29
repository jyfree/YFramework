/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jy.cn.com.ylibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jy.cn.com.ylibrary.util.YLogUtil;

/**
 * Administrator
 * created at 2015/12/8 14:16
 * TODO:数据库辅助类
 * <p/>
 * 注意：如果数据库表有int|double类型的字段。
 * <p/>
 * 插入数据时，该类型字段不能为null，否则不能插入数据。
 * <p/>
 * 若为null，则不能设置事务，否则会导致db死锁
 */
public abstract class BaseOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = BaseOpenHelper.class.getSimpleName();


    public BaseOpenHelper(Context context, int version) {
        super(context, getUserDatabaseName(), null, version);
    }


    private static String getUserDatabaseName() {
        return "app.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DownloadDao.DOWNLOAD_TABLE_CREATE);
        onCreateDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgradeDB(db, oldVersion, newVersion);
    }

    public abstract void onCreateDB(SQLiteDatabase db);

    public abstract void onUpgradeDB(SQLiteDatabase db, int oldVersion, int newVersion);

    /**
     * 新增表字段
     * 注意：更新的字段必需添加注解：@Scope(isUpdateField = true, updateFieldVersion = xxx)
     * 否则更新失败
     *
     * @param db         db
     * @param oldVersion 旧版本号
     * @param subClass   需要更新的目标
     */
    public void addField(SQLiteDatabase db, int oldVersion, Class<?> subClass) {
        execUpgradeSQL(db, DBFieldManager.addField(subClass, oldVersion).toArray(new String[]{}));
    }

    /**
     * 更新版本
     * 示例：String sql = "alter table [" + DownloadDao.TABLE_NAME + "] add " + "test" + " INTEGER";
     *
     * @param db
     * @param sqlArray 数据库语句集合
     */
    public void execUpgradeSQL(SQLiteDatabase db, String... sqlArray) {
        if (sqlArray.length == 0) {
            YLogUtil.INSTANCE.e(TAG, "更新db语句错误，请检查版本号、db实体");
            return;
        }
        db.beginTransaction();
        try {
            for (String sql : sqlArray) {
                db.execSQL(sql);
            }
            db.setTransactionSuccessful();
        } catch (Throwable ex) {
            ex.printStackTrace();
            YLogUtil.INSTANCE.e(TAG, "更新db失败", sqlArray);
        } finally {
            db.endTransaction();
        }
    }

}
