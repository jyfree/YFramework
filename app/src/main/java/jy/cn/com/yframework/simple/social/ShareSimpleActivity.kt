package jy.cn.com.yframework.simple.social

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import jy.cn.com.socialsdklibrary.constant.SDKShareType
import jy.cn.com.yframework.R
import jy.cn.com.yframework.simple.social.helper.ShareHelper
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.util.ActivityUtils

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
}