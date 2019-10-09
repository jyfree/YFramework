package jy.cn.com.yframework.simple.thread;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jy.cn.com.yframework.R;
import jy.cn.com.ylibrary.base.BaseActivity;
import jy.cn.com.ylibrary.thread.ThreadCall;
import jy.cn.com.ylibrary.thread.ThreadCondition;
import jy.cn.com.ylibrary.util.ActivityUtils;
import jy.cn.com.ylibrary.util.HandlerUtil;

/**
 * @Author Administrator
 * @Date 2019/9/25-18:28
 * @TODO 线程测试
 */
public class ThreadSimpleActivity extends BaseActivity {


    Button btnStartThread;
    Button btnSendThread;
    TextView tv_msg;
    ThreadCondition threadCondition;

    public static void startAct(Context context) {
        ActivityUtils.startActivity(context, ThreadSimpleActivity.class);
    }


    @Override
    protected int initLayoutID() {
        return R.layout.simple_thread_activity;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        tv_msg = findViewById(R.id.textView1);
        btnStartThread = findViewById(R.id.btnStartThread);
        btnSendThread = findViewById(R.id.btnSendThread);
    }

    @Override
    protected Object initClassTag() {
        return ThreadSimpleActivity.class.getSimpleName();
    }

    public void threadClick(View view) {
        switch (view.getId()) {
            case R.id.btnStartThread:
                tv_msg.setText("启动线程--等待通知");
                btnStartThread.setEnabled(false);
                btnSendThread.setEnabled(false);
                startThread();
                delay();
                break;
            case R.id.btnSendThread:
                if (null == threadCondition) return;
                tv_msg.setText("发送通知--继续执行线程");
                threadCondition.signal();
                break;
        }
    }

    public void startThread() {
        threadCondition = new ThreadCondition();
        threadCondition.setCall(new ThreadCall() {
            @Override
            public void apply() {

                HandlerUtil.INSTANCE.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnStartThread.setEnabled(true);
                        btnSendThread.setEnabled(true);
                        tv_msg.setText("执行线程");
                    }
                });

            }
        });
        threadCondition.start();

    }

    private void delay() {
        HandlerUtil.INSTANCE.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnSendThread.setEnabled(true);
            }
        }, 2);
    }
}
