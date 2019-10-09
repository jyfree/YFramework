package jy.cn.com.socialsdklibrary.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import jy.cn.com.socialsdklibrary.SDKConfig;
import jy.cn.com.socialsdklibrary.constant.SDKLoginType;
import jy.cn.com.socialsdklibrary.listener.OnSocialSdkLoginListener;
import jy.cn.com.socialsdklibrary.util.SDKLogUtil;

/**
 * Administrator
 * created at 2016/2/17 15:20
 * TODO:qq登录
 */
public class QQChannelManager {

    //QQ登录
    Tencent mTencent;
    Context mContext;
    private OnSocialSdkLoginListener qqLoginListener;

    public QQChannelManager(Context context) {
        this.mContext = context;
        initQQLogin();

    }

    public void setLoginListener(OnSocialSdkLoginListener loginListener) {
        this.qqLoginListener = loginListener;
    }

    private void initQQLogin() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(SDKConfig.getQq_appID(), mContext);
        }

    }

    public void loginQQ() {
        if (null == mContext) return;
        //如果sessionid有效，则退出
        if (mTencent.isSessionValid())
            mTencent.logout(mContext);
        if (mTencent.isSupportSSOLogin((Activity) mContext)) {//支持SSO登陆
            mTencent.login((Activity) mContext, "all", loginListener);
        } else {
            mTencent.loginServerSide((Activity) mContext, "all", loginListener);
        }

    }


    /**
     * SSO 授权回调
     * 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
     * <p/>
     * onActivityResults处调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void result2Activity(int requestCode, int resultCode, Intent data) {

        if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN ||
                requestCode == com.tencent.connect.common.Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
    }


    private IUiListener loginListener = new BaseUIListener() {
        @Override
        protected void doComplete(JSONObject values) {
            SDKLogUtil.INSTANCE.i("QQ登录授权--成功--AuthorSwitch_SDK:", values);
            initOpenidAndToken(values);
        }

        @Override
        public void onError(UiError e) {
            super.onError(e);
            SDKLogUtil.INSTANCE.e("QQ登录授权--失败--errorCode", e.errorCode, "errorMessage", e.errorMessage);
            qqLoginListener.loginFail(SDKLoginType.TYPE_QQ, e.errorMessage);
        }

        @Override
        public void onCancel() {
            super.onCancel();
            SDKLogUtil.INSTANCE.i("QQ登录授权--取消");
            qqLoginListener.loginCancel(SDKLoginType.TYPE_QQ);
        }
    };


    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);

                qqLoginListener.loginAuthSuccess(SDKLoginType.TYPE_QQ, token, openId);
            } else {
                SDKLogUtil.INSTANCE.e("QQ登录授权--授权数据为空");
                qqLoginListener.loginFail(SDKLoginType.TYPE_QQ, "授权数据为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            SDKLogUtil.INSTANCE.e("QQ登录授权--解析授权信息失败");
            qqLoginListener.loginFail(SDKLoginType.TYPE_QQ, "解析授权失败");
        }
    }

}
