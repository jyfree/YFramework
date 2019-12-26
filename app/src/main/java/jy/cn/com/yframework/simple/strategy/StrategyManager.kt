package jy.cn.com.yframework.simple.strategy

import jy.cn.com.ylibrary.strategy.MsgHandleOptions

/**

 * @Author Administrator
 * @Date 2019/12/26-16:26
 * @TODO
 */
object StrategyManager {

    /**
     * 初始化消息策略
     */
    fun init() {
        MsgHandleOptions.beginBuilder()
                .addMsgHandle(LevelUpMsgHandler::class.java.simpleName, LevelUpMsgHandler::class.java)
    }
}