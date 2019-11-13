package jy.cn.com.ylibrary.base.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.trello.rxlifecycle2.LifecycleProvider;

import jy.cn.com.ylibrary.base.contract.BaseContract;

/**
 * @Author Administrator
 * @Date 2019/11/13-11:29
 * @TODO
 */
public abstract class BaseModelViewGroup<M extends BaseContract.BaseModel> extends BaseViewGroup {

    public M model;
    public LifecycleProvider lifecycleProvider;

    public BaseModelViewGroup(Context context) {
        super(context);
    }

    public BaseModelViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseModelViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setModel(M model) {
        this.model = model;
    }

    public void setLifecycleProvider(LifecycleProvider lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }
}
