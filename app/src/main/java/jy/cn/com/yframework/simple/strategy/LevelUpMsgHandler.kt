package jy.cn.com.yframework.simple.strategy

import jy.cn.com.ylibrary.strategy.MsgHandleAbstract
import jy.cn.com.ylibrary.strategy.MsgHandleFactory
import jy.cn.com.ylibrary.util.YLogUtil


class LevelUpMsgHandler : MsgHandleAbstract() {

    override fun handle(msg: Any) {
        MsgHandleFactory.instance.proceed(msg) {
            execute(msg)
        }
    }

    private fun execute(msg: Any) {
        YLogUtil.i("执行消息策略", msg, Thread.currentThread().name)
    }
}
