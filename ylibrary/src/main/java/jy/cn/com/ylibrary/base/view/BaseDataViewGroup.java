package jy.cn.com.ylibrary.base.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @Author Administrator
 * @Date 2019/11/13-11:24
 * @TODO
 */
public abstract class BaseDataViewGroup<D> extends BaseViewGroup {
    public D data;

    public BaseDataViewGroup(Context context) {
        super(context);
    }

    public BaseDataViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseDataViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(D data) {
        if (data != null) {
            this.data = data;
        }
    }
}
