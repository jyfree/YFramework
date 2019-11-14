package jy.cn.com.yframework.simple.social.ext.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

import jy.cn.com.yframework.Constants
import jy.cn.com.ylibrary.rxbus.RxBus
import jy.cn.com.ylibrary.rxbus.Subscribe
import jy.cn.com.ylibrary.rxbus.ThreadMode
import jy.cn.com.ylibrary.util.HandlerUtil

/**
 * @Author Administrator
 * @Date 2019/11/12-11:03
 * @TODO
 */
class SDKLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //不接受触摸屏事件
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        RxBus.getDefault().register(this)
        ExtLogin.instance.sdkLoginManager.behavior(this, savedInstanceState)
        initCompleteTime()
    }

    private fun initCompleteTime() {
        window.decorView.post {
            HandlerUtil.runOnUiThread(Runnable {
                ExtLogin.instance.sdkLoginManager.checkLogin(this@SDKLoginActivity, intent)
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ExtLogin.instance.sdkLoginManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }

    @SuppressLint("微信登录授权回调--成功")
    @Subscribe(code = Constants.RxBus.CODE_WX_LOGIN_AUTH_SUCCEED, threadMode = ThreadMode.MAIN)
    fun rxBusWXLoginSucceed(code: String) {
        ExtLogin.instance.sdkLoginManager.onResultToWXAuthSuccess(this, code)
    }

    @SuppressLint("微信登录授权回调--失败")
    @Subscribe(code = Constants.RxBus.CODE_WX_LOGIN_AUTH_FAIL, threadMode = ThreadMode.MAIN)
    fun rxBusWXLoginFail(errCode: Int) {
        ExtLogin.instance.sdkLoginManager.onResultToWXAuthFail(this, errCode)
    }

    @SuppressLint("微信登录授权回调--取消")
    @Subscribe(code = Constants.RxBus.CODE_WX_LOGIN_AUTH_CANCEL, threadMode = ThreadMode.MAIN)
    fun rxBusWXLoginCancel() {
        ExtLogin.instance.sdkLoginManager.onResultToWXAuthCancel(this)
    }
}
