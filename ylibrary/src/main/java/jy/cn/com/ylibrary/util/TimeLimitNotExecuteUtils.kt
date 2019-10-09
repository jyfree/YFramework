package jy.cn.com.ylibrary.util

/**
 * 在一定时间内，限制程序二次执行
 */
class TimeLimitNotExecuteUtils(val time: Long) {
    private var preExecuteTime = 0L
    fun execute(execute: () -> Unit) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - preExecuteTime > time) {
            execute.invoke()
            preExecuteTime = currentTimeMillis
        }
    }
}