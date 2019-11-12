package jy.cn.com.yframework.simple.social

import android.content.Context
import android.os.Bundle
import android.view.View
import jy.cn.com.socialsdklibrary.constant.SDKLoginType
import jy.cn.com.socialsdklibrary.listener.OnSocialSdkLoginListener
import jy.cn.com.yframework.R
import jy.cn.com.yframework.simple.social.ext.login.ExtLogin
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/11/12-14:40
 * @TODO
 */
class ExtLoginSimpleActivity : BaseActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, ExtLoginSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_login_activity

    override fun initUI(savedInstanceState: Bundle?) {

    }

    fun onClickLogin(view: View) {
        when (view.id) {
            R.id.login_2qq -> request(SDKLoginType.TYPE_QQ)
            R.id.login_2wx -> request(SDKLoginType.TYPE_WX)
            R.id.login_2wb -> request(SDKLoginType.TYPE_WB)
        }
    }

    private fun request(loginType: Int) {
        ExtLogin.getInstance().sdkLoginManager.setLoginListener(object : OnSocialSdkLoginListener {
            override fun loginAuthSuccess(type: Int, token: String?, info: String?) {
                YLogUtil.i("登录授权成功--类型：", type, "token", token, "info", info)
            }

            override fun loginFail(type: Int, error: String?) {
                YLogUtil.e("登录授权失败--类型：", type, "error", error)
            }

            override fun loginCancel(type: Int) {
                YLogUtil.i("取消登录--类型：", type)
            }
        }).setWXListener { YLogUtil.e("未安装微信") }.request(this, loginType)
    }
}