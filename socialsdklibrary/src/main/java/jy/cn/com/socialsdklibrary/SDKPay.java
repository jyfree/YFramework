package jy.cn.com.socialsdklibrary;

import android.app.ProgressDialog;
import android.content.Context;

import jy.cn.com.socialsdklibrary.alipay.AliPayManager;
import jy.cn.com.socialsdklibrary.alipay.PayBean;
import jy.cn.com.socialsdklibrary.listener.OnSocialSdkPayListener;
import jy.cn.com.socialsdklibrary.util.SDKLogUtil;
import jy.cn.com.socialsdklibrary.wx.WXListener;
import jy.cn.com.socialsdklibrary.wx.WXPayBean;
import jy.cn.com.socialsdklibrary.wx.WXPayManager;

/**
 * Administrator
 * created at 2016/2/24 11:35
 * TODO:第三方支付
 */
public class SDKPay {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private OnSocialSdkPayListener listener;

    public SDKPay(Context context, OnSocialSdkPayListener listener) {
        this.mContext = context;
        this.listener = listener;
        initProgressDialog();
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(mContext.getString(R.string.social_sdk_pay_prompt));
        mProgressDialog.setCancelable(false);
    }

    public void showProgressDialog(boolean isShow) {
        try {
            if (mProgressDialog == null || mContext == null)
                return;
            if (isShow) {
                mProgressDialog.show();
            } else {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            SDKLogUtil.e(e.getMessage());
        }
    }

    //**********************************支付宝支付*********************************************

    /**
     * 支付宝支付
     *
     * @param payBean 订单参数
     */
    public void aliPay(String orderId, PayBean payBean) {
        getAliPayManager(orderId).pay(payBean);
    }

    public void aliPay(String orderId, String payInfo) {
        getAliPayManager(orderId).pay(payInfo);
    }

    private AliPayManager aliPayManager;

    private AliPayManager getAliPayManager(final String orderId) {
        //支付宝支付
        if (aliPayManager == null) {
            aliPayManager = new AliPayManager(mContext, new OnSocialSdkPayListener() {
                @Override
                public void paySuccess(int type, String id) {
                    listener.paySuccess(type, orderId);
                }

                @Override
                public void payFail(int type, String error) {
                    listener.payFail(type, error);
                }

                @Override
                public void payCancel(int type) {
                    listener.payCancel(type);
                }
            });
        }
        return aliPayManager;
    }


    //**********************************微信支付*********************************

    private WXPayManager wxPayManager;

    private void initWXPayManager() {
        if (wxPayManager == null) {
            wxPayManager = new WXPayManager(mContext);
        }
    }

    /**
     * 微信支付
     *
     * @param wxPayBean
     */
    public void wxPay(WXPayBean wxPayBean) {
        initWXPayManager();
        wxPayManager.genPayReq(wxPayBean);
        wxPayManager.pay();
    }

    /**
     * 本地调起微信支付测试示例
     */
    public void wxPayTest() {
        initWXPayManager();
        wxPayManager.localPayTask();
    }

    /**
     * 检查微信是否安装回调
     * @param listener
     */
    public void setWxListener(WXListener listener) {
        initWXPayManager();
        wxPayManager.setListener(listener);
    }

}
