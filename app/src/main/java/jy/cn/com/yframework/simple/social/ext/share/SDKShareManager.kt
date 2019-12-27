package jy.cn.com.yframework.simple.social.ext.share

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import jy.cn.com.socialsdklibrary.SDKShare
import jy.cn.com.socialsdklibrary.bean.SDKShareChannel
import jy.cn.com.socialsdklibrary.bean.ShareInfo
import jy.cn.com.socialsdklibrary.constant.SDKShareType
import jy.cn.com.socialsdklibrary.dialog.SDKShareDialog
import jy.cn.com.socialsdklibrary.listener.OnSocialSdkShareListener
import jy.cn.com.socialsdklibrary.wx.WXListener
import jy.cn.com.yframework.R
import jy.cn.com.yframework.simple.http.bean.ShareInfoVo
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/12/27-10:07
 * @TODO
 */
class SDKShareManager {

    private var sdkShareChannels: ArrayList<SDKShareChannel>? = null
    private var sdkShare: SDKShare? = null
    private var shareListener: OnSocialSdkShareListener? = null
    private var wxListener: WXListener? = null
    private val SHARE_TYPE = "shareType"
    private val SHARE_INFO = "shareInfo"
    private var shareType: Int = SDKShareType.TYPE_WX_CB

    fun setShareListener(shareListener: OnSocialSdkShareListener): SDKShareManager {
        this.shareListener = shareListener
        return this
    }

    fun setWXListener(wxListener: WXListener): SDKShareManager {
        this.wxListener = wxListener
        return this
    }

    fun requestShare(context: Context, shareType: Int, shareInfoVo: ShareInfoVo) {
        val intent = Intent(context, SDKShareActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(SHARE_TYPE, shareType)
        intent.putExtra(SHARE_INFO, shareInfoVo)
        context.startActivity(intent)
    }

    fun requestShare(context: Context, shareInfoVo: ShareInfoVo) {
        showShareDialog(context, shareInfoVo)
    }

    fun behavior(activity: Activity, savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            initShare(activity)
        }
    }

    private fun initShare(activity: Activity) {
        sdkShare = SDKShare(activity, object : OnSocialSdkShareListener {
            override fun shareSuccess(type: Int) {
                onDestroy(activity)
                shareListener?.shareSuccess(type)
            }

            override fun shareFail(type: Int, error: String?) {
                onDestroy(activity)
                shareListener?.shareFail(type, error)
            }

            override fun shareCancel(type: Int) {
                onDestroy(activity)
                shareListener?.shareCancel(type)
            }
        })
        sdkShare?.setWXListener {
            onDestroy(activity)
            wxListener?.installWXAPP()
        }
    }


    private fun prepareShareData(context: Context) {
        sdkShareChannels = ArrayList()
        sdkShareChannels?.add(SDKShareChannel(SDKShareType.TYPE_WX_FRIENDS, R.drawable.logo_wechat, context.getString(R.string.share_2wechat)))
        sdkShareChannels?.add(SDKShareChannel(SDKShareType.TYPE_QQ_FRIENDS, R.drawable.logo_qq, context.getString(R.string.share_2qq)))
        sdkShareChannels?.add(SDKShareChannel(SDKShareType.TYPE_WX_CB, R.drawable.logo_wechatmoments, context.getString(R.string.share_2wechatmoments)))
        sdkShareChannels?.add(SDKShareChannel(SDKShareType.TYPE_QQ_QZONE, R.drawable.logo_qzone, context.getString(R.string.share_2qzone)))
        sdkShareChannels?.add(SDKShareChannel(SDKShareType.TYPE_WB, R.drawable.logo_wb, context.getString(R.string.share_2wb)))
    }


    fun checkShare(activity: Activity, intent: Intent?) {

        if (intent == null) {
            YLogUtil.e("checkShare intent is null")
            onDestroy(activity)
            return
        }
        if (intent.extras == null) {
            YLogUtil.e("checkShare extras is null")
            onDestroy(activity)
            return
        }

        val shareInfoVo = intent.getParcelableExtra<ShareInfoVo>(SHARE_INFO)
        val shareType = intent.getIntExtra(SHARE_TYPE, 0)
        onShare(shareType, shareInfoVo)

    }


    private fun showShareDialog(context: Context, shareInfoVo: ShareInfoVo) {
        if (sdkShareChannels == null) {
            prepareShareData(context)
        }
        val sdkShareDialog = SDKShareDialog(context, R.style.public_Theme_dialog, sdkShareChannels, SDKShareDialog.OnSDKShareListener {
            requestShare(context, it.id, shareInfoVo)
        })
        sdkShareDialog.initSystemUI()
        sdkShareDialog.show()
    }

    private fun onShare(type: Int, shareInfoVo: ShareInfoVo) {

        shareType = type

        val shareInfo = ShareInfo()
        shareInfo.appName = shareInfoVo.appName
        //图片不能超过32K
        shareInfo.bitmap = shareInfoVo.bitmap
        shareInfo.imageUrl = shareInfoVo.imageUrl
        shareInfo.summary = shareInfoVo.summary
        shareInfo.title = shareInfoVo.title
        shareInfo.targetUrl = shareInfoVo.targetUrl

        sdkShare?.share(type, shareInfo)
    }

    fun doResultIntent(data: Intent?) {
        sdkShare?.doResultIntent(data)
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        sdkShare?.result2Activity(requestCode, resultCode, data)
    }


    fun onResultToWXShareSuccess(activity: Activity) {
        onDestroy(activity)
        shareListener?.shareSuccess(shareType)
    }

    fun onResultToWXShareCancel(activity: Activity) {
        onDestroy(activity)
        shareListener?.shareCancel(shareType)
    }

    fun onResultToWXShareFail(activity: Activity, errCode: Int) {
        onDestroy(activity)
        shareListener?.shareFail(shareType, "错误码：$errCode")
    }


    /**
     * 摧毁本库的 SDKShareActivity
     */
    private fun onDestroy(activity: Activity?) {
        activity?.finish()
    }
}