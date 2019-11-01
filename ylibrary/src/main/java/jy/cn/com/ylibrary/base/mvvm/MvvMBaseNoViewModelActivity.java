package jy.cn.com.ylibrary.base.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jy.cn.com.ylibrary.base.BaseAppCompatActivity;

/**
 * @Author Administrator
 * @Date 2019/11/1-10:36
 * @TODO
 */
public abstract class MvvMBaseNoViewModelActivity<DBinding extends ViewDataBinding> extends BaseAppCompatActivity {

    protected DBinding dataBinding;

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.setContentView(this, initLayoutID());
        initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBinding != null) {
            dataBinding.unbind();
        }
    }

    protected abstract void initView(@Nullable Bundle savedInstanceState);
}
