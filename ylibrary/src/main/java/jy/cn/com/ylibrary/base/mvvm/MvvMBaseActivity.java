package jy.cn.com.ylibrary.base.mvvm;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jy.cn.com.ylibrary.base.contract.BaseContract;
import jy.cn.com.ylibrary.base.mvvm.vm.BaseViewModel;

/**
 * @Author Administrator
 * @Date 2019/11/1-11:06
 * @TODO
 */
public abstract class MvvMBaseActivity<VM extends BaseViewModel, DBinding extends ViewDataBinding> extends MvvMBaseNoViewModelActivity<DBinding> {

    protected VM viewModel;

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

        //初始化viewModel
        viewModel = ViewModelProviders.of(this).get(initViewModelClass());
        viewModel.attachMode(initModel());

        //初始化dataBinding
        dataBinding = DataBindingUtil.setContentView(this, initLayoutID());
        dataBinding.setLifecycleOwner(this);
        dataBinding.setVariable(initViewModelId(), viewModel);

        initView(savedInstanceState);
    }

    /**
     * 初始化ViewModel
     *
     * @return
     */
    protected abstract Class<VM> initViewModelClass();

    /**
     * 初始化ViewModel的variableId
     *
     * @return
     */
    protected abstract int initViewModelId();

    /**
     * 初始化mode
     *
     * @return
     */
    protected abstract BaseContract.BaseModel initModel();


}
