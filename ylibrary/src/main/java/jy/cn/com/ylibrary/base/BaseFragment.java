package jy.cn.com.ylibrary.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.jetbrains.annotations.NotNull;

import jy.cn.com.ylibrary.base.contract.BaseContract;
import jy.cn.com.ylibrary.helper.LoadSirHelper;
import jy.cn.com.ylibrary.util.ToastUtil;
import jy.cn.com.ylibrary.util.YLogUtil;


/**
 * Administrator
 * created at 2018/11/21 11:24
 * TODO:fragment基类
 */
public abstract class BaseFragment extends RxFragment implements BaseContract.BaseView {

    //启动时间
    public long startFragmentTime;
    private LoadSirHelper loadSirHelper = new LoadSirHelper();
    protected View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startFragmentTime = System.currentTimeMillis();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(initLayoutID(), container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view, savedInstanceState);
        YLogUtil.INSTANCE.d(initClassTag(), "启动时长(ms)", System.currentTimeMillis() - startFragmentTime);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtil.cancelToast();
    }


    /**
     * 初始化布局ID
     */
    protected abstract int initLayoutID();

    /**
     * 初始化UI
     */
    protected abstract void initUI(View view, @Nullable Bundle savedInstanceState);

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
        loadSirHelper.showPopWindowLoading(getContext(), getView(), flag);
    }

    @Override
    public void showLoading(@NotNull Object target, boolean flag) {
        loadSirHelper.showLoading(target, flag);
    }
}
