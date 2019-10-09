package jy.cn.com.yframework.simple.loading.callback

import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.loadsir.callback.Callback

/**
 * Administrator
 * created at 2018/12/3 9:12
 * description:LoadSir错误页
 */
class ErrorCallback : Callback() {
    override fun onCreateView(): Int = R.layout.loadsir_callback_error
}