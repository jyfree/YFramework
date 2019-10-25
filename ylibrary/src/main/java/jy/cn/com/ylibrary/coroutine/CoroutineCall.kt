package jy.cn.com.ylibrary.coroutine

import android.arch.lifecycle.LifecycleOwner
import jy.cn.com.ylibrary.util.YLogUtil
import kotlinx.coroutines.GlobalScope

/**

 * @Author Administrator
 * @Date 2019/10/24-18:19
 * @TODO
 */
interface CoroutineCall {
    fun <T> request(
            coroutineResultCallback: CoroutineResultCallback<T>,
            lifecycleOwner: LifecycleOwner,
            block: suspend () -> T?
    ) {
        GlobalScope.asyncWithLifecycle(lifecycleOwner) {
            try {
                block.invoke()
            } catch (ex: Exception) {
                YLogUtil.e("CoroutineCall request  error : ${ex.message}")
                null
            }
        }.then {
            coroutineResultCallback.forResult(it)
        }
    }
}