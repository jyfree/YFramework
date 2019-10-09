package jy.cn.com.ylibrary.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author jy
 * @Date 2019/8/8-15:53
 * @TODO 时间工具类
 */
object TimeUtil {
    @SuppressLint("SimpleDateFormat")
    private val sFormat = SimpleDateFormat("yyMMddHHmmssSSS")
    @SuppressLint("SimpleDateFormat")
    private val sLogFormat = SimpleDateFormat("MM-dd HH:mm:ss.SSS")

    val nowTimeStr: String
        get() = sFormat.format(Date())

    val logStr: String
        get() = sLogFormat.format(Date())
}
