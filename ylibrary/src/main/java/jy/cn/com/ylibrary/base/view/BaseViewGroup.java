package jy.cn.com.ylibrary.base.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Author Administrator
 * @Date 2019/11/13-10:08
 * @TODO ViewGroup基类
 */
public abstract class BaseViewGroup extends ViewGroup {

    public BaseViewGroup(Context context) {
        this(context, null);
    }

    public BaseViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, initResLayoutId(), this);
        initUI(view);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public abstract int initResLayoutId();

    public abstract void initUI(View view);


}
