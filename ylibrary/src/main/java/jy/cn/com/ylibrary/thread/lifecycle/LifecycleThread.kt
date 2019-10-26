package jy.cn.com.ylibrary.thread.lifecycle

import android.arch.lifecycle.LifecycleOwner
import jy.cn.com.ylibrary.thread.QueueProcessingType
import jy.cn.com.ylibrary.thread.ThreadPoolFactory
import jy.cn.com.ylibrary.util.YLogUtil
import java.util.concurrent.ExecutorService

/**

 * @Author Administrator
 * @Date 2019/10/25-16:00
 * @TODO
 */

//线程池
private var SYNC_EXECUTOR = ThreadPoolFactory.createExecutor(2, 2, QueueProcessingType.FIFO)

fun <T> executeThreadWithLifecycle(lifecycleOwner: LifecycleOwner, block: () -> T) {

    var life: LifecycleThreadListener? = null
    val thread = object : Thread() {

        override fun run() {
            try {
                if (life?.isDestroy == false) {
                    block()
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
                YLogUtil.e("executeThread--InterruptedException", e.message)
            }
        }
    }
    life = LifecycleThreadListener(thread)
    lifecycleOwner.lifecycle.addObserver(life)

    submit(thread)


}

fun <T> executeThread(block: () -> T) {

    val thread = object : Thread() {

        override fun run() {
            block()
        }
    }
    submit(thread)
}


fun submit(task: Thread) {
    if ((SYNC_EXECUTOR as ExecutorService).isShutdown) {
        SYNC_EXECUTOR = ThreadPoolFactory.createExecutor(2, 2, QueueProcessingType.FIFO)
    }
    SYNC_EXECUTOR.execute(task)
}