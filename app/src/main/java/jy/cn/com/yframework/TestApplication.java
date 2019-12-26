package jy.cn.com.yframework;

import android.content.Context;
import android.support.multidex.MultiDex;

import jy.cn.com.socialsdklibrary.SDKConfig;
import jy.cn.com.yframework.simple.cmd.CMDMessageHandler;
import jy.cn.com.yframework.simple.db.DBOpenHelper;
import jy.cn.com.yframework.simple.loading.callback.EmptyCallback;
import jy.cn.com.yframework.simple.loading.callback.ErrorCallback;
import jy.cn.com.yframework.simple.loading.callback.ImageLoadingCallback;
import jy.cn.com.yframework.simple.loading.callback.LoadingCallback;
import jy.cn.com.yframework.simple.loading.callback.TimeoutCallback;
import jy.cn.com.yframework.simple.strategy.LevelUpMsgHandler;
import jy.cn.com.ylibrary.BaseApplication;
import jy.cn.com.ylibrary.db.DBManager;
import jy.cn.com.ylibrary.glide.ImageLoaderConfiguration;
import jy.cn.com.ylibrary.helper.LogCatHelper;
import jy.cn.com.ylibrary.loadsir.callback.SuccessCallback;
import jy.cn.com.ylibrary.loadsir.core.LoadSir;
import jy.cn.com.ylibrary.selector.XSelector;
import jy.cn.com.ylibrary.strategy.MsgHandleOptions;
import jy.cn.com.ylibrary.util.AppUtils;
import jy.cn.com.ylibrary.util.CrashUtils;
import jy.cn.com.ylibrary.util.YLogUtil;

/**
 * @Author Administrator
 * @Date 2019/9/17-16:00
 * @TODO
 */
public class TestApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        int pid = android.os.Process.myPid();//进程id
        String process = AppUtils.INSTANCE.getCurProcessName(this, pid);//进程名

        YLogUtil.INSTANCE.dFormat("进程id：%s--进程名：%s", pid, process);

        if (null == process || getApplicationContext().getPackageName().equals(process)) {
            //初始化接口请求baseUrl
            RequestDomainConfig.INSTANCE.init();
            //初始化loading
            initLoadSir();
            //初始化数据库
            DBManager.Companion.initializeInstance(DBOpenHelper.Companion.getInstance(getApplicationContext()));
            //初始化cmd
            CMDMessageHandler.INSTANCE.register();
            //初始化imageLoad
            ImageLoaderConfiguration.Companion.getInstance().initImageResId(R.drawable.load_default_image, R.drawable.load_default_image, R.drawable.load_default_image);
            //初始化社会化SDK
            initSocialSDK();
            //将logcat保存到文件
            LogCatHelper.getInstance(this, "").start();
            //初始化颜色背景选择器
            XSelector.INSTANCE.init(this);
            //初始化crash捕获
            initCrashUtils();
            //初始化消息策略
            initMsgHandle();
        }
    }

    @Override
    public synchronized void exit() {
        super.exit();
        CMDMessageHandler.INSTANCE.release();
    }

    /**
     * 初始化loading
     */
    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new ImageLoadingCallback())
                .setDefaultCallback(SuccessCallback.class)
                .setLoadingCallback(LoadingCallback.class)
                .setResLayoutIdOfPopWindow(R.layout.loadsir_callback_loading)
                .commit();
    }

    /**
     * 初始化社会化SDK
     */
    private void initSocialSDK() {
        SDKConfig.beginBuilder()
                .qqAppID("1105229451")
                .wxAppID("wx499feadd9f2855f8")
                .wbAppID("4044733576")
                .wbRedirectUrl("https://api.weibo.com/oauth2/default.html")
                .wbScope("statuses_to_me_read")
                .build(this);
    }

    /**
     * 初始化crash捕获
     */
    private void initCrashUtils() {
        CrashUtils.init(new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                YLogUtil.INSTANCE.eFormat("重启APP，异常信息：%s", crashInfo);
                AppUtils.INSTANCE.relaunchApp();
            }
        });
    }

    /**
     * 初始化消息策略
     */
    private void initMsgHandle() {
        MsgHandleOptions.INSTANCE.beginBuilder()
                .addMsgHandle(LevelUpMsgHandler.class.getSimpleName(), LevelUpMsgHandler.class);
    }
}
