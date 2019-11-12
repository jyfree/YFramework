package jy.cn.com.yframework.simple.social

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import jy.cn.com.socialsdklibrary.constant.SDKShareType
import jy.cn.com.yframework.Constants
import jy.cn.com.yframework.R
import jy.cn.com.yframework.simple.social.helper.ShareHelper
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.rxbus.RxBus
import jy.cn.com.ylibrary.rxbus.Subscribe
import jy.cn.com.ylibrary.rxbus.ThreadMode
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/9/29-19:02
 * @TODO
 */
class ShareSimpleActivity : BaseActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, ShareSimpleActivity::class.java)
        }
    }

    //分享
    private val shareHelper by lazy {
        val share = ShareHelper(this)
        share.initShare()
        share.setShareSuccess {
            //TODO 分享成功
        }
        share
    }

    override fun initLayoutID(): Int = R.layout.simple_share_activity

    override fun initUI(savedInstanceState: Bundle?) {
        RxBus.getDefault().register(this)
        shareHelper.doResultIntent(intent)
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        shareHelper.doResultIntent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        shareHelper.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }

    fun onClickShare(view: View) {
        when (view.id) {
            R.id.share_2qq -> toShare(SDKShareType.TYPE_QQ_FRIENDS)
            R.id.share_2qzone -> toShare(SDKShareType.TYPE_QQ_QZONE)
            R.id.share_2wechat -> toShare(SDKShareType.TYPE_WX_FRIENDS)
            R.id.share_2wechatmoments -> toShare(SDKShareType.TYPE_WX_CB)
            R.id.share_2wb -> toShare(SDKShareType.TYPE_WB)
            R.id.share_all -> showShareDialog()
        }
    }

    private fun showShareDialog() {
        shareHelper.showShareDialog()
    }

    /**
     * type详情见 SDKShareType
     */
    private fun toShare(type: Int) {
        shareHelper.shareMsg(type)
    }

    @SuppressLint("微信分享回调--成功")
    @Subscribe(code = Constants.RxBus.CODE_WX_SHARE_SUCCESS, threadMode = ThreadMode.MAIN)
    fun rxBusWXShareSucceed() {
        YLogUtil.i("微信分享--成功")
    }

    @SuppressLint("微信分享回调--失败")
    @Subscribe(code = Constants.RxBus.CODE_WX_SHARE_FAIL, threadMode = ThreadMode.MAIN)
    fun rxBusWXShareFail(errCode: Int) {
        YLogUtil.i("微信分享--失败--errCode", errCode)
    }

    @SuppressLint("微信分享回调--取消")
    @Subscribe(code = Constants.RxBus.CODE_WX_SHARE_CANCEL, threadMode = ThreadMode.MAIN)
    fun rxBusWXShareCancel() {
        YLogUtil.i("微信分享--取消")
    }
}