package jy.cn.com.ylibrary.base.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jy.cn.com.ylibrary.base.BaseLazyFragment;

/**
 * @Author Administrator
 * @Date 2019/11/1-10:36
 * @TODO
 */
public abstract class MvvMBaseNoViewModelLazyFragment<DBinding extends ViewDataBinding> extends BaseLazyFragment {

    protected DBinding dataBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, initLayoutID(), container, false);
        mView = dataBinding.getRoot();
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dataBinding != null) {
            dataBinding.unbind();
        }
    }

}
