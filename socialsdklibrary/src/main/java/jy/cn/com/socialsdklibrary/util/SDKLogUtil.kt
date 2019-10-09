package jy.cn.com.socialsdklibrary.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author jy
 * @Date 2019/8/8-16:53
 * @TODO 打印log
 */
object SDKLogUtil {

    const val SHOW_LOG = true
    private const val TAG = "socialSdk"

    enum class LogLevel {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    fun e(vararg args: Any?) {
        if (SHOW_LOG) {
            val traceElement = Thread.currentThread().stackTrace[3]
            log(TAG, traceElement, getString(*args), LogLevel.ERROR)
        }
    }

    fun i(vararg args: Any?) {
        if (SHOW_LOG) {
            val traceElement = Thread.currentThread().stackTrace[3]
            log(TAG, traceElement, getString(*args), LogLevel.INFO)
        }
    }

    fun w(vararg args: Any?) {
        if (SHOW_LOG) {
            val traceElement = Thread.currentThread().stackTrace[3]
            log(TAG, traceElement, getString(*args), LogLevel.WARN)
        }
    }

    fun d(vararg args: Any?) {
        if (SHOW_LOG) {
            val traceElement = Thread.currentThread().stackTrace[3]
            log(TAG, traceElement, getString(*args), LogLevel.DEBUG)
        }
    }

    private fun getString(vararg args: Any?): String {
        return when {
            args.size == 1 -> args[0].toString()
            else -> {
                val message = StringBuilder()
                for (`object` in args) {
                    message.append(`object`)
                    message.append("---")
                }
                message.toString()
            }
        }
    }

    private fun log(tag: String, traceElement: StackTraceElement, message: String, logLevel: LogLevel) {
        val msgFormat = "[ %s %s ]---类名：%s---方法名：%s---第%d行---信息---%s"
        val messageWithTime = String.format(Locale.CHINA, msgFormat, SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.CHINA).format(Date()), logLevel.name, traceElement.fileName, traceElement.methodName, traceElement.lineNumber, message)
        when (logLevel) {
            LogLevel.INFO -> Log.i(tag, messageWithTime)
            LogLevel.WARN -> Log.w(tag, messageWithTime)
            LogLevel.DEBUG -> Log.d(tag, messageWithTime)
            LogLevel.ERROR -> Log.e(tag, messageWithTime)
        }
    }

}
