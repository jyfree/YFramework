package jy.cn.com.ylibrary.cmd

/**
 * @Author Administrator
 * @Date 2019/9/28-14:08
 * @TODO
 */
class CMDReceiverListener {
    private val messageHandler: CMDHandler = CMDHandlerIml()

    fun onReceivedCMDMessage(message: CMDMessage) {
        messageHandler.dispatcher(message)
    }
}
