package jy.cn.com.socialsdklibrary.qq;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import jy.cn.com.socialsdklibrary.util.SDKLogUtil;

public class BaseUIListener implements IUiListener {


    BaseUIListener() {
        super();
    }


    @Override
    public void onComplete(Object response) {

        try {
            doComplete((JSONObject) response);
        } catch (Exception e) {
            SDKLogUtil.INSTANCE.e(e.getMessage());
        }
    }

    protected void doComplete(JSONObject values) {

        SDKLogUtil.INSTANCE.i("qq回调--doComplete", values);
    }

    @Override
    public void onError(UiError e) {

        SDKLogUtil.INSTANCE.e("qq回调--onError", e.errorCode, e.errorDetail, e.errorMessage);
    }

    @Override
    public void onCancel() {
        SDKLogUtil.INSTANCE.i("qq回调--onCancel");
    }


}
