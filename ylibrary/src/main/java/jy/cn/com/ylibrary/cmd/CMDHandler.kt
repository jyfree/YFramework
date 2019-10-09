package jy.cn.com.ylibrary.cmd

/**

 * @Author Administrator
 * @Date 2019/9/28-13:27
 * @TODO
 */
interface CMDHandler {
    fun dispatcher(message: CMDMessage)
}