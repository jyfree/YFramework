package jy.cn.com.yframework.simple.cmd

import jy.cn.com.yframework.Constants
import jy.cn.com.ylibrary.cmd.CMD
import jy.cn.com.ylibrary.cmd.CMDDispatcher
import jy.cn.com.ylibrary.cmd.CMDMessage
import jy.cn.com.ylibrary.cmd.CMDMessageHandlerListener
import jy.cn.com.ylibrary.util.YLogUtil
import java.lang.ref.WeakReference

/**

 * @Author Administrator
 * @Date 2019/9/28-14:13
 * @TODO
 */
object CMDMessageHandler {
    private val TAG = CMDMessageHandler::class.java.simpleName
    private var weakReference: WeakReference<CMDMessageHandlerListener>? = null

    fun setCMDMessageHandlerListener(listener: CMDMessageHandlerListener) {
        weakReference = WeakReference(listener)
    }

    fun releaseCMDMessageHandlerListener() {
        weakReference?.clear()
    }

    fun register() {
        CMDDispatcher.register(this)
    }

    fun release() {
        CMDDispatcher.release()
        weakReference?.clear()
    }

    @CMD(id = Constants.CmdType.HOME)
    @Synchronized
    fun testMessage(message: CMDMessage) {
        YLogUtil.eTag(TAG, message.toString())
        weakReference?.get()?.handlerMessage(message)
    }
}