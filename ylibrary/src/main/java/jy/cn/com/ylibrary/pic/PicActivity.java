package jy.cn.com.ylibrary.pic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * @Author Administrator
 * @Date 2019/10/17-17:29
 * @TODO 读取摄像头|相册图片 activity，需要在manifest注册
 */
public class PicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不接受触摸屏事件
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Pic.getInstance().getPicManager().behavior(this, getIntent(), savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Pic.getInstance().getPicManager().onActivityResult(this, requestCode, resultCode, data);
    }
}
