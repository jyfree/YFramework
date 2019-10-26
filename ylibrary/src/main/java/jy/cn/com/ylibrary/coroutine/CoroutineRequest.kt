package jy.cn.com.ylibrary.coroutine

import android.arch.lifecycle.LifecycleOwner
import jy.cn.com.ylibrary.util.YLogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**

 * @Author Administrator
 * @Date 2019/10/24-18:19
 * @TODO
 */
interface CoroutineRequest {
    fun <T> requestCoroutine(
            coroutineResultCallback: CoroutineResultCallback<T>,
            lifecycleOwner: LifecycleOwner,
            block: () -> T?
    ) {
        GlobalScope.asyncWithLifecycle(Dispatchers.Default, lifecycleOwner) {
            try {
                val t = block.invoke()
                GlobalScope.launch(Dispatchers.Main) {
                    coroutineResultCallback.forResult(t)
                }
            } catch (ex: Exception) {
                YLogUtil.e("CoroutineRequest request  error : ${ex.message}")
                coroutineResultCallback.forResult(null)
            }
        }
    }

    fun <T> requestCoroutine(block: () -> T?) {
        GlobalScope.then(Dispatchers.Default) {
            block.invoke()
        }
    }
}