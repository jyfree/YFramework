package jy.cn.com.ylibrary.strategy

object MsgHandleContext {

    fun getMsgHandle(msgType: String): MsgHandleAbstract? {
        try {
            return MsgHandleFactory.instance.getMsgAbstract(msgType)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}
