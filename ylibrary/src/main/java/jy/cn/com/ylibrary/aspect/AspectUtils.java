package jy.cn.com.ylibrary.aspect;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import jy.cn.com.ylibrary.BaseApplication;

/**
 * @Author Administrator
 * @Date 2019/11/8-15:47
 * @TODO
 */
public class AspectUtils {

    public static final String TAG = "Aspect";

    /**
     * 通过对象获取上下文
     */
    public static Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof android.app.Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return BaseApplication.getInstance();
    }
}
