package jy.cn.com.yframework.simple.social.ext.login;

/**
 * @Author Administrator
 * @Date 2019/11/12-14:14
 * @TODO
 */
public class ExtLogin {
    private SDKLoginManager sdkLoginManager;

    private ExtLogin() {
        sdkLoginManager = new SDKLoginManager();
    }

    public static synchronized ExtLogin getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static final ExtLogin instance = new ExtLogin();
    }

    public SDKLoginManager getSDKLoginManager() {
        return sdkLoginManager;
    }
}
