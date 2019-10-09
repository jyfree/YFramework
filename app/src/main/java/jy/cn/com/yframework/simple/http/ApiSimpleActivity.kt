package jy.cn.com.yframework.simple.http

import android.content.Context
import android.os.Bundle
import android.view.View
import jy.cn.com.yframework.R
import jy.cn.com.yframework.simple.http.bean.SendGiftVo
import jy.cn.com.yframework.simple.http.mvp.ApiSimpleModel
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.http.RxObserver
import jy.cn.com.ylibrary.imageload.setImageDefaultLoadIconUrl
import jy.cn.com.ylibrary.util.ActivityUtils
import kotlinx.android.synthetic.main.simple_api_test_activity.*

/**
 * @Author Administrator
 * @Date 2019/9/26-11:38
 * @TODO mvc 请求
 */
class ApiSimpleActivity : BaseActivity() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, ApiSimpleActivity::class.java)
        }
    }

    private val simpleMode = ApiSimpleModel()

    override fun initClassTag(): Any = ApiSimpleActivity::class.java.simpleName

    override fun initLayoutID(): Int = R.layout.simple_api_test_activity

    override fun initUI(savedInstanceState: Bundle?) {

        imageView.setImageDefaultLoadIconUrl("https://img.apk8.com/upload/appsx/14980378457933197.jpg")
    }

    fun onRequest(view: View) {
        when (view.id) {
            R.id.requestData -> sendGift(SendGiftVo("123", "456", 1, 100, 0))
        }
    }

    private fun sendGift(sendGiftVo: SendGiftVo) {
        showPopWindowLoading(true)
        simpleMode.sendGift(RxObserver(doNext = { it ->
            showPopWindowLoading(false)
            tv_msg.setText(it.msg)
        }, doError = { _, _ ->
            showPopWindowLoading(false)
        }), lifecycleProvider, sendGiftVo)
    }
}
