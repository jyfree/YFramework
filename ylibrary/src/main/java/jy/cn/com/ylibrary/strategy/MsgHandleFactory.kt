package jy.cn.com.ylibrary.strategy

import android.os.Build
import jy.cn.com.ylibrary.thread.QueueProcessingType
import jy.cn.com.ylibrary.thread.ThreadPoolFactory
import jy.cn.com.ylibrary.util.YLogUtil
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService

class MsgHandleFactory {
    var strategyMap: MutableMap<String, Class<out MsgHandleAbstract>> = HashMap()
    var pools = ThreadPoolFactory.createExecutorService(2, 2, QueueProcessingType.FIFO)

    private object InnerFactory {
        val msgFactory = MsgHandleFactory()
    }

    @Throws(Exception::class)
    fun getMsgAbstract(msgType: String): MsgHandleAbstract? {
        val clazz = strategyMap[msgType]
        if (clazz == null) {
            YLogUtil.eTag(TAG, "未知的类型")
            return null
        }
        return clazz.newInstance()
    }

    fun proceed(msg: Any, block: (Any) -> Unit) {
        proceed(pools, msg, block)
    }

    fun proceed(executorService: ExecutorService, msg: Any, block: (Any) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            CompletableFuture.runAsync(Runnable {
                block.invoke(msg)
            }, executorService)
        } else {
            val future = executorService.submit(Callable<Void> {
                block.invoke(msg)
                null
            })
        }
    }

    companion object {
        private val TAG = MsgHandleFactory::class.java.simpleName

        val instance: MsgHandleFactory
            get() = InnerFactory.msgFactory
    }

}
