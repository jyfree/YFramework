package jy.cn.com.ylibrary.coroutine

import android.arch.lifecycle.LifecycleOwner
import jy.cn.com.ylibrary.util.YLogUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


/**
 * @param lifecycleOwner Android生命周期的接口
 *
 * @param context 默认 launch 所创建的 CorouTine 会自动继承当前 Coroutine 的 context，
 * 如果有额外的 context 需要传递给所创建的 Coroutine 则可以通过第一个参数来设置。
 *
 * @param start CoroutineStart 枚举类型，用来指定 Coroutine 启动的选项
 * – DEFAULT （默认值）立刻安排执行该Coroutine实例
 * – LAZY 延迟执行，只有当用到的时候才执行
 * – ATOMIC 类似 DEFAULT，区别是当Coroutine还没有开始执行的时候无法取消
 * – UNDISPATCHED 如果使用 Dispatchers.Unconfined dispatcher，则立刻在当前线程执行直到遇到第一个suspension point 。
 *    然后当 Coroutine 恢复的时候，在继续在 suspension的 context 中设置的 CoroutineDispatcher 中执行。
 *
 * @param block Coroutine 代码块
 */
fun <T> GlobalScope.asyncWithLifecycle(
        lifecycleOwner: LifecycleOwner,
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
): Deferred<T> {

    val deferred = GlobalScope.async(context, start) {
        block()
    }
    lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))
    return deferred

}

fun <T> GlobalScope.bindWithLifecycle(
        lifecycleOwner: LifecycleOwner,
        block: CoroutineScope.() -> Deferred<T>
): Deferred<T> {

    val deferred = block.invoke(this)
    lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))

    return deferred
}

fun <T> GlobalScope.applyAsync(
        block: suspend CoroutineScope.() -> T
): Deferred<T> {
    val deferred = GlobalScope.async {
        block()
    }
    return deferred
}

infix fun <T> Deferred<T>.then(block: (T) -> Unit): Job {

    return GlobalScope.launch(context = Dispatchers.Main) {

        block(this@then.await())
    }
}

infix fun <T, R> Deferred<T>.thenAsync(block: (T) -> R): Deferred<R> {

    return GlobalScope.async(context = Dispatchers.Main) {

        block(this@thenAsync.await())
    }
}

suspend fun <T> Deferred<T>.awaitOrNull(timeout: Long = 0L): T? {
    return try {
        if (timeout > 0) {

            withTimeout(timeout) {
                this@awaitOrNull.await()
            }

        } else {
            this.await()
        }
    } catch (e: Exception) {

        YLogUtil.e("Deferred", e.message)
        null
    }
}