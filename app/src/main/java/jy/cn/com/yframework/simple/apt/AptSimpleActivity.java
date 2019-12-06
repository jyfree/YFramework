package jy.cn.com.yframework.simple.apt;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import jy.cn.com.aptlibrary.AptKnife;
import jy.cn.com.aptlibrary.annotation.BindView;
import jy.cn.com.yframework.R;
import jy.cn.com.ylibrary.base.BaseActivity;
import jy.cn.com.ylibrary.util.ActivityUtils;

/**
 * @Author Administrator
 * @Date 2019/12/6-18:24
 * @TODO apt示例
 */
public class AptSimpleActivity extends BaseActivity {

    public static void startAct(Context context) {
        ActivityUtils.startActivity(context, AptSimpleActivity.class);
    }


    @BindView(R.id.textView)
    TextView tv_msg;

    @Override
    protected int initLayoutID() {
        return R.layout.simple_apt_activity;
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        AptKnife.bind(this);
        tv_msg.setText("apt测试");
    }
}
