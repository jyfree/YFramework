package jy.cn.com.ylibrary.sp;

import android.content.Context;

/**
 * Administrator
 * created at 2018/11/20 11:35
 * TODO:存储配置信息
 */
public class SharedPreferencesConfigUtils extends SharedPreferencesBaseUtils {

    private static SharedPreferencesConfigUtils mInstance;

    private static final String SP_NAME = "Config_File";

    public static SharedPreferencesConfigUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SharedPreferencesConfigUtils.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreferencesConfigUtils(context);
                }
            }
        }
        return mInstance;
    }

    private SharedPreferencesConfigUtils(Context context) {
        try {
            if (sharedPreferences == null)
                //设置为共享，以免service需要访问
                sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


}
