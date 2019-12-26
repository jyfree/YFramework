package jy.cn.com.ylibrary.strategy

/**
 * @Author Administrator
 * @Date 2019/12/26-14:12
 * @TODO
 */
object MsgHandleOptions {

    fun beginBuilder(): Builder {
        return Builder()
    }

    class Builder {

        fun addMsgHandle(msgType: String, mClass: Class<out MsgHandleAbstract>): Builder {
            MsgHandleFactory.instance.strategyMap[msgType] = mClass
            return this
        }
    }
}
