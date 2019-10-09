package jy.cn.com.yframework.simple.cmd

import android.content.Context
import android.os.Bundle
import jy.cn.com.yframework.R
import jy.cn.com.ylibrary.base.BaseActivity
import jy.cn.com.ylibrary.cmd.CMDMessage
import jy.cn.com.ylibrary.cmd.CMDMessageHandlerListener
import jy.cn.com.ylibrary.util.ActivityUtils
import jy.cn.com.ylibrary.util.ToastUtil

/**

 * @Author Administrator
 * @Date 2019/9/28-14:20
 * @TODO
 */
class CMDSimpleActivity : BaseActivity(), CMDMessageHandlerListener {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, CMDSimpleActivity::class.java)
        }
    }

    override fun initLayoutID(): Int = R.layout.simple_cmd_activity

    override fun initUI(savedInstanceState: Bundle?) {
        CMDMessageHandler.setCMDMessageHandlerListener(this)
    }

    override fun initClassTag(): Any = CMDSimpleActivity::class.java.simpleName

    override fun onDestroy() {
        super.onDestroy()
        CMDMessageHandler.releaseCMDMessageHandlerListener()
    }

    override fun handlerMessage(message: CMDMessage) {
        ToastUtil.showToast(this, "接收到信息" + message.toString())
    }
}