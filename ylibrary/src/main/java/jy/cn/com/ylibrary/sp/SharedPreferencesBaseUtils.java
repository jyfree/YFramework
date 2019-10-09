package jy.cn.com.ylibrary.sp;

import android.content.SharedPreferences;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jy.cn.com.ylibrary.util.EncodeUtils;
import jy.cn.com.ylibrary.util.ParcelableUtil;
import jy.cn.com.ylibrary.util.YLogUtil;

/**
 * Administrator
 * created at 2018/11/20 12:08
 * TODO:sp基类
 */
public class SharedPreferencesBaseUtils {

    private final String TAG = "SP";
    public SharedPreferences sharedPreferences;

    public boolean getBoolean(String keyName) {
        try {
            return sharedPreferences.getBoolean(keyName, false);
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
        return false;
    }


    public void setBoolean(String keyName, boolean value) {
        try {
            sharedPreferences.edit().putBoolean(keyName, value).apply();
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
    }

    public int getInt(String keyName) {
        try {
            return sharedPreferences.getInt(keyName, 0);
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
        return 0;
    }

    public int getInt(String keyName, int defValue) {
        try {
            return sharedPreferences.getInt(keyName, defValue);
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
        return defValue;
    }

    public void setInt(String keyName, int value) {
        try {
            sharedPreferences.edit().putInt(keyName, value).apply();
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
    }

    public void setObject(String keyName, Parcelable parcelable) {
        try {
            byte[] bytes = ParcelableUtil.marShall(parcelable);
            sharedPreferences.edit().putString(keyName, EncodeUtils.base64Encode2String(bytes)).apply();
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
    }

    public <T extends Parcelable> T getObject(String keyName, Parcelable.Creator<T> creator) {
        try {
            return ParcelableUtil.unMarShall(sharedPreferences.getString(keyName, ""), creator);
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
            return null;
        }
    }

    public String getString(String keyName) {
        try {
            return sharedPreferences.getString(keyName, "");
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
        return "";
    }


    public void setString(String keyName, String value) {
        try {
            sharedPreferences.edit().putString(keyName, value).apply();
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
    }

    public Set<String> getStringSet(String keyName) {
        try {
            return sharedPreferences.getStringSet(keyName, null);
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
        return null;
    }

    public void setStringSet(String keyName, Set<String> value) {
        try {
            sharedPreferences.edit().putStringSet(keyName, value).apply();
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
    }

    public long getLong(String keyName) {
        try {
            return sharedPreferences.getLong(keyName, 0);
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
        return 0;
    }


    public void setLong(String keyName, long value) {
        try {
            sharedPreferences.edit().putLong(keyName, value).apply();
        } catch (Exception e) {
            YLogUtil.INSTANCE.eTag(TAG, e.getMessage());
        }
    }

    public void putHashMapInfo(String key, Map<String, Integer> datas) {
        JSONObject object = new JSONObject();
        for (Map.Entry<String, Integer> entry : datas.entrySet()) {
            try {
                object.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setString(key, object.toString());
    }

    public Map<String, Integer> getHashMapInfo(String key) {
        Map<String, Integer> datas = new HashMap<>();
        String result = getString(key);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray names = jsonObject.names();
            if (names != null) {
                for (int j = 0; j < names.length(); j++) {
                    String name = names.getString(j);
                    int value = jsonObject.getInt(name);
                    datas.put(name, value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datas;
    }
}
