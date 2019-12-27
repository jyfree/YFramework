package jy.cn.com.yframework.simple.social.ext.share

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
 * @Date 2019/12/27-10:31
 * @TODO
 */
class SDKShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //不接受触摸屏事件
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        RxBus.getDefault().register(this)
        ExtShare.instance.sdkShareManager.behavior(this, savedInstanceState)
        ExtShare.instance.sdkShareManager.doResultIntent(intent)
        initCompleteTime()
    }

    private fun initCompleteTime() {
        window.decorView.post {
            HandlerUtil.runOnUiThread(Runnable {
                ExtShare.instance.sdkShareManager.checkShare(this@SDKShareActivity, intent)
            })
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        ExtShare.instance.sdkShareManager.doResultIntent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ExtShare.instance.sdkShareManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }

    @SuppressLint("微信分享回调--成功")
    @Subscribe(code = Constants.RxBus.CODE_WX_SHARE_SUCCESS, threadMode = ThreadMode.MAIN)
    fun rxBusWXShareSucceed() {
        ExtShare.instance.sdkShareManager.onResultToWXShareSuccess(this)
    }

    @SuppressLint("微信分享回调--失败")
    @Subscribe(code = Constants.RxBus.CODE_WX_SHARE_FAIL, threadMode = ThreadMode.MAIN)
    fun rxBusWXShareFail(errCode: Int) {
        ExtShare.instance.sdkShareManager.onResultToWXShareFail(this, errCode)
    }

    @SuppressLint("微信分享回调--取消")
    @Subscribe(code = Constants.RxBus.CODE_WX_SHARE_CANCEL, threadMode = ThreadMode.MAIN)
    fun rxBusWXShareCancel() {
        ExtShare.instance.sdkShareManager.onResultToWXShareCancel(this)
    }
}