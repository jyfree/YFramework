package jy.cn.com.yframework.simple.social

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import jy.cn.com.socialsdklibrary.SDKPay
import jy.cn.com.socialsdklibrary.listener.OnSocialSdkPayListener
import jy.cn.com.socialsdklibrary.wx.WXPayBean
import jy.cn.com.yframework.Constants
import jy.cn.com.yframework.R
import jy.cn.com.yframework.simple.http.bean.WXPayVo
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.rxbus.RxBus
import jy.cn.com.ylibrary.rxbus.Subscribe
import jy.cn.com.ylibrary.rxbus.ThreadMode
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/9/29-18:32
 * @TODO
 */
class PaySimpleActivity : BaseActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, PaySimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_pay_activity

    override fun initUI(savedInstanceState: Bundle?) {
        RxBus.getDefault().register(this)
    }

    private val sdkPay by lazy {
        val sdkPay = SDKPay(this, object : OnSocialSdkPayListener {
            override fun paySuccess(type: Int, orderId: String?) {
                YLogUtil.i("支付成功--类型：", type, "orderId", orderId)
                showSDKProgress(false)
            }

            override fun payFail(type: Int, error: String?) {
                YLogUtil.e("支付失败--类型：", type, "error", error)
                showSDKProgress(false)
            }

            override fun payCancel(type: Int) {
                YLogUtil.i("取消支付--类型：", type)
                showSDKProgress(false)
            }
        })
        sdkPay.setWxListener {
            YLogUtil.e("未安装微信")
            showSDKProgress(false)
        }
        sdkPay
    }

    fun onClickPay(view: View) {
        when (view.id) {
            R.id.pay_2ali -> {
                val payInfo = "alipay_sdk=alipay-sdk-java-3.4.49.ALL&app_id=2019010962861643&biz_content=%7B%22body%22%3A%22WOWO%E8%AF%AD%E9%9F%B3%E9%87%91%E5%B8%81%22%2C%22out_trade_no%22%3A%221699%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22WOWO%E8%AF%AD%E9%9F%B3%E9%87%91%E5%B8%81%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2250.0%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2Ftestservice.wowolive99.com%2F%2Fpay%2Fnotify%2Falipay.do&sign=wLmxxtlfRUvypLM3y1eoGyggkFZH5ZQHuMH1e1r2rET%2BtKRmhE8gJFI89cctCeGMB%2Bns3bdw9Ay%2BLrMxcnroEPTbRD69M0i80jcG84t%2BjA%2BO1PlAstfyKAr4tnw5n4GkkGQGuTx61wZyYWDJ56fIacoJDVlXGm2PnJvRJZYyHApJGE%2BbORE0GVTfzHvWxH%2BMLklARgLViDPkGvGXXmKL%2Fif%2By3o9C8HfnHYINPaLENBY0BCjm%2FkxlDD7nO65uX4tBlCbDYDKl3196xk2lvDd8nLtM5222Y5tDjL3900rOwgAHVA81qirBk4IQwBH8aavr%2F6ZhlF%2FUQGJ619IKXVXwA%3D%3D&sign_type=RSA2×tamp=2019-10-08+11%3A07%3A30&version=1.0"
                aliPlayOrderSuccess("1699", payInfo)
            }
            R.id.pay_2wx -> {
                wxPayOrderSuccess(null)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showSDKProgress(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }

    private fun showSDKProgress(show: Boolean) {
        sdkPay.showProgressDialog(show)
    }

    /**
     * 下单成功，调起支付宝
     */
    private fun aliPlayOrderSuccess(orderId: String, payInfo: String) {
        showSDKProgress(true)
        sdkPay.aliPay(orderId, payInfo)
    }

    /**
     * 下单成功，调起微信
     */
    private fun wxPayOrderSuccess(wxPayVo: WXPayVo?) {
        showSDKProgress(true)
        val wxPayBean = WXPayBean()
        wxPayBean.partnerId = "1313981201"//wxPayVo.partnerId
        wxPayBean.prepayId = "wx0817275229598459db05841f1027564800"//wxPayVo.prepayId
        wxPayBean.packageValue = "Sign=WXPay"//wxPayVo.packageValue
        wxPayBean.nonceStr = "jx4ia8vddbc9h646fu8ggjm6cmdk4y2k"//wxPayVo.nonceStr
        wxPayBean.timeStamp = "1570526872"//wxPayVo.timeStamp
        wxPayBean.sign = "0B47048F589697A9D1A467B8B2EBF80A"//wxPayVo.sign
        sdkPay.wxPay(wxPayBean)
    }

    @SuppressLint("微信支付回调--成功")
    @Subscribe(code = Constants.RxBus.CODE_WX_PAY_SUCCESS, threadMode = ThreadMode.MAIN)
    fun rxBusWXPaySucceed() {
        showSDKProgress(false)
        YLogUtil.i("微信支付--成功")
    }

    @SuppressLint("微信支付回调--失败")
    @Subscribe(code = Constants.RxBus.CODE_WX_PAY_FAIL, threadMode = ThreadMode.MAIN)
    fun rxBusWXPayFail(errCode: Int) {
        showSDKProgress(false)
        YLogUtil.i("微信支付--失败--errCode", errCode)
    }

    @SuppressLint("微信支付回调--取消")
    @Subscribe(code = Constants.RxBus.CODE_WX_PAY_CANCEL, threadMode = ThreadMode.MAIN)
    fun rxBusWXPayCancel() {
        showSDKProgress(false)
        YLogUtil.i("微信支付--取消")
    }
}