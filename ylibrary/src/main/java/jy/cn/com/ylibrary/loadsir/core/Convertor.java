package jy.cn.com.ylibrary.loadsir.core;


import jy.cn.com.ylibrary.loadsir.callback.Callback;

/**
 * Administrator
 * created at 2019/9/26 10:05
 * TODO:
 */
public interface Convertor<T> {
    Class<? extends Callback> map(T t);
}
