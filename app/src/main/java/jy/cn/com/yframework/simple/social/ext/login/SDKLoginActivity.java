package jy.cn.com.yframework.simple.social.ext.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import jy.cn.com.yframework.Constants;
import jy.cn.com.ylibrary.rxbus.RxBus;
import jy.cn.com.ylibrary.rxbus.Subscribe;
import jy.cn.com.ylibrary.rxbus.ThreadMode;
import jy.cn.com.ylibrary.util.HandlerUtil;

/**
 * @Author Administrator
 * @Date 2019/11/12-11:03
 * @TODO
 */
public class SDKLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不接受触摸屏事件
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        RxBus.getDefault().register(this);
        ExtLogin.getInstance().getSDKLoginManager().behavior(this, savedInstanceState);
        initCompleteTime();
    }

    private void initCompleteTime() {
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                HandlerUtil.INSTANCE.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ExtLogin.getInstance().getSDKLoginManager().checkLogin(SDKLoginActivity.this, getIntent());
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ExtLogin.getInstance().getSDKLoginManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }

    @SuppressLint("微信登录授权回调--成功")
    @Subscribe(code = Constants.RxBus.CODE_WX_LOGIN_AUTH_SUCCEED, threadMode = ThreadMode.MAIN)
    public void rxBusWXLoginSucceed(String code) {
        ExtLogin.getInstance().getSDKLoginManager().onResultToWXAuthSuccess(this, code);
    }

    @SuppressLint("微信登录授权回调--失败")
    @Subscribe(code = Constants.RxBus.CODE_WX_LOGIN_AUTH_FAIL, threadMode = ThreadMode.MAIN)
    public void rxBusWXLoginFail(int errCode) {
        ExtLogin.getInstance().getSDKLoginManager().onResultToWXAuthFail(this, errCode);
    }

    @SuppressLint("微信登录授权回调--取消")
    @Subscribe(code = Constants.RxBus.CODE_WX_LOGIN_AUTH_CANCEL, threadMode = ThreadMode.MAIN)
    public void rxBusWXLoginCancel() {
        ExtLogin.getInstance().getSDKLoginManager().onResultToWXAuthCancel(this);
    }
}
