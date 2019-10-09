package jy.cn.com.yframework.simple.loading;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jy.cn.com.yframework.R;
import jy.cn.com.ylibrary.base.BaseActivity;
import jy.cn.com.ylibrary.util.ActivityUtils;
import jy.cn.com.ylibrary.util.HandlerUtil;

/**
 * @Author Administrator
 * @Date 2019/9/26-11:38
 * @TODO
 */
public class LoadingSimpleActivity extends BaseActivity {

    private TextView tv_target;
    private Button btn_showDefaultLoading;
    private Button btn_showPopWindowsLoading;

    public static void startAct(Context context) {
        ActivityUtils.startActivity(context, LoadingSimpleActivity.class);
    }

    @Override
    protected Object initClassTag() {
        return LoadingSimpleActivity.class.getSimpleName();
    }


    @Override
    protected int initLayoutID() {
        return R.layout.simple_loading_activity;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        tv_target = findViewById(R.id.tv_target);
        btn_showDefaultLoading = findViewById(R.id.showDefaultLoading);
        btn_showPopWindowsLoading = findViewById(R.id.showPopWindowsLoading);
    }


    public void stopLoading(View view) {
        switch (view.getId()) {
            case R.id.showDefaultLoading:
                setBtnEnabled(false);
                showLoading(tv_target, true);
                HandlerUtil.INSTANCE.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showLoading(tv_target, false);
                        setBtnEnabled(true);
                    }
                }, 5);
                break;
            case R.id.showPopWindowsLoading:

                setBtnEnabled(false);
                showPopWindowLoading(true);
                HandlerUtil.INSTANCE.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showPopWindowLoading(false);
                        setBtnEnabled(true);
                    }
                }, 5);

                break;
        }
    }

    private void setBtnEnabled(boolean isEnabled) {
        btn_showDefaultLoading.setEnabled(isEnabled);
        btn_showPopWindowsLoading.setEnabled(isEnabled);
    }
}
