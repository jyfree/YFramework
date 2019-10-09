package jy.cn.com.ylibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import jy.cn.com.ylibrary.interceptor.PopupWindowTouchInterceptor;
import jy.cn.com.ylibrary.loadsir.callback.SuccessCallback;
import jy.cn.com.ylibrary.loadsir.core.LoadService;
import jy.cn.com.ylibrary.loadsir.core.LoadSir;
import jy.cn.com.ylibrary.util.HandlerUtil;
import jy.cn.com.ylibrary.util.ScreenResolution;
import jy.cn.com.ylibrary.util.YLogUtil;
import jy.cn.com.ylibrary.widget.CustomPopWindow;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;

/**
 * @Author Administrator
 * @Date 2019/9/25-21:59
 * @TODO 加载器帮助类
 */
public class LoadSirHelper {

    //customPopupWindow
    private CustomPopWindow customPopWindow = null;

    //loading view
    private LoadSir loadSir = LazyKt.lazy(new Function0<LoadSir>() {
        @Override
        public LoadSir invoke() {
            return LoadSir.getDefault();
        }
    }).getValue();
    private LoadService loadService;


    private void initPopupWindowLoading(Context context) {
        if (customPopWindow == null && loadSir.getResLayoutIdOfPopWindow() != LoadSir.EMPTY_LAYOUT) {
            Pair<Integer, Integer> screenResolution = ScreenResolution.getResolution(context);
            customPopWindow = new CustomPopWindow.PopupWindowBuilder(context).setView(loadSir.getResLayoutIdOfPopWindow())
                    .size(screenResolution.first, screenResolution.second)
                    .enableBackgroundDark(true)
                    .setTouchIntercepter(new PopupWindowTouchInterceptor())
                    .create();
        }
    }

    public void showPopWindowLoading(Activity context, boolean flag) {
        showPopWindowLoading(context, context.findViewById(android.R.id.content), flag);
    }

    /**
     * 注意：popWindow必须依附于某一个view，而在onCreate中view还没有加载完毕，必须要等activity的生命周期函数全部执行完毕
     *
     * @param context 上下文
     * @param parent  所依附的view
     * @param flag    是否显示
     */
    public void showPopWindowLoading(Context context, View parent, boolean flag) {
        try {
            initPopupWindowLoading(context);
            if (customPopWindow == null) return;
            if (flag) {
                customPopWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
            } else {
                disMissPopWindow();
            }
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
            YLogUtil.INSTANCE.e(e.getMessage());
        }
    }

    private void disMissPopWindow() {
        HandlerUtil.INSTANCE.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    customPopWindow.dissmiss();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    YLogUtil.INSTANCE.e(e.getMessage());
                }
            }
        });
    }

    public void showLoading(Object target, boolean flag) {
        if (loadSir.getLoadingCallbackClass() == null) {
            return;
        }
        if (loadService == null) {
            loadService = loadSir.register(target);
        }
        loadService.showCallback(flag ? loadSir.getLoadingCallbackClass() : SuccessCallback.class);
    }

}
