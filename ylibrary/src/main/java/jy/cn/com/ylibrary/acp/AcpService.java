package jy.cn.com.ylibrary.acp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import jy.cn.com.ylibrary.util.YLogUtil;

/**
 * @Author Administrator
 * @Date 2019/10/11-17:48
 * @TODO
 */
public class AcpService {

    /**
     * 检查权限授权状态
     *
     * @param context    上下文
     * @param permission 权限
     * @return
     */
    public static int checkSelfPermission(Context context, String permission) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            int targetSdkVersion = info.applicationInfo.targetSdkVersion;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    return ContextCompat.checkSelfPermission(context, permission);
                } else {
                    return PermissionChecker.checkSelfPermission(context, permission);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ContextCompat.checkSelfPermission(context, permission);
    }

    /**
     * 申请授权
     *
     * @param activity    activity
     * @param permissions 取消
     * @param requestCode 请求码
     */
    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        boolean shouldShowRational = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        YLogUtil.INSTANCE.i("shouldShowRational = " + shouldShowRational);
        return shouldShowRational;
    }
}
