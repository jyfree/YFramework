package jy.cn.com.ylibrary.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import jy.cn.com.ylibrary.util.YLogUtil;

/**
 * Administrator
 * created at 2019/10/18 17:00
 * TODO:log收集
 * 注：若path为空则保存到sdcard（需要访问权限）
 */
public class LogCatHelper {

    private static LogCatHelper instance = null;
    private static final String TAG = LogCatHelper.class.getSimpleName();
    private String dirPath;//保存路径
    private int appId;//应用pid
    private Thread logThread;

    /**
     * @param path log日志保存根目录
     */
    public static LogCatHelper getInstance(Context mContext, String path) {
        if (instance == null) {
            instance = new LogCatHelper(mContext, path);
        }
        return instance;
    }

    private LogCatHelper(Context mContext, String path) {
        appId = android.os.Process.myPid();
        if (TextUtils.isEmpty(path)) {
            dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "seeker" + File.separator + mContext.getPackageName();
        } else {
            dirPath = path;
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 启动log日志保存
     */
    public void start() {
        if (!YLogUtil.SHOW_LOG) return;
        if (logThread == null) {
            logThread = new Thread(new LogRunnable(appId, dirPath));
        }
        logThread.start();
    }

    private static class LogRunnable implements Runnable {

        private Process mProcess;
        private FileOutputStream fos;
        private BufferedReader mReader;
        private String cmd;
        private String mPid;
        String dirPath;
        String fileName;

        LogRunnable(int pid, String dirPath) {
            this.mPid = "" + pid;
            this.dirPath = dirPath;
            try {
                fileName = FormatDate.getFormatDate();
                File file = new File(dirPath, fileName + ".log");
                if (!file.exists()) {
                    file.createNewFile();
                }
                fos = new FileOutputStream(file, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cmd = "logcat *:v | grep \"(" + mPid + ")\"";
        }

        @Override
        public void run() {
            try {
                filter(dirPath, fileName);
                mProcess = Runtime.getRuntime().exec(cmd);
                mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()), 1024);
                String line;
                while ((line = mReader.readLine()) != null) {
                    if (line.length() == 0) {
                        continue;
                    }
                    if (fos != null && line.contains(mPid)) {
                        fos.write((FormatDate.getFormatTime() + " " + line + "\r\n").getBytes());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mProcess != null) {
                    mProcess.destroy();
                    mProcess = null;
                }
                try {
                    if (mReader != null) {
                        mReader.close();
                        mReader = null;
                    }
                    if (fos != null) {
                        fos.close();
                        fos = null;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private static void filter(String dirPath, String nowFileName) {
        try {
            int nowTime = 0;
            if (null != nowFileName && !nowFileName.isEmpty()) {
                nowTime = Integer.parseInt(nowFileName.substring(0, nowFileName.length() - 2));
                YLogUtil.INSTANCE.iTag(TAG, "创建log文件", nowFileName, "当前时间", nowTime);
            }
            File root = new File(dirPath);
            File[] fileList = root.listFiles();
            if (fileList == null || fileList.length < 1) {
                return;
            }
            YLogUtil.INSTANCE.iTag(TAG, "过滤旧log文件，只保留当天log");
            for (File file : fileList) {
                String name = file.getName();
                if (!name.isEmpty() && name.endsWith(".log")) {
                    int time = Integer.parseInt(name.substring(0, name.length() - 6));
                    if (nowTime - time > 0) {
                        boolean success = file.delete();
                        YLogUtil.INSTANCE.iTag(TAG, "删除文件", name, "删除成功", success);
                    } else {
                        YLogUtil.INSTANCE.iTag(TAG, "保留文件", name);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            YLogUtil.INSTANCE.eTag(TAG, "过滤log失败", e.getMessage());
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static class FormatDate {

        static String getFormatDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
            return sdf.format(System.currentTimeMillis());
        }

        static String getFormatTime() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(System.currentTimeMillis());
        }
    }
}