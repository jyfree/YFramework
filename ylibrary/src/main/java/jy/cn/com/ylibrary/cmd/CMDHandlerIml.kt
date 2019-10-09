package jy.cn.com.ylibrary.cmd

/**

 * @Author Administrator
 * @Date 2019/9/28-13:28
 * @TODO
 */
class CMDHandlerIml : CMDHandler {
    override fun dispatcher(message: CMDMessage) {
        CMDDispatcher.invoke(message.type, message)
    }

}