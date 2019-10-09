package jy.cn.com.yframework.simple.social.helper

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import jy.cn.com.socialsdklibrary.SDKShare
import jy.cn.com.socialsdklibrary.bean.SDKShareChannel
import jy.cn.com.socialsdklibrary.bean.ShareInfo
import jy.cn.com.socialsdklibrary.constant.SDKShareType
import jy.cn.com.socialsdklibrary.dialog.SDKShareDialog
import jy.cn.com.socialsdklibrary.listener.OnSocialSdkShareListener
import jy.cn.com.yframework.Constants
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.util.YLogUtil


/**

 * @Author Administrator
 * @Date 2019/1/3-14:15
 * @TODO 分享
 */
class ShareHelper(private var context: Context) {

    private lateinit var sdkShareChannels: MutableList<SDKShareChannel>
    private lateinit var sdkShare: SDKShare
    private var success: (() -> Unit)? = null

    private var shareDialog: SDKShareDialog? = null

    fun setShareSuccess(success: () -> Unit) {
        this.success = success
    }

    fun initShare() {
        sdkShare = SDKShare(context, object : OnSocialSdkShareListener {
            override fun shareSuccess(type: Int) {
                YLogUtil.i("分享成功--类型：", type)
                success?.invoke()
            }

            override fun shareFail(type: Int, error: String?) {
                YLogUtil.e("分享失败--类型：", type, "error", error)
            }

            override fun shareCancel(type: Int) {
                YLogUtil.i("取消分享--类型：", type)
            }
        })
        sdkShare.setWXListener {
            YLogUtil.e("未安装微信")
        }
        prepareShareData()
    }

    private fun prepareShareData() {
        sdkShareChannels = ArrayList()
        sdkShareChannels.add(SDKShareChannel(SDKShareType.TYPE_WX_FRIENDS, R.drawable.logo_wechat, context.getString(R.string.share_2wechat)))
        sdkShareChannels.add(SDKShareChannel(SDKShareType.TYPE_QQ_FRIENDS, R.drawable.logo_qq, context.getString(R.string.share_2qq)))
        sdkShareChannels.add(SDKShareChannel(SDKShareType.TYPE_WX_CB, R.drawable.logo_wechatmoments, context.getString(R.string.share_2wechatmoments)))
        sdkShareChannels.add(SDKShareChannel(SDKShareType.TYPE_QQ_QZONE, R.drawable.logo_qzone, context.getString(R.string.share_2qzone)))
        sdkShareChannels.add(SDKShareChannel(SDKShareType.TYPE_WB, R.drawable.logo_wb, context.getString(R.string.share_2wb)))
    }


    fun showShareDialog() {
        if (shareDialog == null) {
            shareDialog = SDKShareDialog(context, R.style.public_Theme_dialog, sdkShareChannels, SDKShareDialog.OnSDKShareListener { sdkShareChannel ->
                shareMsg(sdkShareChannel.id)
            })
        }
        if (shareDialog?.isShowing == false) {
            shareDialog?.show()
        }
    }

    fun shareMsg(type: Int) {

        val shareInfo = ShareInfo()
        shareInfo.appName = context.getString(R.string.app_name)
        //图片不能超过32K
        shareInfo.bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.share_icon)
        shareInfo.imageUrl = Constants.URL.SHARE_IMAGE_URL
        shareInfo.summary = "撩起来"
        shareInfo.title = context.getString(R.string.app_name)
        shareInfo.targetUrl = Constants.URL.SHARE_TARGET_URL

        sdkShare.share(type, shareInfo)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        sdkShare.result2Activity(requestCode, resultCode, data)
    }

    fun doResultIntent(data: Intent?) {
        sdkShare.doResultIntent(data)
    }
}