package jy.cn.com.yframework.simple.social

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import jy.cn.com.socialsdklibrary.SDKLogin
import jy.cn.com.socialsdklibrary.listener.OnSocialSdkLoginListener
import jy.cn.com.yframework.Constants
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.rxbus.RxBus
import jy.cn.com.ylibrary.rxbus.Subscribe
import jy.cn.com.ylibrary.rxbus.ThreadMode
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/9/29-18:38
 * @TODO
 */
class LoginSimpleActivity : BaseActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, LoginSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_login_activity

    override fun initUI(savedInstanceState: Bundle?) {
        RxBus.getDefault().register(this)
    }


    private val sdkLogin by lazy {
        val sdkLogin = SDKLogin(this, object : OnSocialSdkLoginListener {
            override fun loginAuthSuccess(type: Int, token: String?, info: String?) {
                YLogUtil.i("登录授权成功--类型：", type, "token", token, "info", info)
                showSDKProgress(false)
            }

            override fun loginFail(type: Int, error: String?) {
                YLogUtil.e("登录授权失败--类型：", type, "error", error)
                showSDKProgress(false)
            }

            override fun loginCancel(type: Int) {
                YLogUtil.i("取消登录--类型：", type)
                showSDKProgress(false)
            }
        })
        sdkLogin.setWXListener {
            YLogUtil.e("未安装微信")
            showSDKProgress(false)
        }
        sdkLogin
    }

    fun onClickLogin(view: View) {
        when (view.id) {
            R.id.login_2qq -> qqLogin()
            R.id.login_2wx -> wxLogin()
            R.id.login_2wb -> wbLogin()
        }
    }

    override fun onResume() {
        super.onResume()
        showSDKProgress(false)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        sdkLogin.result2Activity(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }

    private fun showSDKProgress(show: Boolean) {
        sdkLogin.showProgressDialog(show)
    }


    private fun qqLogin() {
        showSDKProgress(true)
        sdkLogin.qqLogin()
    }

    private fun wxLogin() {
        showSDKProgress(true)
        sdkLogin.wxLogin()
    }

    private fun wbLogin() {
        showSDKProgress(true)
        sdkLogin.wbLogin()
    }

    @SuppressLint("微信登录授权回调--成功")
    @Subscribe(code = Constants.RxBus.CODE_WX_LOGIN_AUTH_SUCCEED, threadMode = ThreadMode.MAIN)
    fun rxBusWXLoginSucceed(code: String) {
        YLogUtil.i("微信登录授权成功--code", code)
        showSDKProgress(false)
    }

    @SuppressLint("微信登录授权回调--失败")
    @Subscribe(code = Constants.RxBus.CODE_WX_LOGIN_AUTH_FAIL, threadMode = ThreadMode.MAIN)
    fun rxBusWXLoginFail(errCode: Int) {
        YLogUtil.i("微信登录授权失败--errCode", errCode)
        showSDKProgress(false)
    }

    @SuppressLint("微信登录授权回调--取消")
    @Subscribe(code = Constants.RxBus.CODE_WX_LOGIN_AUTH_CANCEL, threadMode = ThreadMode.MAIN)
    fun rxBusWXLoginCancel() {
        YLogUtil.i("微信登录授权取消")
        showSDKProgress(false)
    }
}