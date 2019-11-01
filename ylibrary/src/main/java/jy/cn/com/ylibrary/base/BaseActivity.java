package jy.cn.com.ylibrary.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.components.RxActivity;

import org.jetbrains.annotations.NotNull;

import jy.cn.com.ylibrary.BaseApplication;
import jy.cn.com.ylibrary.base.contract.BaseContract;
import jy.cn.com.ylibrary.helper.LoadSirHelper;
import jy.cn.com.ylibrary.util.ToastUtil;
import jy.cn.com.ylibrary.util.YLogUtil;


/**
 * Administrator
 * created at 2018/11/21 11:24
 * TODO:activity基类
 */
public abstract class BaseActivity extends RxActivity implements BaseContract.BaseView {

    //启动时间
    public long startActivityTime;
    private LoadSirHelper loadSirHelper = new LoadSirHelper();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        startActivityTime = System.currentTimeMillis();
        setContentView(initLayoutID());
        BaseApplication.getInstance().addActivity(this);
        initUI(savedInstanceState);
        YLogUtil.INSTANCE.dFormat("activity创建：%s---时长：%s(ms)", initClassTag(), System.currentTimeMillis() - startActivityTime);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().removeActivity(this);
        ToastUtil.cancelToast();
        YLogUtil.INSTANCE.dFormat("activity销毁：%s", initClassTag());
    }

    /**
     * 初始化布局ID
     */
    protected abstract int initLayoutID();

    /**
     * 初始化UI
     */
    protected abstract void initUI(@Nullable Bundle savedInstanceState);

    /**
     * 获取tag
     *
     * @return
     */
    protected abstract Object initClassTag();


    @NonNull
    @Override
    public LifecycleProvider getLifecycleProvider() {
        return this;
    }

    @Override
    public void showPopWindowLoading(boolean flag) {
        loadSirHelper.showPopWindowLoading(this, flag);
    }

    @Override
    public void showLoading(@NotNull Object target, boolean flag) {
        loadSirHelper.showLoading(target, flag);
    }
}
