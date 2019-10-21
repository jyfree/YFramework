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
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseOpenHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static DatabaseOpenHelper instance;

    /**
     * 下载信息表
     */
    private static final String DOWNLOAD_TABLE_CREATE = "CREATE TABLE "
            + DownloadDao.TABLE_NAME + " ("
            + DownloadDao.COLUMN_NAME_PRIMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DownloadDao.COLUMN_NAME_SAVE_PATH + " TEXT, "
            + DownloadDao.COLUMN_NAME_COUNT_LENGTH + " INTEGER, "
            + DownloadDao.COLUMN_NAME_READ_LENGTH + " INTEGER, "
            + DownloadDao.COLUMN_NAME_CONNECTION_TIME + " INTEGER, "
            + DownloadDao.COLUMN_NAME_STATE_INT + " INTEGER, "
            + DownloadDao.COLUMN_NAME_URL + " TEXT, "
            + DownloadDao.COLUMN_NAME_UPDATE_PROGRESS + " INTEGER); ";

    private DatabaseOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    public synchronized static DatabaseOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static String getUserDatabaseName() {
        return "app.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DOWNLOAD_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 3) {
            upgradeToVersion2(db);
        }
    }


    /**
     * 更新到版本2
     *
     * @param db
     */
    private void upgradeToVersion2(SQLiteDatabase db) {
//        db.beginTransaction();
//        try {
//            String sql = "alter table [" + DownloadDao.TABLE_NAME + "] add " + "test" + " INTEGER";
//            db.execSQL(sql);
//            db.setTransactionSuccessful();
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//            YLogUtil.INSTANCE.eTag(TAG,"更新到版本2失败");
//        } finally {
//            db.endTransaction();
//        }
    }

}
