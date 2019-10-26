package jy.cn.com.ylibrary.thread.lifecycle

import android.arch.lifecycle.LifecycleOwner
import jy.cn.com.ylibrary.util.HandlerUtil
import jy.cn.com.ylibrary.util.YLogUtil

/**

 * @Author Administrator
 * @Date 2019/10/24-18:19
 * @TODO
 */
interface ThreadRequest {
    fun <T> requestThread(
            threadResultCallback: ThreadResultCallback<T>,
            lifecycleOwner: LifecycleOwner,
            block: () -> T?
    ) {

        executeThreadWithLifecycle(lifecycleOwner) {
            try {
                val t = block.invoke()
                HandlerUtil.runOnUiThread(Runnable {
                    threadResultCallback.forResult(t)
                })
            } catch (ex: Exception) {
                YLogUtil.e("ThreadRequest request  error : ${ex.message}")
                threadResultCallback.forResult(null)
            }
        }
    }

    fun <T> requestThread(block: () -> T?) {
        executeThread {
            block.invoke()
        }
    }

}