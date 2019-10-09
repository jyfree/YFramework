package jy.cn.com.ylibrary.util

import android.app.ActivityManager
import android.content.Context
import android.os.Process
import jy.cn.com.ylibrary.BaseApplication
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
     *
     * @param file      文件
     * @param authority 7.0及以上安装需要传入清单文件中的`<provider>`的authorities属性
     * <br></br>参看https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     */
    fun installApp(file: File, authority: String) {
        if (!FileUtils.isFileExists(file)) return
        BaseApplication.getInstance().startActivity(IntentUtils.getInstallAppIntent(file, authority))
    }


}