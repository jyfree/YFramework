package jy.cn.com.yframework.simple.social.ext.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseResp;

import jy.cn.com.socialsdklibrary.SDKLogin;
import jy.cn.com.socialsdklibrary.constant.SDKLoginType;
import jy.cn.com.socialsdklibrary.listener.OnSocialSdkLoginListener;
import jy.cn.com.socialsdklibrary.util.SDKLogUtil;
import jy.cn.com.socialsdklibrary.wx.WXListener;


/**
 * @Author Administrator
 * @Date 2019/11/12-11:08
 * @TODO
 */
public class SDKLoginManager {

    private SDKLogin sdkLogin;
    private OnSocialSdkLoginListener loginListener;
    private WXListener wxListener;
    private final String LOGIN_TYPE = "loginType";


    public SDKLoginManager setLoginListener(OnSocialSdkLoginListener loginListener) {
        this.loginListener = loginListener;
        return this;
    }

    public SDKLoginManager setWXListener(WXListener wxListener) {
        this.wxListener = wxListener;
        return this;
    }

    public void requestQQLogin(Context context) {
        request(context, SDKLoginType.TYPE_QQ);
    }

    public void requestWXLogin(Context context) {
        request(context, SDKLoginType.TYPE_WX);
    }

    public void requestWBLogin(Context context) {
        request(context, SDKLoginType.TYPE_WB);
    }

    public void request(Context context, int loginType) {
        Intent intent = new Intent(context, SDKLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(LOGIN_TYPE, loginType);
        context.startActivity(intent);
    }


    public void behavior(Activity activity, Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            initSdkLogin(activity);
            showSDKProgress(true);
        }
    }

    private void initSdkLogin(final Activity activity) {
        sdkLogin = new SDKLogin(activity, new OnSocialSdkLoginListener() {
            @Override
            public void loginAuthSuccess(int type, String token, String info) {
                onDestroy(activity);
                if (loginListener != null) {
                    loginListener.loginAuthSuccess(type, token, info);
                }
            }

            @Override
            public void loginFail(int type, String error) {
                onDestroy(activity);
                if (loginListener != null) {
                    loginListener.loginFail(type, error);
                }
            }

            @Override
            public void loginCancel(int type) {
                onDestroy(activity);
                if (loginListener != null) {
                    loginListener.loginCancel(type);
                }
            }
        });
        sdkLogin.setWXListener(new WXListener() {
            @Override
            public void installWXAPP() {
                onDestroy(activity);
                if (loginListener != null) {
                    wxListener.installWXAPP();
                }
            }
        });
    }

    public void checkLogin(Activity activity, Intent intent) {

        if (intent == null) {
            SDKLogUtil.INSTANCE.e("checkLogin intent is null");
            onDestroy(activity);
            return;
        }
        if (intent.getExtras() == null) {
            SDKLogUtil.INSTANCE.e("checkLogin extras is null");
            onDestroy(activity);
            return;
        }

        int loginType = intent.getIntExtra(LOGIN_TYPE, 0);
        switch (loginType) {
            case SDKLoginType.TYPE_QQ:
                qqLogin();
                break;
            case SDKLoginType.TYPE_WB:
                wbLogin();
                break;
            case SDKLoginType.TYPE_WX:
                wxLogin();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        sdkLogin.result2Activity(requestCode, resultCode, data);
    }

    /**
     * 微信登录回调
     *
     * @param errCode
     * @param message
     */
    public void onActivityResultToWX(Activity activity, int errCode, String message) {
        switch (errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (loginListener != null) {
                    loginListener.loginAuthSuccess(SDKLoginType.TYPE_WX, "", message);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (loginListener != null) {
                    loginListener.loginCancel(SDKLoginType.TYPE_WX);
                }
                break;
            default:
                if (loginListener != null) {
                    loginListener.loginFail(SDKLoginType.TYPE_WX, "错误码：" + errCode);
                }
                break;
        }
        onDestroy(activity);
    }

    private void qqLogin() {
        sdkLogin.qqLogin();
    }

    private void wxLogin() {
        sdkLogin.wxLogin();
    }

    private void wbLogin() {
        sdkLogin.wbLogin();
    }

    private void showSDKProgress(boolean show) {
        sdkLogin.showProgressDialog(show);
    }

    /**
     * 摧毁本库的 SDKLoginActivity
     */
    private void onDestroy(Activity activity) {
        showSDKProgress(false);
        if (activity != null) {
            activity.finish();
        }
    }
}
