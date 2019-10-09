package jy.cn.com.ylibrary;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.util.ArrayMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 程序入口
 *
 * @author jy
 */
public class BaseApplication extends Application {

    @SuppressWarnings("rawtypes")
    public List<Activity> activityList = new ArrayList<>();

    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (null == mInstance) {
            mInstance = this;
        }
    }

    /**
     * 添加Activity到容器中
     *
     * @param activity
     */
    @SuppressWarnings("unchecked")
    public synchronized void addActivity(Activity activity) {
        for (int i = 0; i < activityList.size(); i++) {
            if (((Activity) activityList.get(i)).getLocalClassName().equals(activity.getLocalClassName())) {
                activityList.remove(i);
                break;
            }
        }
        activityList.add(activity);
    }

    /**
     * activity被销毁时，从activityList中移除
     *
     * @param activity
     */
    public synchronized void removeActivity(Activity activity) {
        if (activityList.size() != 0 && activity != null) {

            activityList.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 清空activityList
     */
    public synchronized void clearActivities() {
        if (activityList.size() != 0) {
            Activity activity = null;
            Iterator<Activity> iterator = activityList.iterator();
            while (iterator.hasNext()) {
                activity = iterator.next();
                if (!activity.isFinishing()) {
                    activity.finish();
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 移除非当前的所有activity
     */
    public synchronized void clearActivitiesExitsThis(String activityString) {
        if (activityList.size() != 0) {
            Activity activity = null;
            Iterator<Activity> iterator = activityList.iterator();
            while (iterator.hasNext()) {
                activity = iterator.next();
                if (!activity.isFinishing() && !activity.toString().contains(activityString)) {
                    activity.finish();
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 移除activityString
     *
     * @param activityString
     */
    public synchronized void removeActivityThis(String activityString) {
        if (activityList.size() != 0) {
            Activity activity = null;
            @SuppressWarnings("unchecked")
            Iterator<Activity> iterator = activityList.iterator();
            while (iterator.hasNext()) {
                activity = iterator.next();
                if (activity.toString().contains(activityString)) {
                    if (!activity.isFinishing()) {
                        activity.finish();
                        iterator.remove();
                    }
                    return;
                }

            }
        }
    }

    /**
     * 除activityString外的activity都finish
     */
    public synchronized void removeActivityUnThisOther(String activityString) {
        if (activityList.size() != 0) {
            Activity activity = null;
            @SuppressWarnings("unchecked")
            Iterator<Activity> iterator = activityList.iterator();
            while (iterator.hasNext()) {
                activity = iterator.next();
                if (!activity.toString().contains(activityString)) {
                    if (!activity.isFinishing()) {
                        activity.finish();
                        iterator.remove();
                    }
                }

            }
        }
    }

    /**
     * 判断activity是否finish
     *
     * @param activity
     * @return
     */
    public synchronized boolean activityIsRun(Activity activity) {
        if (activityList.size() != 0 && activity != null) {

            if (activityList.contains(activity)) {

                if (!activity.isFinishing()) {

                    return true;
                } else {

                    return false;
                }
            }

        }
        return false;
    }

    /**
     * 判断activity是否finish
     *
     * @param activityString com.jj.shows.MainActivity
     * @return
     */
    public synchronized boolean activityIsRun(String activityString) {
        if (activityList.size() != 0) {

            Activity activity = null;
            @SuppressWarnings("unchecked")
            Iterator<Activity> iterator = activityList.iterator();
            while (iterator.hasNext()) {
                activity = iterator.next();
                if (activity.toString().contains(activityString)) {
                    if (!activity.isFinishing()) {

                        return true;
                    } else {

                        return false;
                    }
                }

            }

        }
        return false;
    }


    /**
     * 遍历所有Activity并finish
     */
    @SuppressWarnings("unchecked")
    public synchronized void exit() {
        clearActivities();
        System.exit(0);
    }

    /**
     * 获取栈顶Activity
     *
     * @return 栈顶Activity
     */
    public Activity getTopActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = null;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                activities = (HashMap) activitiesField.get(activityThread);
            } else {
                activities = (ArrayMap) activitiesField.get(activityThread);
            }
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Activity getTopActivityByActivityList() {
        if (!activityList.isEmpty())
            return activityList.get(activityList.size() - 1);
        return null;
    }
}
