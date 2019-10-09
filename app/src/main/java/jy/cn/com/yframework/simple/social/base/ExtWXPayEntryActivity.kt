package jy.cn.com.yframework.simple.social.base

import jy.cn.com.socialsdklibrary.wx.SDKWXPayEntryActivity
import jy.cn.com.yframework.Constants
import jy.cn.com.ylibrary.rxbus.RxBus
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/9/29-18:47
 * @TODO 实现微信支付需要继承此类
 */
abstract class ExtWXPayEntryActivity : SDKWXPayEntryActivity() {
    override fun paySucceed() {
        //TODO 支付成功，发送RxBus
        YLogUtil.i("微信支付--成功")
        RxBus.getDefault().send(Constants.RxBus.CODE_WX_PAY_SUCCESS)
    }

    override fun payCancel() {
        //TODO 取消支付
        YLogUtil.i("微信支付--取消")
    }

    override fun payFail(errCode: Int) {
        //TODO 支付失败 errCode
        YLogUtil.e("微信支付--失败--code", errCode)
    }


}