package jy.cn.com.ylibrary.base.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jy.cn.com.ylibrary.base.contract.BaseContract;
import jy.cn.com.ylibrary.base.viewmodel.BaseViewModel;

/**
 * @Author Administrator
 * @Date 2019/11/1-11:06
 * @TODO
 */
public abstract class MvvMBaseActivity<VM extends BaseViewModel, DBinding extends ViewDataBinding> extends MvvMBaseNoModelActivity<DBinding> {

    protected VM viewModel;

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.setContentView(this, initLayoutID());
        dataBinding.setLifecycleOwner(this);
        viewModel = initViewModel();
        viewModel.attachMode(initModel());
        initView(savedInstanceState);
    }

    /**
     * 初始化ViewModel
     *
     * @return
     */
    protected abstract VM initViewModel();

    /**
     * 初始化mode
     *
     * @return
     */
    protected abstract BaseContract.BaseModel initModel();

}
