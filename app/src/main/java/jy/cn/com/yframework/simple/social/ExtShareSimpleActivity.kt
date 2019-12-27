package jy.cn.com.yframework.simple.social

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import jy.cn.com.socialsdklibrary.constant.SDKShareType
import jy.cn.com.socialsdklibrary.listener.OnSocialSdkShareListener
import jy.cn.com.socialsdklibrary.wx.WXListener
import jy.cn.com.yframework.Constants
import jy.cn.com.yframework.R
import jy.cn.com.yframework.simple.http.bean.ShareInfoVo
import jy.cn.com.yframework.simple.social.ext.share.ExtShare
import jy.cn.com.yframework.simple.social.ext.share.SDKShareManager
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/11/12-14:40
 * @TODO
 */
class ExtShareSimpleActivity : BaseActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, ExtShareSimpleActivity::class.java)
        }
    }

    private var sdkShareManager: SDKShareManager? = null

    override fun initLayoutID(): Int = R.layout.simple_share_activity

    override fun initUI(savedInstanceState: Bundle?) {

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

    private fun toShare(shareType: Int) {
        getSdkShareManager().requestShare(this, shareType, getShareInfo())
    }

    private fun showShareDialog() {
        getSdkShareManager().requestShare(this, getShareInfo())
    }

    private fun getSdkShareManager(): SDKShareManager {
        if (sdkShareManager == null) {
            sdkShareManager = ExtShare.instance.sdkShareManager.setShareListener(object : OnSocialSdkShareListener {
                override fun shareSuccess(type: Int) {
                    YLogUtil.i("分享成功--类型：", type)
                }

                override fun shareFail(type: Int, error: String?) {
                    YLogUtil.e("分享失败--类型：", type, "error", error)
                }

                override fun shareCancel(type: Int) {
                    YLogUtil.i("取消分享--类型：", type)
                }

            }).setWXListener(WXListener {
                YLogUtil.e("未安装微信")
            })
        }
        return sdkShareManager!!
    }

    private fun getShareInfo(): ShareInfoVo {
        val shareInfoVo = ShareInfoVo()
        shareInfoVo.appName = getString(R.string.app_name)
        //图片不能超过32K
        shareInfoVo.bitmap = BitmapFactory.decodeResource(resources, R.drawable.share_icon)
        shareInfoVo.imageUrl = Constants.URL.SHARE_IMAGE_URL
        shareInfoVo.summary = "撩起来"
        shareInfoVo.title = getString(R.string.app_name)
        shareInfoVo.targetUrl = Constants.URL.SHARE_TARGET_URL

        return shareInfoVo
    }

}