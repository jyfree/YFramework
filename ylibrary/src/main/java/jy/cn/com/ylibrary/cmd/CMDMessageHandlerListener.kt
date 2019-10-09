package jy.cn.com.ylibrary.cmd


/**

 * @Author Administrator
 * @Date 2019/9/28-14:16
 * @TODO
 */
interface CMDMessageHandlerListener {
    /**
     * 回调消息
     */
    fun handlerMessage(message: CMDMessage)

}