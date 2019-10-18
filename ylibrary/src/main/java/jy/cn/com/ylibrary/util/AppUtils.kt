package jy.cn.com.ylibrary.util

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Process
import jy.cn.com.ylibrary.BaseApplication
import jy.cn.com.ylibrary.sp.SharedPreferencesConfigUtils
import java.io.File


object AppUtils {
    /**
     * 获取当前进程名
     */
    fun getCurProcessName(context: Context): String? {
        val pid = Process.myPid()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.runningAppProcesses.forEach {
            if (it.pid == pid) {
                return it.processName
            }
        }
        return null
    }

    /**
     * 根据进程id，获取进程名
     */
    fun getCurProcessName(context: Context, pid: Int): String? {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.runningAppProcesses.forEach {
            if (it.pid == pid) {
                return it.processName
            }
        }
        return null
    }

    /**
     * 安装App（支持7.0）
     * 8.0 需要manifest注册权限：android.permission.REQUEST_INSTALL_PACKAGES
     * @param file      文件
     * @param authority 7.0及以上安装需要传入清单文件中的`<provider>`的authorities属性
     * <br></br>参看https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     */
    fun installApp(file: File, authority: String) {
        if (!FileUtils.isFileExists(file)) return
        BaseApplication.getInstance().startActivity(IntentUtils.getInstallAppIntent(file, authority))
    }

    /**
     * 获取APP签名
     */
    fun getSignature(): String {
        try {
            var spSignature = SharedPreferencesConfigUtils.getInstance(BaseApplication.getInstance().applicationContext).getString(SharedPreferencesConfigUtils.SIGNATURE)
            if (!spSignature.isNullOrEmpty()) {
                return spSignature
            }
            val pm: PackageManager = BaseApplication.getInstance().packageManager
            val apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES)
            for (packageInfo in apps) {
                if (packageInfo.packageName.contains(BaseApplication.getInstance().packageName)) {

                    spSignature = MD5Util.getMD5(packageInfo.signatures[0].toByteArray())
                    SharedPreferencesConfigUtils.getInstance(BaseApplication.getInstance().applicationContext).setString(SharedPreferencesConfigUtils.SIGNATURE, spSignature)
                    return spSignature
                }
            }

        } catch (e: Exception) {
            YLogUtil.e("getSignature", e.message)
        }

        return "unKnow"
    }

}