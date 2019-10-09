package jy.cn.com.yframework.simple.social.base

import jy.cn.com.socialsdklibrary.wx.SDKWXEntryActivity
import jy.cn.com.yframework.Constants
import jy.cn.com.ylibrary.rxbus.RxBus
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/9/29-18:45
 * @TODO 实现微信登录|分享需要继承此类
 */
abstract class ExtWXEntryActivity : SDKWXEntryActivity() {

    override fun authSucceed(code: String?) {
        //TODO 登录授权成功，发送RxBus 请求微信登录接口
        YLogUtil.i("微信登录授权--成功--code", code)
        RxBus.getDefault().send(Constants.RxBus.CODE_REQUEST_WX_LOGIN, code)
    }

    override fun authCancel() {
        //TODO 取消登录授权
        YLogUtil.i("微信登录授权--取消")
    }

    override fun authFail(code: Int) {
        //TODO 授权失败 code
        YLogUtil.e("微信登录授权--失败--code", code)
    }

    override fun shareSucceed() {
        //TODO 分享微信成功，发送RxBus
        YLogUtil.i("微信分享--成功")
        RxBus.getDefault().send(Constants.RxBus.CODE_WX_SHARE_SUCCESS)
    }

    override fun shareCancel() {
        //TODO 取消分享
        YLogUtil.i("微信分享--取消")

    }

    override fun shareFail(code: Int) {
        //TODO 分享失败 code
        YLogUtil.e("微信分享--失败--code", code)

    }
}