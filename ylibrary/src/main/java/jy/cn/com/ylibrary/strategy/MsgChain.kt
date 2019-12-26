package jy.cn.com.ylibrary.strategy


class MsgChain {

    fun addChain(msgType: String, msg: Any): MsgChain {
        val handleAbstract = MsgHandleContext.getMsgHandle(msgType)
        handleAbstract?.handle(msg)
        return this
    }

}
