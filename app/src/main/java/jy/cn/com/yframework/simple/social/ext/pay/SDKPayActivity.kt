package jy.cn.com.yframework.simple.social.ext.pay

import android.annotation.SuppressLint
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
 * @Date 2019/12/26-16:52
 * @TODO
 */
class SDKPayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //不接受触摸屏事件
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        RxBus.getDefault().register(this)
        ExtPay.instance.sdkPayManager.behavior(this, savedInstanceState)
        initCompleteTime()
    }

    private fun initCompleteTime() {
        window.decorView.post {
            HandlerUtil.runOnUiThread(Runnable {
                ExtPay.instance.sdkPayManager.checkPay(this@SDKPayActivity, intent)
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }

    @SuppressLint("微信支付回调--成功")
    @Subscribe(code = Constants.RxBus.CODE_WX_PAY_SUCCESS, threadMode = ThreadMode.MAIN)
    fun rxBusWXPaySucceed() {
        ExtPay.instance.sdkPayManager.onResultToWXPaySuccess(this, intent.getStringExtra(ExtPay.instance.sdkPayManager.ORDER_ID))
    }

    @SuppressLint("微信支付回调--失败")
    @Subscribe(code = Constants.RxBus.CODE_WX_PAY_FAIL, threadMode = ThreadMode.MAIN)
    fun rxBusWXPayFail(errCode: Int) {
        ExtPay.instance.sdkPayManager.onResultToWXPayFail(this, errCode)
    }

    @SuppressLint("微信支付回调--取消")
    @Subscribe(code = Constants.RxBus.CODE_WX_PAY_CANCEL, threadMode = ThreadMode.MAIN)
    fun rxBusWXPayCancel() {
        ExtPay.instance.sdkPayManager.onResultToWXPayCancel(this)
    }

}